package yackeen.education.ta3allam.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import yackeen.education.ta3allam.R;
import yackeen.education.ta3allam.model.dto.request.LoginRequest;
import yackeen.education.ta3allam.model.dto.response.LoginResponse;
import yackeen.education.ta3allam.model.dto.response.RegisterResponse;
import yackeen.education.ta3allam.server.api.API;
import yackeen.education.ta3allam.model.dto.request.RegisterRequest;
import yackeen.education.ta3allam.services.MyFirebaseInstanceIdService;
import yackeen.education.ta3allam.util.TextHelper;
import yackeen.education.ta3allam.util.UserHelper;

import static yackeen.education.ta3allam.util.Constants.EMAIL;
import static yackeen.education.ta3allam.util.Constants.ID;
import static yackeen.education.ta3allam.util.Constants.NAME;


public class RegisterActivity extends AppCompatActivity {

    //Tags
    private final String TAG = "registerActivity";

    //Views
    private EditText nameEditText, emailEditText, passwordEditText;

    //Variables
    private boolean isByFacebook = false;
    private String id;
    private String name;
    private String email;
    private String password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        initializeLayoutElements();

        checkIntent();
    }

    //Initialization
    private void initializeLayoutElements() {

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                finish();
            }
        });

        nameEditText     = (EditText) findViewById(R.id.edit_text_name);
        emailEditText    = (EditText) findViewById(R.id.edit_text_email);
        passwordEditText = (EditText) findViewById(R.id.edit_text_password);
    }
    private void checkIntent(){

        if (getIntent().hasExtra(ID)){

            isByFacebook = true;
            id = getIntent().getStringExtra(ID);
            email = getIntent().getStringExtra(EMAIL);
            name = getIntent().getStringExtra(NAME);

            nameEditText.setText(name);
            emailEditText.setText(email);
        }
    }

    //Click listener
    public void onRegisterButtonClick(View view) {

        if (!TextHelper.isEditTextEmpty(new EditText[]{nameEditText, emailEditText, passwordEditText})
                && TextHelper.isEmail(emailEditText)
                && TextHelper.isPassword(passwordEditText)){

            UserHelper.showProgressDialog(this, getString(R.string.register), getString(R.string.registering));
            if (email != null && !email.equals(emailEditText.getText().toString())) isByFacebook = false;

            name = nameEditText.getText().toString().trim();
            email = emailEditText.getText().toString().trim();
            password = passwordEditText.getText().toString().trim();

            if (isByFacebook){

                API.getUserAPIs().register(
                        getRegisterByFacebookBody(),
                        getRegisterListener(),
                        getRegisterFailedListener());

            }else {

                API.getUserAPIs().register(
                        getRegisterByEmailBody(),
                        getRegisterListener(),
                        getRegisterFailedListener());


            }
        }
    }

    public void loginCal(){
        LoginRequest body = new LoginRequest();
        body.isByFacebook = false;
        body.FacebookID = "";
        body.Email = emailEditText.getText().toString();
        body.Password = passwordEditText.getText().toString();
        body.DeviceToken = MyFirebaseInstanceIdService.getDeviceToken(RegisterActivity.this);

        API.getUserAPIs().login(
                body,
                getLoginRegisterListener(),
                getRegisterFailedListener());
    }

    //Action
    private RegisterRequest getRegisterByEmailBody(){

        RegisterRequest body = new RegisterRequest();
        body.isByFacebook = false;
        body.name  = name;
        body.email = email;
        body.password = password;
        body.confirmPassword = password;
        body.facebookID = "";
        return body;
    }
    private RegisterRequest getRegisterByFacebookBody(){

        RegisterRequest body = new RegisterRequest();
        body.isByFacebook = true;
        body.name  = name;
        body.email = email;
        body.password = password;
        body.confirmPassword = password;
        body.facebookID = id;

        return body;
    }

    //Network Listener
    private Response.Listener<RegisterResponse> getRegisterListener(){
        return new Response.Listener<RegisterResponse>() {
            @Override
            public void onResponse(RegisterResponse response) {

                Log.i(TAG, "onResponse:IsSuccess "    .concat(String.valueOf(response.isSuccessful)));
                Log.i(TAG, "onResponse:ErrorMessage " .concat(response.errorMessage));

                if (response.isSuccessful){
                    Toast.makeText(RegisterActivity.this, "تم السجيل", Toast.LENGTH_SHORT).show();
                    loginCal();

                }else{

                    UserHelper.dismissProgressDialog();
                    Toast.makeText(RegisterActivity.this, response.errorMessage, Toast.LENGTH_SHORT).show();}
            }
        };
    }

    private Response.Listener<LoginResponse> getLoginRegisterListener(){

        return new Response.Listener<LoginResponse>() {
            @Override
            public void onResponse(LoginResponse response) {


                UserHelper.dismissProgressDialog();
                Log.d(TAG, ":"+UserHelper.getSecurityToken(RegisterActivity.this));

                Log.i(TAG, "onResponse:IsSuccess "    .concat(String.valueOf(response.isSuccessful)));
                Log.i(TAG, "onResponse:isFirstTime: " .concat(String.valueOf(response.isFirstTime)));
                Log.i(TAG, "onResponse:UserType "     .concat(String.valueOf(response.userType)));
                Log.i(TAG, "onResponse:UserID "       .concat(response.userID));
                Log.i(TAG, "onResponse:ErrorMessage " .concat(response.errorMessage));
                if (response.isSuccessful){
                    UserHelper.saveStringInSharedPreferences(UserHelper.USER_ID,response.userID);
                    UserHelper.saveIntInSharedPreferences(UserHelper.USER_TYPE,response.userType);
                    Intent intent = new Intent(RegisterActivity.this, FirstLogin.class);
                        startActivity(intent);
                    finish();
                }else {Toast.makeText(RegisterActivity.this, response.errorMessage, Toast.LENGTH_SHORT).show();
                    Log.d(TAG, "onResponse: "+response.errorMessage);}

            }
        };}
    private Response.ErrorListener getRegisterFailedListener(){

        return new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "onErrorResponse: ".concat(error.toString()));

                UserHelper.dismissProgressDialog();
                Toast.makeText(RegisterActivity.this, R.string.network_error, Toast.LENGTH_SHORT).show();
            }
        };
    }
}

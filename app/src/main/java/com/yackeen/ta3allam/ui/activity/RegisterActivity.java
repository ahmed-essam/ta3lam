package com.yackeen.ta3allam.ui.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.yackeen.ta3allam.R;
import com.yackeen.ta3allam.model.dto.response.RegisterResponse;
import com.yackeen.ta3allam.server.api.API;
import com.yackeen.ta3allam.model.dto.request.RegisterRequest;
import com.yackeen.ta3allam.util.TextHelper;
import com.yackeen.ta3allam.util.UserHelper;

import static com.yackeen.ta3allam.util.Constants.EMAIL;
import static com.yackeen.ta3allam.util.Constants.ID;
import static com.yackeen.ta3allam.util.Constants.NAME;


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
        toolbar.setTitle(R.string.register);
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

            if (!this.email.equals(email)) isByFacebook = false;

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

            }
        };
    }
    private Response.ErrorListener getRegisterFailedListener(){

        return new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "onErrorResponse: ".concat(error.toString()));

                UserHelper.getInstance().dismissProgressDialog();
                Toast.makeText(RegisterActivity.this, R.string.network_error, Toast.LENGTH_SHORT).show();
            }
        };
    }
}
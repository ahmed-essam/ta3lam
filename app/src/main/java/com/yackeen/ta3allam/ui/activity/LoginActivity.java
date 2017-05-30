package com.yackeen.ta3allam.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.yackeen.ta3allam.R;
import com.yackeen.ta3allam.model.dto.request.LoginRequest;
import com.yackeen.ta3allam.model.dto.response.LoginResponse;
import com.yackeen.ta3allam.server.api.API;
import com.yackeen.ta3allam.ui.Fragment.FacebookLoginFragment;
import com.yackeen.ta3allam.util.TextHelper;
import com.yackeen.ta3allam.util.UserHelper;

import static com.android.volley.Response.*;

public class LoginActivity extends AppCompatActivity {

    //Tags
    private final String TAG = "loginActivity";

    //Views
    private EditText emailEditText, passwordEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (UserHelper.isLoggedIn(this)) startActivity(new Intent(this, Home.class));
        setContentView(R.layout.activity_login);

        initializeLayoutElements();
        initializeFacebookFragment(savedInstanceState);
    }

    //Initialization
    private void initializeLayoutElements() {

        emailEditText = (EditText) findViewById(R.id.edit_text_email);
        passwordEditText = (EditText) findViewById(R.id.edit_text_password);
    }
    private void initializeFacebookFragment(Bundle savedInstanceState){

        // Check that the activity is using the layout version with
        // the fragment_container FrameLayout
        if (findViewById(R.id.fragment_facebook_container) != null) {

            // However, if we're being restored from a previous state,
            // then we don't need to do anything and should return or else
            // we could end up with overlapping fragments.
            if (savedInstanceState != null) {
                return;
            }

            FacebookLoginFragment fragment = new FacebookLoginFragment();

            // Add the fragment to the 'fragment_container' FrameLayout
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment_facebook_container, fragment, "fragmentFacebook").commit();
        }
    }

    //Click Listener
    public void onLoginButtonClick(View view) {

        if (!TextHelper.isEditTextEmpty(new EditText[]{emailEditText, passwordEditText})
                && TextHelper.isEmail(emailEditText)
                && TextHelper.isPassword(passwordEditText)){

            UserHelper.showProgressDialog(this, getString(R.string.sign_in), getString(R.string.signing_in));

            LoginRequest body = new LoginRequest();
            body.isByFacebook = false;
            body.FacebookID = "";
            body.Email = emailEditText.getText().toString();
            body.Password = passwordEditText.getText().toString();
            body.DeviceToken = UserHelper.getDeviceToken(this);

            API.getUserAPIs().login(
                    body,
                    getLoginListener(),
                    getLoginFailedListener());
        }
    }
    public void onTextSignUpClick(View view) {

        startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
    }
    public void onTextForgotPasswordClick(View view) {

        startActivity(new Intent(LoginActivity.this, PasswordResetActivity.class));
    }

    //Network Listener
    private Response.Listener<LoginResponse> getLoginListener(){

        return new Listener<LoginResponse>() {
            @Override
            public void onResponse(LoginResponse response) {

                UserHelper.dismissProgressDialog();

                Log.i(TAG, "onResponse:IsSuccess "    .concat(String.valueOf(response.isSuccessful)));
                Log.i(TAG, "onResponse:isFirstTime: " .concat(String.valueOf(response.isFirstTime)));
                Log.i(TAG, "onResponse:UserType "     .concat(String.valueOf(response.userType)));
                Log.i(TAG, "onResponse:UserID "       .concat(response.userID));
                Log.i(TAG, "onResponse:ErrorMessage " .concat(response.errorMessage));

                if (response.isSuccessful){

                    if (response.isFirstTime){

                        startActivity(new Intent(LoginActivity.this, FirstLogin.class));
                        finish();

                    }else {

                        startActivity(new Intent(LoginActivity.this, FirstLogin.class));
                        finish();

                    }//end inner if

                }else Toast.makeText(LoginActivity.this, response.errorMessage, Toast.LENGTH_SHORT).show();
                //end outer if
            }
        };
    }
    private Response.ErrorListener getLoginFailedListener(){

        return new ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "onErrorResponse: ".concat(error.toString()));

                UserHelper.dismissProgressDialog();
                Toast.makeText(LoginActivity.this, R.string.network_error, Toast.LENGTH_SHORT).show();
            }
        };
    }
}

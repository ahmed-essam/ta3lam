package com.yackeen.ta3allam.ui.Fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginResult;
import com.yackeen.ta3allam.R;
import com.yackeen.ta3allam.model.dto.request.LoginRequest;
import com.yackeen.ta3allam.model.dto.response.LoginResponse;
import com.yackeen.ta3allam.provider.AuthFacebook;
import com.yackeen.ta3allam.server.api.API;
import com.yackeen.ta3allam.ui.activity.FirstLogin;
import com.yackeen.ta3allam.ui.activity.Home;
import com.yackeen.ta3allam.ui.activity.RegisterActivity;
import com.yackeen.ta3allam.util.UserHelper;

import org.json.JSONException;
import org.json.JSONObject;

import static com.yackeen.ta3allam.util.Constants.EMAIL;
import static com.yackeen.ta3allam.util.Constants.ID;
import static com.yackeen.ta3allam.util.Constants.NAME;

public class FacebookLoginFragment extends Fragment {

    //Tags
    private final String TAG = "facebookFragment";

    //Variables
    private String id;
    private String name;
    private String email;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_facebook_login, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initializeLayoutElements();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        AuthFacebook.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }

    //Initialization
    private void initializeLayoutElements() {

        Button loginButton = (Button) getActivity().findViewById(R.id.btn_facebook);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AuthFacebook.login(FacebookLoginFragment.this, getFacebookCallback());
            }
        });
    }

    //Action
    private void login(boolean isFirstTime) {

        if (isFirstTime){

            getActivity().startActivity(new Intent(getActivity(), FirstLogin.class));
            getActivity().finish();

        }else {

            getActivity().startActivity(new Intent(getActivity(), Home.class));
            getActivity().finish();

        }//end inner if
    }
    private void registerNewUser(){

        Intent intent = new Intent(getActivity(), RegisterActivity.class);
        intent.putExtra(ID, id);
        intent.putExtra(NAME, name.replaceAll(" ", ""));
        intent.putExtra(EMAIL, email);
        startActivity(intent);
    }

    //Listeners
    private FacebookCallback<LoginResult> getFacebookCallback(){

        return new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Log.i(TAG, "onSuccess:Token:".concat(loginResult.getAccessToken().getToken()));

                GraphRequest request = getJasonObjectCallBack(loginResult);
                Bundle parameters = new Bundle();
                parameters.putString("fields", "id,name,email");
                request.setParameters(parameters);
                request.executeAsync();
            }

            @Override
            public void onCancel() {

                Log.i(TAG, "onCancel");
            }

            @Override
            public void onError(FacebookException error) {
                Log.e(TAG, "onError:".concat(error.getMessage()));
            }
        };
    }
    private GraphRequest getJasonObjectCallBack(LoginResult loginResult) {

        return GraphRequest.newMeRequest(loginResult.getAccessToken(), new GraphRequest.GraphJSONObjectCallback() {

            @Override
            public void onCompleted(JSONObject object, GraphResponse response) {
                Log.v(TAG, response.toString());
                try {

                    id = object.getString(ID);
                    name = object.getString(NAME);
                    email = object.getString(EMAIL);
                    String deviceToken = UserHelper.getDeviceToken(getActivity());

                    LoginRequest body = new LoginRequest();
                    body.isByFacebook = true;
                    body.FacebookID = id;
                    body.Email = email;
                    body.Password = "";
                    body.DeviceToken = deviceToken;

                    API.getUserAPIs().login(
                            body,
                            getLoginListener(),
                            getLoginFailedListener());

                } catch (JSONException e) {

                    Log.e(TAG, "GraphRequest:".concat(e.getMessage()));
                }
            }
        });
    }
    private Response.Listener<LoginResponse> getLoginListener(){

        return new Response.Listener<LoginResponse>() {
            @Override
            public void onResponse(LoginResponse response) {

                UserHelper.dismissProgressDialog();

                Log.i(TAG, "onResponse:IsSuccess "    .concat(String.valueOf(response.isSuccessful)));
                Log.i(TAG, "onResponse:isFirstTime: " .concat(String.valueOf(response.isFirstTime)));
                Log.i(TAG, "onResponse:UserType "     .concat(String.valueOf(response.userType)));
                Log.i(TAG, "onResponse:UserID "       .concat(response.userID));
                Log.i(TAG, "onResponse:ErrorMessage " .concat(response.errorMessage));

                if (response.isSuccessful) login(response.isFirstTime);
                else registerNewUser();
            }
        };
    }
    private Response.ErrorListener getLoginFailedListener(){

        return new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "onErrorResponse: ".concat(error.toString()));

                UserHelper.dismissProgressDialog();

                Toast.makeText(getActivity(), R.string.network_error, Toast.LENGTH_SHORT).show();
            }
        };
    }
}

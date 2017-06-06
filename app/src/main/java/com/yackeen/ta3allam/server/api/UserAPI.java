package com.yackeen.ta3allam.server.api;

import android.content.Context;
import android.util.Log;

import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.yackeen.ta3allam.application.AppController;
import com.yackeen.ta3allam.model.dto.request.FirstLogin1Request;
import com.yackeen.ta3allam.model.dto.request.FirstLogin2Request;
import com.yackeen.ta3allam.model.dto.request.LoginRequest;
import com.yackeen.ta3allam.model.dto.request.RegisterRequest;
import com.yackeen.ta3allam.model.dto.request.ResetPasswordRequest;
import com.yackeen.ta3allam.model.dto.response.FirstLoginResponse1;
import com.yackeen.ta3allam.model.dto.response.FirstLoginResponse2;
import com.yackeen.ta3allam.model.dto.response.LoginResponse;
import com.yackeen.ta3allam.model.dto.response.RegisterResponse;
import com.yackeen.ta3allam.model.dto.response.ResetPasswordResponse;
import com.yackeen.ta3allam.server.request.GsonPostRequest;
import com.yackeen.ta3allam.services.MyFirebaseInstanceIdService;
import com.yackeen.ta3allam.ui.activity.FirstLogin;
import com.yackeen.ta3allam.util.UserHelper;

import java.util.HashMap;
import java.util.Map;

public class UserAPI {

    private final String BaseURL = "http://yaken.cloudapp.net/Ta3llam/Api/User/";
    private final String AppBaseURL = "http://yaken.cloudapp.net/Ta3llam/Api/";

    //Tags
    private final String TAG = "userAPI";

    //References
    private final static UserAPI userAPI = new UserAPI();
    public static UserAPI getInstance() {

        return userAPI;
    }
    private AppController appContext = AppController.getInstance();

    public void login(LoginRequest body, Listener<LoginResponse> listener, ErrorListener errorListener){

        String url = BaseURL.concat("Login");

        Log.i(TAG.concat(":login"), "URL: ".concat(url));
        Log.i(TAG.concat(":login"), "body:IsByFacebook: ".concat(String.valueOf(body.isByFacebook)));
        Log.i(TAG.concat(":login"), "body:FacebookID: "  .concat(body.FacebookID));
        Log.i(TAG.concat(":login"), "body:Email: "       .concat(body.Email));
        Log.i(TAG.concat(":login"), "body:Password: "    .concat(body.Password));
        Log.i(TAG.concat(":login"), "body:DeviceToken: " .concat(body.DeviceToken));

        GsonPostRequest<LoginResponse> request = new GsonPostRequest<>(
                url,
                body,
                LoginResponse.class,
                null,
                listener,
                errorListener
        );
        appContext.addToRequestQueue(request);
    }
    public void register(RegisterRequest body, Listener<RegisterResponse> listener, ErrorListener errorListener){

        String url = BaseURL.concat("Register");

        Log.i(TAG.concat(":register"), "URL: ".concat(url));
        Log.i(TAG.concat(":register"), "body:IsByFacebook: ".concat(String.valueOf(body.isByFacebook)));
        Log.i(TAG.concat(":register"), "body:FacebookID: "  .concat(body.facebookID));
        Log.i(TAG.concat(":register"), "body:Name: "        .concat(body.name));
        Log.i(TAG.concat(":register"), "body:Email: "       .concat(body.email));
        Log.i(TAG.concat(":register"), "body:Password: "    .concat(body.password));
        Log.i(TAG.concat(":register"), "body:ConfirmPassword: ".concat(body.confirmPassword));

        GsonPostRequest<RegisterResponse> request = new GsonPostRequest<>(
                url,
                body,
                RegisterResponse.class,
                null,
                listener,
                errorListener
        );
        appContext.addToRequestQueue(request);
    }
    public void resetPasswordFor(ResetPasswordRequest body, Listener<ResetPasswordResponse> listener, ErrorListener errorListener){

        String url = BaseURL.concat("ForgetPassword");
        Log.i(TAG.concat(":forgotPassword"), "URL: ".concat(url));
        Log.i(TAG.concat(":forgotPassword"), "body:Email: ".concat(body.email));

        GsonPostRequest<ResetPasswordResponse> request = new GsonPostRequest<>(
                url,
                body,
                ResetPasswordResponse.class,
                null,
                listener,
                errorListener
        );
        appContext.addToRequestQueue(request);
    }
    public void getAllCourses(FirstLogin1Request body, Listener<FirstLoginResponse1> listener, ErrorListener errorListener, Context context){
        String url = AppBaseURL.concat("Courses/GetCoursesList");
        Map map = new HashMap();
        map.put("UserID", UserHelper.getUserId(context));
        map.put("Token", MyFirebaseInstanceIdService.getDeviceToken(context));
        GsonPostRequest<FirstLoginResponse1> request = new GsonPostRequest<>(
                url,
                body,
                FirstLoginResponse1.class,
                map,
                listener,
                errorListener
        );appContext.addToRequestQueue(request);
    }
    public void getAllbooks(FirstLogin2Request body, Listener<FirstLoginResponse2> listener, ErrorListener errorListener, Context context){
        String url = AppBaseURL.concat("Books/GetBooksList");
        Map map = new HashMap();
        map.put("UserID", UserHelper.getUserId(context));
        map.put("Token", MyFirebaseInstanceIdService.getDeviceToken(context));
        GsonPostRequest<FirstLoginResponse2> request = new GsonPostRequest<>(
                url,
                body,
                FirstLoginResponse2.class,
                map,
                listener,
                errorListener
        );appContext.addToRequestQueue(request);
    }
}

package yackeen.education.ta3allam.server.api;

import android.content.Context;
import android.util.Log;

import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;

import yackeen.education.ta3allam.application.AppController;
import yackeen.education.ta3allam.model.dto.request.BookDetailRequest;
import yackeen.education.ta3allam.model.dto.request.CommentsRequest;
import yackeen.education.ta3allam.model.dto.request.ConversationRequest;
import yackeen.education.ta3allam.model.dto.request.FirstLogin1Request;
import yackeen.education.ta3allam.model.dto.request.FirstLogin2Request;
import yackeen.education.ta3allam.model.dto.request.FollowerRequest;
import yackeen.education.ta3allam.model.dto.request.ForumRequest;
import yackeen.education.ta3allam.model.dto.request.LoginRequest;
import yackeen.education.ta3allam.model.dto.request.MessageListRequest;
import yackeen.education.ta3allam.model.dto.request.NewsRequest;
import yackeen.education.ta3allam.model.dto.request.NotificationsRequest;
import yackeen.education.ta3allam.model.dto.request.RegisterRequest;
import yackeen.education.ta3allam.model.dto.request.ResetPasswordRequest;
import yackeen.education.ta3allam.model.dto.request.SendMessageRequest;
import yackeen.education.ta3allam.model.dto.request.SetUserBookRequest;
import yackeen.education.ta3allam.model.dto.response.BookDetailResponse;
import yackeen.education.ta3allam.model.dto.response.CommentsResponse;
import yackeen.education.ta3allam.model.dto.response.ConversationResponse;
import yackeen.education.ta3allam.model.dto.response.EmptyResponse;
import yackeen.education.ta3allam.model.dto.response.FirstLoginResponse1;
import yackeen.education.ta3allam.model.dto.response.FirstLoginResponse2;
import yackeen.education.ta3allam.model.dto.response.FollwerResponse;
import yackeen.education.ta3allam.model.dto.response.ForumResponse;
import yackeen.education.ta3allam.model.dto.response.LoginResponse;
import yackeen.education.ta3allam.model.dto.response.MessageListResponse;
import yackeen.education.ta3allam.model.dto.response.NewsResponse;
import yackeen.education.ta3allam.model.dto.response.NotificationsResponse;
import yackeen.education.ta3allam.model.dto.response.RegisterResponse;
import yackeen.education.ta3allam.model.dto.response.ResetPasswordResponse;
import yackeen.education.ta3allam.model.dto.response.SetUserBookResponse;
import yackeen.education.ta3allam.server.request.GsonPostRequest;
import yackeen.education.ta3allam.services.MyFirebaseInstanceIdService;
import yackeen.education.ta3allam.util.UserHelper;

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

//user APIs
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

    // app APIs
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
        );
        appContext.addToRequestQueue(request);
    }
    public void getAllNews(NewsRequest body, Listener<NewsResponse> listener, ErrorListener errorListener, Context context){
        String url = BaseURL.concat("DoGetUserHomeDetails");
        Map map = new HashMap();
        map.put("UserID", UserHelper.getUserId(context));
        map.put("Token", MyFirebaseInstanceIdService.getDeviceToken(context));
        GsonPostRequest<NewsResponse> request = new GsonPostRequest<>(
                url,
                body,
                NewsResponse.class,
                map,
                listener,
                errorListener
        );
        appContext.addToRequestQueue(request);
    }
    public void getUserProfileDetail(FollowerRequest body, Listener<FollwerResponse> listener, ErrorListener errorListener, Context context){
        String url = BaseURL.concat("GetUserProfileDetails");
        Map map = new HashMap();
        map.put("UserID", UserHelper.getUserId(context));
        map.put("Token", MyFirebaseInstanceIdService.getDeviceToken(context));
        GsonPostRequest<FollwerResponse> request = new GsonPostRequest<>(
                url,
                body,
                FollwerResponse.class,
                map,
                listener,
                errorListener
        );
        appContext.addToRequestQueue(request);
    }
    public void getAllNotifications(NotificationsRequest body, Listener<NotificationsResponse> listener, ErrorListener errorListener, Context context){
        String url = AppBaseURL.concat("Notifications/GetAllUserNotifications");
        Map map = new HashMap();
        map.put("UserID", UserHelper.getUserId(context));
        map.put("Token", MyFirebaseInstanceIdService.getDeviceToken(context));
        GsonPostRequest<NotificationsResponse> request = new GsonPostRequest<>(
                url,
                body,
                NotificationsResponse.class,
                map,
                listener,
                errorListener
        );
        appContext.addToRequestQueue(request);
    }
    public void getBookDetails(BookDetailRequest body, Listener<BookDetailResponse> listener, ErrorListener errorListener, Context context){
        String url = AppBaseURL.concat("Books/GetBookDetails");
        Map map = new HashMap();
        map.put("UserID", UserHelper.getUserId(context));
        map.put("Token", MyFirebaseInstanceIdService.getDeviceToken(context));
        GsonPostRequest<BookDetailResponse> request = new GsonPostRequest<>(
                url,
                body,
                BookDetailResponse.class,
                map,
                listener,
                errorListener
        );
        appContext.addToRequestQueue(request);
    }
    public void getBookForum(ForumRequest body, Listener<ForumResponse> listener, ErrorListener errorListener, Context context){
        String url = AppBaseURL.concat("Forum/GetBookPosts");
        Map map = new HashMap();
        map.put("UserID", UserHelper.getUserId(context));
        map.put("Token", MyFirebaseInstanceIdService.getDeviceToken(context));
        GsonPostRequest<ForumResponse> request = new GsonPostRequest<>(
                url,
                body,
                ForumResponse.class,
                map,
                listener,
                errorListener
        );
        appContext.addToRequestQueue(request);
    }
    public void getForumComments(CommentsRequest body, Listener<CommentsResponse> listener, ErrorListener errorListener, Context context){
        String url = AppBaseURL.concat("Forum/DoGetPostComments");
        Map map = new HashMap();
        map.put("UserID", UserHelper.getUserId(context));
        map.put("Token", MyFirebaseInstanceIdService.getDeviceToken(context));
        GsonPostRequest<CommentsResponse> request = new GsonPostRequest<>(
                url,
                body,
                CommentsResponse.class,
                map,
                listener,
                errorListener
        );
        appContext.addToRequestQueue(request);
    }
    public void setUserBook(SetUserBookRequest body, Listener<SetUserBookResponse> listener, ErrorListener errorListener, Context context){
        String url = AppBaseURL.concat("Books/SetUserBooks");
        Map map = new HashMap();
        map.put("UserID", UserHelper.getUserId(context));
        map.put("Token", MyFirebaseInstanceIdService.getDeviceToken(context));
        GsonPostRequest<SetUserBookResponse> request = new GsonPostRequest<>(
                url,
                body,
                SetUserBookResponse.class,
                map,
                listener,
                errorListener
        );
        appContext.addToRequestQueue(request);
    }
    public void getUserMessagesContacts(MessageListRequest body, Listener<MessageListResponse> listener, ErrorListener errorListener, Context context){
        String url = AppBaseURL.concat("Messages/GetMessagesContacts");
        Map map = new HashMap();
        map.put("UserID", UserHelper.getUserId(context));
        map.put("Token", MyFirebaseInstanceIdService.getDeviceToken(context));
        GsonPostRequest<MessageListResponse> request = new GsonPostRequest<>(
                url,
                body,
                MessageListResponse.class,
                map,
                listener,
                errorListener
        );
        appContext.addToRequestQueue(request);
    }
    public void getConversationMessages(ConversationRequest body, Listener<ConversationResponse> listener, ErrorListener errorListener, Context context){
        String url = AppBaseURL.concat("Messages/ConversationMessages");
        Map map = new HashMap();
        map.put("UserID", UserHelper.getUserId(context));
        map.put("Token", MyFirebaseInstanceIdService.getDeviceToken(context));
        GsonPostRequest<ConversationResponse> request = new GsonPostRequest<>(
                url,
                body,
                ConversationResponse.class,
                map,
                listener,
                errorListener
        );
        appContext.addToRequestQueue(request);
    }
    public void getSendMessages(SendMessageRequest body, Listener<EmptyResponse> listener, ErrorListener errorListener, Context context){
        String url = AppBaseURL.concat("Messages/SendMessage");
        Map map = new HashMap();
        map.put("UserID", UserHelper.getUserId(context));
        map.put("Token", MyFirebaseInstanceIdService.getDeviceToken(context));
        GsonPostRequest<EmptyResponse> request = new GsonPostRequest<>(
                url,
                body,
                EmptyResponse.class,
                map,
                listener,
                errorListener
        );
        appContext.addToRequestQueue(request);
    }

}
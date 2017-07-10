package yackeen.education.ta3allam.server.api;

import android.content.Context;
import android.util.Log;

import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;

import yackeen.education.ta3allam.application.AppController;
import yackeen.education.ta3allam.model.dto.request.AddCommentRequest;
import yackeen.education.ta3allam.model.dto.request.AddPostRequest;
import yackeen.education.ta3allam.model.dto.request.BookDetailRequest;
import yackeen.education.ta3allam.model.dto.request.BookFollowRequest;
import yackeen.education.ta3allam.model.dto.request.CommentsRequest;
import yackeen.education.ta3allam.model.dto.request.ConversationRequest;
import yackeen.education.ta3allam.model.dto.request.EditProfilePictureRequest;
import yackeen.education.ta3allam.model.dto.request.EditProfileRequest;
import yackeen.education.ta3allam.model.dto.request.FirstLogin1Request;
import yackeen.education.ta3allam.model.dto.request.FirstLogin2Request;
import yackeen.education.ta3allam.model.dto.request.FollowerRequest;
import yackeen.education.ta3allam.model.dto.request.ForumRequest;
import yackeen.education.ta3allam.model.dto.request.FriendsRequest;
import yackeen.education.ta3allam.model.dto.request.LikeAndShareRequest;
import yackeen.education.ta3allam.model.dto.request.LoginRequest;
import yackeen.education.ta3allam.model.dto.request.MessageListRequest;
import yackeen.education.ta3allam.model.dto.request.NewsRequest;
import yackeen.education.ta3allam.model.dto.request.NotificationsRequest;
import yackeen.education.ta3allam.model.dto.request.RegisterRequest;
import yackeen.education.ta3allam.model.dto.request.ResetPasswordRequest;
import yackeen.education.ta3allam.model.dto.request.ResetPasswordRequest2;
import yackeen.education.ta3allam.model.dto.request.SearchRequest;
import yackeen.education.ta3allam.model.dto.request.SendMessageRequest;
import yackeen.education.ta3allam.model.dto.request.SetUserBookRequest;
import yackeen.education.ta3allam.model.dto.request.UnSetUserBookRequest;
import yackeen.education.ta3allam.model.dto.request.UserFollowRequest;
import yackeen.education.ta3allam.model.dto.response.BookDetailResponse;
import yackeen.education.ta3allam.model.dto.response.CommentsResponse;
import yackeen.education.ta3allam.model.dto.response.ConversationResponse;
import yackeen.education.ta3allam.model.dto.response.EditProfilePictureResponse;
import yackeen.education.ta3allam.model.dto.response.EditProfileResponse;
import yackeen.education.ta3allam.model.dto.response.EmptyResponse;
import yackeen.education.ta3allam.model.dto.response.FirstLoginResponse1;
import yackeen.education.ta3allam.model.dto.response.FirstLoginResponse2;
import yackeen.education.ta3allam.model.dto.response.FollwerResponse;
import yackeen.education.ta3allam.model.dto.response.ForumResponse;
import yackeen.education.ta3allam.model.dto.response.FriendsResponse;
import yackeen.education.ta3allam.model.dto.response.LoginResponse;
import yackeen.education.ta3allam.model.dto.response.MessageListResponse;
import yackeen.education.ta3allam.model.dto.response.NewsResponse;
import yackeen.education.ta3allam.model.dto.response.NotificationsResponse;
import yackeen.education.ta3allam.model.dto.response.RegisterResponse;
import yackeen.education.ta3allam.model.dto.response.ResetPasswordResponse;
import yackeen.education.ta3allam.model.dto.response.ResetPasswordResponse2;
import yackeen.education.ta3allam.model.dto.response.SearchResponse;
import yackeen.education.ta3allam.model.dto.response.SetUserBookResponse;
import yackeen.education.ta3allam.model.dto.response.UnreadeMessageNumResponse;
import yackeen.education.ta3allam.model.dto.response.UnreadeNotificationNumResponse;
import yackeen.education.ta3allam.server.request.GsonPostRequest;
import yackeen.education.ta3allam.services.MyFirebaseInstanceIdService;
import yackeen.education.ta3allam.ui.activity.EditProfileActivity;
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
    public void login(LoginRequest body, Listener<LoginResponse> listener, ErrorListener errorListener) {

        String url = BaseURL.concat("Login");

        Log.i(TAG.concat(":login"), "URL: ".concat(url));
        Log.i(TAG.concat(":login"), "body:IsByFacebook: ".concat(String.valueOf(body.isByFacebook)));
        Log.i(TAG.concat(":login"), "body:FacebookID: ".concat(body.FacebookID));
        Log.i(TAG.concat(":login"), "body:Email: ".concat(body.Email));
        Log.i(TAG.concat(":login"), "body:Password: ".concat(body.Password));
        Log.i(TAG.concat(":login"), "body:DeviceToken: ".concat(body.DeviceToken));

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

    public void register(RegisterRequest body, Listener<RegisterResponse> listener, ErrorListener errorListener) {

        String url = BaseURL.concat("Register");

        Log.i(TAG.concat(":register"), "URL: ".concat(url));
        Log.i(TAG.concat(":register"), "body:IsByFacebook: ".concat(String.valueOf(body.isByFacebook)));
        Log.i(TAG.concat(":register"), "body:FacebookID: ".concat(body.facebookID));
        Log.i(TAG.concat(":register"), "body:Name: ".concat(body.name));
        Log.i(TAG.concat(":register"), "body:Email: ".concat(body.email));
        Log.i(TAG.concat(":register"), "body:Password: ".concat(body.password));
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

    public void resetPasswordFor(ResetPasswordRequest body, Listener<ResetPasswordResponse> listener, ErrorListener errorListener) {

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

    public void verifyPasswordFor(ResetPasswordRequest2 body, Listener<ResetPasswordResponse2> listener, ErrorListener errorListener) {

        String url = BaseURL.concat("VerifyPassword");
        Log.i(TAG.concat(":forgotPassword"), "URL: ".concat(url));
        Log.i(TAG.concat(":forgotPassword"), "body:Email: ".concat(body.getEmail()));

        GsonPostRequest<ResetPasswordResponse2> request = new GsonPostRequest<>(
                url,
                body,
                ResetPasswordResponse2.class,
                null,
                listener,
                errorListener
        );
        appContext.addToRequestQueue(request);
    }

    // app APIs
    public void getAllCourses(FirstLogin1Request body, Listener<FirstLoginResponse1> listener, ErrorListener errorListener, Context context) {
        String url = AppBaseURL.concat("Courses/GetCoursesList");
        Map map = new HashMap();
        map.put("UserID", UserHelper.getUserId(context));
        map.put("Token", UserHelper.getSecurityToken(context));
        GsonPostRequest<FirstLoginResponse1> request = new GsonPostRequest<>(
                url,
                body,
                FirstLoginResponse1.class,
                map,
                listener,
                errorListener
        );
        appContext.addToRequestQueue(request);
    }

    public void getAllbooks(FirstLogin2Request body, Listener<FirstLoginResponse2> listener, ErrorListener errorListener, Context context) {
        String url = AppBaseURL.concat("Books/GetBooksList");
        Map map = new HashMap();
        map.put("UserID", UserHelper.getUserId(context));
        map.put("Token", UserHelper.getSecurityToken(context));
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

    public void getAllNews(NewsRequest body, Listener<NewsResponse> listener, ErrorListener errorListener, Context context) {
        String url = BaseURL.concat("DoGetUserHomeDetails");
        Map map = new HashMap();
        map.put("UserID", UserHelper.getUserId(context));
        map.put("Token", UserHelper.getSecurityToken(context));
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

    public void getUserProfileDetail(FollowerRequest body, Listener<FollwerResponse> listener, ErrorListener errorListener, Context context) {
        String url = BaseURL.concat("GetUserProfileDetails");
        Map map = new HashMap();
        map.put("UserID", UserHelper.getUserId(context));
        map.put("Token", UserHelper.getSecurityToken(context));
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

    public void getAllNotifications(NotificationsRequest body, Listener<NotificationsResponse> listener, ErrorListener errorListener, Context context) {
        String url = AppBaseURL.concat("Notifications/GetAllUserNotifications");
        Map map = new HashMap();
        map.put("UserID", UserHelper.getUserId(context));
        map.put("Token", UserHelper.getSecurityToken(context));
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

    public void getBookDetails(BookDetailRequest body, Listener<BookDetailResponse> listener, ErrorListener errorListener, Context context) {
        String url = AppBaseURL.concat("Books/GetBookDetails");
        Map map = new HashMap();
        map.put("UserID", UserHelper.getUserId(context));
        map.put("Token", UserHelper.getSecurityToken(context));
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

    public void getBookForum(ForumRequest body, Listener<ForumResponse> listener, ErrorListener errorListener, Context context) {
        String url = AppBaseURL.concat("Forum/GetBookPosts");
        Map map = new HashMap();
        map.put("UserID", UserHelper.getUserId(context));
        map.put("Token", UserHelper.getSecurityToken(context));
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

    public void addBookPost(AddPostRequest body, Listener<EmptyResponse> listener, ErrorListener errorListener, Context context) {
        String url = AppBaseURL.concat("Forum/AddPost");
        Map map = new HashMap();
        map.put("UserID", UserHelper.getUserId(context));
        map.put("Token", UserHelper.getSecurityToken(context));
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

    public void getForumComments(CommentsRequest body, Listener<CommentsResponse> listener, ErrorListener errorListener, Context context) {
        String url = AppBaseURL.concat("Forum/DoGetPostComments");
        Map map = new HashMap();
        map.put("UserID", UserHelper.getUserId(context));
        map.put("Token", UserHelper.getSecurityToken(context));
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

    public void setUserBook(SetUserBookRequest body, Listener<SetUserBookResponse> listener, ErrorListener errorListener, Context context) {
        String url = AppBaseURL.concat("Books/SetUserBooks");
        Map map = new HashMap();
        map.put("UserID", UserHelper.getUserId(context));
        map.put("Token", UserHelper.getSecurityToken(context));
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

    public void getUserMessagesContacts(MessageListRequest body, Listener<MessageListResponse> listener, ErrorListener errorListener, Context context) {
        String url = AppBaseURL.concat("Messages/GetMessagesContacts");
        Map map = new HashMap();
        map.put("UserID", UserHelper.getUserId(context));
        map.put("Token", UserHelper.getSecurityToken(context));
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

    public void getConversationMessages(ConversationRequest body, Listener<ConversationResponse> listener, ErrorListener errorListener, Context context) {
        String url = AppBaseURL.concat("Messages/ConversationMessages");
        Map map = new HashMap();
        map.put("UserID", UserHelper.getUserId(context));
        map.put("Token", UserHelper.getSecurityToken(context));
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

    public void getNewConversationMessages(ConversationRequest body, Listener<ConversationResponse> listener, ErrorListener errorListener, Context context) {
        String url = AppBaseURL.concat("Messages/GetNewConversationMsgs");
        Map map = new HashMap();
        map.put("UserID", UserHelper.getUserId(context));
        map.put("Token", UserHelper.getSecurityToken(context));
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

    public void getSendMessages(SendMessageRequest body, Listener<EmptyResponse> listener, ErrorListener errorListener, Context context) {
        String url = AppBaseURL.concat("Messages/SendMessage");
        Map map = new HashMap();
        map.put("UserID", UserHelper.getUserId(context));
        map.put("Token", UserHelper.getSecurityToken(context));
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

    public void addComment(AddCommentRequest body, Listener<EmptyResponse> listener, ErrorListener errorListener, Context context) {
        String url = AppBaseURL.concat("Forum/DoAddPostComment");
        Map map = new HashMap();
        map.put("UserID", UserHelper.getUserId(context));
        map.put("Token", UserHelper.getSecurityToken(context));
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

    public void likePost(LikeAndShareRequest body, Listener<EmptyResponse> listener, ErrorListener errorListener, Context context) {
        String url = AppBaseURL.concat("Forum/DoLikePost");
        Map map = new HashMap();
        map.put("UserID", UserHelper.getUserId(context));
        map.put("Token", UserHelper.getSecurityToken(context));
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

    public void sharePost(LikeAndShareRequest body, Listener<EmptyResponse> listener, ErrorListener errorListener, Context context) {
        String url = AppBaseURL.concat("Forum/DoSharePost");
        Map map = new HashMap();
        map.put("UserID", UserHelper.getUserId(context));
        map.put("Token", UserHelper.getSecurityToken(context));
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

    public void followUser(UserFollowRequest body, Listener<EmptyResponse> listener, ErrorListener errorListener, Context context) {
        String url = AppBaseURL.concat("User/SetUserFollower");
        Map map = new HashMap();
        map.put("UserID", UserHelper.getUserId(context));
        map.put("Token", UserHelper.getSecurityToken(context));
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

    public void unFollowUser(UserFollowRequest body, Listener<EmptyResponse> listener, ErrorListener errorListener, Context context) {
        String url = AppBaseURL.concat("User/UnfollowUser");
        Map map = new HashMap();
        map.put("UserID", UserHelper.getUserId(context));
        map.put("Token", UserHelper.getSecurityToken(context));
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

    public void followBook(BookFollowRequest body, Listener<EmptyResponse> listener, ErrorListener errorListener, Context context) {
        String url = AppBaseURL.concat("Books/SetBookFollower");
        Map map = new HashMap();
        map.put("UserID", UserHelper.getUserId(context));
        map.put("Token", UserHelper.getSecurityToken(context));
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

    public void unfollowBook(BookFollowRequest body, Listener<EmptyResponse> listener, ErrorListener errorListener, Context context) {
        String url = AppBaseURL.concat("Books/UnSetBookFollower");
        Map map = new HashMap();
        map.put("UserID", UserHelper.getUserId(context));
        map.put("Token", UserHelper.getSecurityToken(context));
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

    public void unSetUserBook(UnSetUserBookRequest body, Listener<SetUserBookResponse> listener, ErrorListener errorListener, Context context) {
        String url = AppBaseURL.concat("Books/UnSetUserBook");
        Map map = new HashMap();
        map.put("UserID", UserHelper.getUserId(context));
        map.put("Token", UserHelper.getSecurityToken(context));
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

    public void getAllUserFollowers(FriendsRequest body, Listener<FriendsResponse> listener, ErrorListener errorListener, Context context) {
        String url = AppBaseURL.concat("User/GetUserFollowers");
        Map map = new HashMap();
        map.put("UserID", UserHelper.getUserId(context));
        map.put("Token", UserHelper.getSecurityToken(context));
        GsonPostRequest<FriendsResponse> request = new GsonPostRequest<>(
                url,
                body,
                FriendsResponse.class,
                map,
                listener,
                errorListener
        );
        appContext.addToRequestQueue(request);
    }

    public void searchUser(SearchRequest body, Listener<SearchResponse> listener, ErrorListener errorListener, Context context) {
        String url = AppBaseURL.concat("User/SearchUser");
        Map map = new HashMap();
        map.put("UserID", UserHelper.getUserId(context));
        map.put("Token", UserHelper.getSecurityToken(context));
        GsonPostRequest<SearchResponse> request = new GsonPostRequest<>(
                url,
                body,
                SearchResponse.class,
                map,
                listener,
                errorListener
        );
        appContext.addToRequestQueue(request);
    }

    public void editProfile(EditProfileRequest body, Listener<EditProfileResponse> listener, ErrorListener errorListener, Context context) {
        String url = AppBaseURL.concat("User/EditProfileDetails");
        Map map = new HashMap();
        map.put("UserID", UserHelper.getUserId(context));
        map.put("Token", UserHelper.getSecurityToken(context));
        GsonPostRequest<EditProfileResponse> request = new GsonPostRequest<>(
                url,
                body,
                EditProfileResponse.class,
                map,
                listener,
                errorListener
        );
        appContext.addToRequestQueue(request);
    }

    public void editProfilePicture(EditProfilePictureRequest body, Listener<EditProfilePictureResponse> listener, ErrorListener errorListener, Context context) {
        String url = AppBaseURL.concat("User/EditProfilePictureDetails/?UserID=" + UserHelper.getUserId(context));
        Map map = new HashMap();
        map.put("UserID", UserHelper.getUserId(context));
        map.put("Token", UserHelper.getSecurityToken(context));
        GsonPostRequest<EditProfilePictureResponse> request = new GsonPostRequest<>(
                url,
                body,
                EditProfilePictureResponse.class,
                map,
                listener,
                errorListener
        );
        appContext.addToRequestQueue(request);
    }
    public void getUnreadeMessagesNum(NewsRequest body, Listener<UnreadeMessageNumResponse> listener, ErrorListener errorListener, Context context) {
        String url = AppBaseURL.concat("Messages/UserUnreadMessagesNumber");
        Map map = new HashMap();
        map.put("UserID", UserHelper.getUserId(context));
        map.put("Token", UserHelper.getSecurityToken(context));
        GsonPostRequest<UnreadeMessageNumResponse> request = new GsonPostRequest<>(
                url,
                body,
                UnreadeMessageNumResponse.class,
                map,
                listener,
                errorListener
        );
        appContext.addToRequestQueue(request);
    }
    public void getNotificationNum(NewsRequest body, Listener<UnreadeNotificationNumResponse> listener, ErrorListener errorListener, Context context) {
        String url = AppBaseURL.concat("Notifications/GetNoitificationNumber");
        Map map = new HashMap();
        map.put("UserID", UserHelper.getUserId(context));
        map.put("Token", UserHelper.getSecurityToken(context));
        GsonPostRequest<UnreadeNotificationNumResponse> request = new GsonPostRequest<>(
                url,
                body,
                UnreadeNotificationNumResponse.class,
                map,
                listener,
                errorListener
        );
        appContext.addToRequestQueue(request);
    }

}

package yackeen.education.ta3allam.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.squareup.picasso.Picasso;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import yackeen.education.ta3allam.Capsule.Book;
import yackeen.education.ta3allam.Capsule.Follower;
import yackeen.education.ta3allam.Capsule.Message;
import yackeen.education.ta3allam.Capsule.UserBooks;
import yackeen.education.ta3allam.R;
import yackeen.education.ta3allam.adapter.FirstLoginAdapter2;
import yackeen.education.ta3allam.adapter.FollowersAdapter;
import yackeen.education.ta3allam.adapter.GridCoursesAdapter;
import yackeen.education.ta3allam.model.dto.request.BookFollowRequest;
import yackeen.education.ta3allam.model.dto.request.FollowerRequest;
import yackeen.education.ta3allam.model.dto.request.UserFollowRequest;
import yackeen.education.ta3allam.model.dto.response.ChatNotificationResponse;
import yackeen.education.ta3allam.model.dto.response.EmptyResponse;
import yackeen.education.ta3allam.model.dto.response.FollwerResponse;
import yackeen.education.ta3allam.server.api.API;
import yackeen.education.ta3allam.ui.Fragment.Profile;
import yackeen.education.ta3allam.util.UserHelper;


public class UserProfileActivity extends AppCompatActivity {
    public static final String TAG = UserProfileActivity.class.getSimpleName();

    private static final String Userparam ="userID";
    private Profile.OnFragmentInteractionListener mListener;
    private RecyclerView followerRecyclerView;
    private FollowersAdapter followersAdapter;
    private Button followButton;
    private Button messageButton;
    private TextView name;
    private TextView about;
    private TextView aboutWord;
    private TextView studyNow;
    private TextView showAll;
    ImageView userImage;
    GridView gridView;
    GridCoursesAdapter gridCoursesAdapter;
    private String USERID;
    FollwerResponse detailResponse;
     TextView followerNumTextView;
    private Toolbar toolbar;
    private List<UserBooks> userBooksList;
    private Button wallButton;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
        USERID = getUSERIDFromId();
        toolbar = (Toolbar)findViewById(R.id.user_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        name = (TextView)findViewById(R.id.user_name_text);
        followButton = (Button) findViewById(R.id.follow_user_button);
        messageButton = (Button) findViewById(R.id.message_user_button);
        wallButton = (Button) findViewById(R.id.btn_wall);
        followerNumTextView = (TextView) findViewById(R.id.follower_num_rounded_text);
        followerNumTextView.setVisibility(View.INVISIBLE);
        showAll = (TextView)findViewById(R.id.textView_watch_all);
        showAll.setVisibility(View.INVISIBLE);
        showAll.setEnabled(false);
        about = (TextView)findViewById(R.id.about);
        aboutWord = (TextView)findViewById(R.id.about_word);
        studyNow = (TextView)findViewById(R.id.study_now);
        if (UserHelper.getUserType(UserProfileActivity.this) == 1){
            about.setEnabled(false);
            about.setVisibility(View.GONE);
            aboutWord.setVisibility(View.GONE);
            studyNow.setText(getResources().getString(R.string.study_now));
        }
        userImage=(ImageView)findViewById(R.id.user_profile_image);
        gridView =(GridView)findViewById(R.id.courses_grid);
        gridCoursesAdapter = new GridCoursesAdapter(this, R.layout.grid_item_laayout);
        gridView.setAdapter(gridCoursesAdapter);
        followerRecyclerView = (RecyclerView)findViewById(R.id.followers_list);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.HORIZONTAL);
        followerRecyclerView.setLayoutManager(manager);
        followerRecyclerView.hasFixedSize();
        followersAdapter = new FollowersAdapter(this);
        initiateOnClickListener();
        feachDataFromApi();
        followerRecyclerView.setAdapter(followersAdapter);



    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId()==android.R.id.home){
            this.finish();
        }
        return super.onOptionsItemSelected(item);
    }
    public void initiateOnClickListener(){
        followButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                followButton.setEnabled(false);
                if (detailResponse.isFollowing()){
                    unfollowUsingApi();
                }else{
                    followUsingApi();
                }

            }
        });
        messageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = ChatActivity.newUserChatIntent(UserProfileActivity.this,USERID);
                startActivity(intent);
            }
        });
        showAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =AllBooksActivity.newِAllBooksIntent(UserProfileActivity.this,userBooksList);
                startActivity(intent);
            }
        });
        wallButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = UserPostsActivity.newPostsIntent(UserProfileActivity.this,USERID);
                startActivity(intent);
            }
        });
    }
    public void addListener(){
        followerNumTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= FriendsActivity.newFriendsIntent(UserProfileActivity.this,UserHelper.getUserId(UserProfileActivity.this));
                startActivity(intent);
            }
        });

    }
    public static Intent newUserProfileIntent(Context context, String userID){
        Intent intent = new Intent(context,UserProfileActivity.class);
        intent.putExtra(Userparam,userID);
        return intent;
    }
    private String getUSERIDFromId() {
        Intent intent = getIntent();
       String user = intent.getStringExtra(Userparam);
        return user;
    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(ChatNotificationResponse notificationResponse) {
        if (notificationResponse.type==2) {
            Log.d(TAG, "onMessageEvent: ");
            feachDataFromApi();
        }

    }
    public void feachDataFromApi(){
        FollowerRequest body = new FollowerRequest();
        body.setUserID(UserHelper.getUserId(this));
        body.setSelectedUserID(USERID);
        API.getUserAPIs().getUserProfileDetail(body,getProfileDataListener(),
                getProfileDataFailedListener(),this);

    }

    public void followUsingApi(){
        UserFollowRequest body = new UserFollowRequest();
        body.setUserID(UserHelper.getUserId(this));
        body.setSelectedUserID(USERID);
        API.getUserAPIs().followUser(body,getfollowListener(),
                getProfileDataFailedListener(),this);
    }
    public void unfollowUsingApi(){
        UserFollowRequest body = new UserFollowRequest();
        body.setUserID(UserHelper.getUserId(this));
        body.setSelectedUserID(USERID);
        API.getUserAPIs().unFollowUser(body,getunfollowListener(),
                getProfileDataFailedListener(),this);
    }
    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        EventBus.getDefault().unregister(this);
        super.onStop();
    }
    public void addView(FollwerResponse response){
        name.setText(response.getUserName());
//        level.setText(response.getUserType());
        about.setText(response.getAbout());
        Picasso.with(this).load(response.getUserPictureURL())
                .placeholder(R.drawable.default_emam_larg)
                .error(R.drawable.default_emam_larg)
                .into(userImage);

    }
    //network response
    private Response.Listener<FollwerResponse> getProfileDataListener(){
        return new Response.Listener<FollwerResponse>() {
            @Override
            public void onResponse(FollwerResponse response) {
                if (response.isSuccess()) {
                    Log.e(TAG, "network_response:" + response.followers.size());
                    detailResponse = response;
                    if (detailResponse.isFollowing()){
                        followButton.setBackgroundDrawable(getResources().getDrawable(R.drawable.send_orage_rounded_button));
                        followButton.setText(R.string.alreadyafollower);
                    }
                    addView(response);
                    List<Follower> followers = new ArrayList<>();
                    userBooksList = new ArrayList<>();
                    if (response.userBooksList.size() > 4) {
                        userBooksList.clear();
                        showAll.setEnabled(true);
                        showAll.setVisibility(View.VISIBLE);
                        for (int i = 0; i < 4; i++) {
                            userBooksList.add(response.userBooksList.get(i));
                        }
                    } else {
                        userBooksList.clear();
                        userBooksList.addAll(response.userBooksList);
                    }
                    if (response.getFollowersNumber() > 6) {
                        followerNumTextView.setVisibility(View.VISIBLE);
                        addListener();
                        followers.clear();
                        int followernum = response.getFollowersNumber() - 6;
                        followerNumTextView.setText("+" + followernum);
                        for (int i = 0; i < 6; i++) {
                            followers.add(response.followers.get(i));
                        }
                    } else {
                        followers.clear();
                        followers.addAll(response.followers);
                    }
                    followersAdapter.addAll(followers);
                    gridCoursesAdapter.clear();
                    gridCoursesAdapter.addAll(userBooksList);
                    gridCoursesAdapter.notifyDataSetChanged();
                }
            }
        };

    }
    private Response.ErrorListener getProfileDataFailedListener(){
        return new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "onErrorResponse: ".concat(error.toString()));
                Toast.makeText(UserProfileActivity.this, yackeen.education.ta3allam.R.string.network_error, Toast.LENGTH_SHORT).show();

            }
        };
    }
    //follow response
    private Response.Listener<EmptyResponse> getfollowListener(){

        return new Response.Listener<EmptyResponse>() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onResponse(EmptyResponse response) {
                followButton.setEnabled(true);
                followButton.setBackground(getResources().getDrawable(R.drawable.send_orage_rounded_button));
                followButton.setText(R.string.alreadyafollower);
                Log.e(TAG, "onResponse: user_followed");



            }
        };
    }
    private Response.Listener<EmptyResponse> getunfollowListener(){

        return new Response.Listener<EmptyResponse>() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onResponse(EmptyResponse response) {

                followButton.setBackground(getResources().getDrawable(R.drawable.rounded_buton_with_white_border));
                followButton.setText(R.string.follow_button_text);
                Log.e(TAG, "onResponse: userUnFollowed");

            }
        };
    }


}

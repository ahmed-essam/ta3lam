package yackeen.education.ta3allam.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import yackeen.education.ta3allam.Capsule.Comment;
import yackeen.education.ta3allam.Capsule.FollowerDetail;
import yackeen.education.ta3allam.Capsule.Message;
import yackeen.education.ta3allam.R;
import yackeen.education.ta3allam.adapter.FriendsAdapter;
import yackeen.education.ta3allam.model.dto.request.CommentsRequest;
import yackeen.education.ta3allam.model.dto.request.FriendsRequest;
import yackeen.education.ta3allam.model.dto.response.ChatNotificationResponse;
import yackeen.education.ta3allam.model.dto.response.CommentsResponse;
import yackeen.education.ta3allam.model.dto.response.FriendsResponse;
import yackeen.education.ta3allam.server.api.API;
import yackeen.education.ta3allam.util.UserHelper;

public class FriendsActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private RecyclerView friendsRecyclerView;
    private FriendsAdapter friendsAdapter;
    private String selectedUserId;
    private static final String ARGID ="selected_id";
    private String TAG="forum_comments_activity";
private TextView noDataText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friends);
        createView();
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        selectedUserId = getUSERIDFromIntent();
        feachFollowersFromApi();
        friendsRecyclerView.setAdapter(friendsAdapter);

    }

    public void createView(){
        toolbar = (Toolbar)findViewById(R.id.friends_toolbar);
        toolbar.setNavigationIcon(R.drawable.close);
        friendsRecyclerView = (RecyclerView)findViewById(R.id.friends_recycler_view);
        friendsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        friendsAdapter = new FriendsAdapter(this);
        noDataText = (TextView) findViewById(R.id.no_data_text);
        noDataText.setEnabled(false);
        noDataText.setVisibility(View.INVISIBLE);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId()==android.R.id.home){
            this.finish();
        }
        return super.onOptionsItemSelected(item);
    }

    public static Intent newFriendsIntent(Context context, String userID){
        Intent intent = new Intent(context,FriendsActivity.class);
        intent.putExtra(ARGID,userID);
        return intent;
    }
    private String getUSERIDFromIntent() {
        Intent intent = getIntent();
        String user = intent.getStringExtra(ARGID);
        return user;
    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(ChatNotificationResponse notificationResponse) {
        Log.d(TAG, "onMessageEvent: ");
        if (notificationResponse.type==2) {
            feachFollowersFromApi();
        }
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
    //network call
    public void feachFollowersFromApi(){
        FriendsRequest body = new FriendsRequest();
        body.setSelectedUserID(selectedUserId);
        API.getUserAPIs().getAllUserFollowers(body,getCommentsListener(),
                getCommentsFailedListener(),this);

    }
    //network response
    private Response.Listener<FriendsResponse> getCommentsListener(){
        return new Response.Listener<FriendsResponse>() {
            @Override
            public void onResponse(FriendsResponse response) {
                if (response.followersDetails.size() == 0){
                    noDataText.setVisibility(View.VISIBLE);
                    noDataText.setText("لاتوجد بيانات");
                    noDataText.setEnabled(true);
                }
                Log.e(TAG,"network_response:"+response.followersDetails.size());
                List<FollowerDetail> followerList = response.followersDetails;
                Log.d(TAG,"network_response:"+followerList.size());
                friendsAdapter.addAll(followerList);

            }
        };
    }
    private Response.ErrorListener getCommentsFailedListener(){
        return new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                noDataText.setVisibility(View.VISIBLE);
                noDataText.setText("خطأ بالشبكه");
                noDataText.setEnabled(true);
                Log.e(TAG, "onErrorResponse: ".concat(error.toString()));
                Toast.makeText(FriendsActivity.this, R.string.network_error, Toast.LENGTH_SHORT).show();

            }
        };
    }

}

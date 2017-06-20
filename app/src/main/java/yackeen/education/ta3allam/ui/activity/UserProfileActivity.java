package yackeen.education.ta3allam.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import yackeen.education.ta3allam.Capsule.Follower;
import yackeen.education.ta3allam.Capsule.UserBooks;
import yackeen.education.ta3allam.R;
import yackeen.education.ta3allam.adapter.FollowersAdapter;
import yackeen.education.ta3allam.adapter.GridCoursesAdapter;
import yackeen.education.ta3allam.model.dto.request.FollowerRequest;
import yackeen.education.ta3allam.model.dto.response.FollwerResponse;
import yackeen.education.ta3allam.server.api.API;
import yackeen.education.ta3allam.ui.Fragment.Profile;
import yackeen.education.ta3allam.util.UserHelper;

import static com.google.android.gms.plus.PlusOneDummyView.TAG;

public class UserProfileActivity extends AppCompatActivity {
    private static final String Userparam ="userID";
    private Profile.OnFragmentInteractionListener mListener;
    private RecyclerView followerRecyclerView;
    private FollowersAdapter followersAdapter;
    private TextView name;
    private TextView about;
    CircleImageView userImage;
    GridView gridView;
    GridCoursesAdapter gridCoursesAdapter;
    private String USERID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
        USERID = getUSERIDFromId();
        name = (TextView)findViewById(yackeen.education.ta3allam.R.id.user_name_text);
        about = (TextView)findViewById(yackeen.education.ta3allam.R.id.about);
        userImage=(CircleImageView)findViewById(yackeen.education.ta3allam.R.id.user_profile_image);
        gridView =(GridView)findViewById(yackeen.education.ta3allam.R.id.courses_grid);
        gridCoursesAdapter = new GridCoursesAdapter(this, yackeen.education.ta3allam.R.layout.grid_item_laayout);
        gridView.setAdapter(gridCoursesAdapter);
        followerRecyclerView = (RecyclerView)findViewById(yackeen.education.ta3allam.R.id.followers_list);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.HORIZONTAL);
        followerRecyclerView.setLayoutManager(manager);
        followerRecyclerView.hasFixedSize();
        followersAdapter = new FollowersAdapter(this);
        feachDataFromApi();
        followerRecyclerView.setAdapter(followersAdapter);
        List<UserBooks> userBooksList = new ArrayList<>();
        UserBooks userBooks = new UserBooks();
        userBooks.setBookName("النهضه");
        userBooks.setTeacherName("احمد الطيب");
        userBooks.setPercentage(0.37);
        userBooksList.add(userBooks);
        gridCoursesAdapter.clear();
        gridCoursesAdapter.addAll(userBooksList);
        gridCoursesAdapter.notifyDataSetChanged();

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
    public void feachDataFromApi(){
        FollowerRequest body = new FollowerRequest();
        body.setUserID(UserHelper.getUserId(this));
        body.setSelectedUserID(USERID);
        API.getUserAPIs().getUserProfileDetail(body,getProfileDataListener(),
                getProfileDataFailedListener(),this);

    }
    public void addView(FollwerResponse response){
        name.setText(response.getUserName());
//        level.setText(response.getUserType());
        about.setText(response.getAbout());
        Picasso.with(this).load(response.getUserPictureURL()).error(yackeen.education.ta3allam.R.drawable.default_emam).into(userImage);

    }
    //network response
    private Response.Listener<FollwerResponse> getProfileDataListener(){
        return new Response.Listener<FollwerResponse>() {
            @Override
            public void onResponse(FollwerResponse response) {
                Log.e(TAG, "network_response:" + response.followers.size());
                addView(response);
                List<Follower> followers = response.followers;
                Log.d(TAG, "network_response:" + followers.size());
                followersAdapter.addAll(followers);
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


}

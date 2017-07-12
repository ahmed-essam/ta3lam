package yackeen.education.ta3allam.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
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

import java.util.List;

import yackeen.education.ta3allam.Capsule.News;
import yackeen.education.ta3allam.R;
import yackeen.education.ta3allam.adapter.FirstLoginAdapter2;
import yackeen.education.ta3allam.adapter.NewsAdapter;
import yackeen.education.ta3allam.model.dto.request.NewsRequest;
import yackeen.education.ta3allam.model.dto.response.NewsResponse;
import yackeen.education.ta3allam.server.api.API;
import yackeen.education.ta3allam.util.EndlessRecyclerViewScrollListener;
import yackeen.education.ta3allam.util.UserHelper;


public class UserPostsActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {
    public static final String TAG = UserPostsActivity.class.getSimpleName();

    private Toolbar toolbar;
    private TextView noDataText;
    private RecyclerView postsRecyclerView;
    private NewsAdapter newsAdapter;
    private String userId;
    private static final String USERIDARG ="user_id";
    SwipeRefreshLayout mSwipeRefreshLayout;
    private EndlessRecyclerViewScrollListener scrollListener;

    int postId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_posts);
        getUserIdFromIntent();
        toolbar = (Toolbar)findViewById(R.id.posts_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.refresh);
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mSwipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                mSwipeRefreshLayout.setRefreshing(true);
            }
        });

        noDataText = (TextView)findViewById(R.id.no_data_text);
        noDataText.setEnabled(false);
        noDataText.setVisibility(View.INVISIBLE);
        postsRecyclerView = (RecyclerView)findViewById(R.id.posts_recyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        postsRecyclerView.setLayoutManager(layoutManager);
        scrollListener = new EndlessRecyclerViewScrollListener(layoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                // Triggered only when new data needs to be appended to the list
                // Add whatever code is needed to append new items to the bottom of the list
                feachNewsFromApi(postId);
            }
        };
        newsAdapter = new NewsAdapter(this);
        postId=0;
        feachNewsFromApi(postId);
        postsRecyclerView.setAdapter(newsAdapter);
        postsRecyclerView.addOnScrollListener(scrollListener);

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId()==android.R.id.home){
            this.finish();
        }
        return super.onOptionsItemSelected(item);
    }
    public static Intent newPostsIntent(Context context,String userID){
        Intent intent = new Intent(context,UserPostsActivity.class);
        intent.putExtra(USERIDARG,userID);
        return intent;
    }
    public void getUserIdFromIntent(){
        userId = getIntent().getStringExtra(USERIDARG);
    }
    public void feachNewsFromApi(int postId){
        NewsRequest body = new NewsRequest();
        body.setUserID(UserHelper.getUserId(this));
        body.setPostID(postId);
        Log.e(TAG, "FeachNewsFromApi: "+body.getUserID());
        API.getUserAPIs().getAllNews(body,getNewsListener(),
                getNewsFailedListener(),this);
    }
    private Response.Listener<NewsResponse> getNewsListener(){
        return new Response.Listener<NewsResponse>() {
            @Override
            public void onResponse(NewsResponse response) {
                if (response.HomePosts.size() == 0){
                    noDataText.setVisibility(View.VISIBLE);
                    noDataText.setText("لاتوجد بيانات");
                    noDataText.setEnabled(true);
                }
                Log.e(TAG,"network_response:"+response.HomePosts.size());
                List<News> newsList = response.HomePosts;
                Log.d(TAG,"network_response:"+newsList.size());
                postId = newsList.get(newsList.size()-1).getPostId();
                newsAdapter.addAll(newsList);
                mSwipeRefreshLayout.setRefreshing(false);
            }

        };
    }
    private Response.ErrorListener getNewsFailedListener(){
        return new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                noDataText.setVisibility(View.VISIBLE);
                noDataText.setText("خطأ بالشبكه");
                noDataText.setEnabled(true);
                Log.e(TAG, "onErrorResponse: ".concat(error.toString()));
                Toast.makeText(UserPostsActivity.this, R.string.network_error, Toast.LENGTH_SHORT).show();
                mSwipeRefreshLayout.setRefreshing(false);

            }
        };
    }

    @Override
    public void onRefresh() {
        postId = 0;
        feachNewsFromApi(postId);
    }
}

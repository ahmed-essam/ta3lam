package com.yackeen.ta3allam.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.android.gms.vision.text.Text;
import com.yackeen.ta3allam.Capsule.Book;
import com.yackeen.ta3allam.Capsule.BookDetail;
import com.yackeen.ta3allam.Capsule.News;
import com.yackeen.ta3allam.R;
import com.yackeen.ta3allam.adapter.ForumAdapter;
import com.yackeen.ta3allam.model.dto.request.BookDetailRequest;
import com.yackeen.ta3allam.model.dto.request.ForumRequest;
import com.yackeen.ta3allam.model.dto.response.BookDetailResponse;
import com.yackeen.ta3allam.model.dto.response.ForumResponse;
import com.yackeen.ta3allam.server.api.API;
import com.yackeen.ta3allam.util.UserHelper;

import java.util.List;

public class ForumsShowActivity extends AppCompatActivity {
    private final static String ARG_BOOK_id="bookId";
    private final String TAG ="Forum_show_activity";
    private int BookID;
    private TextView emamName;
    private TextView bookName;
    private TextView courseName;
    private RecyclerView forumRecyclerView;
    private ForumAdapter forumAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forums_show);
        BookID=retrieveDataFromIntent();
        emamName= (TextView)findViewById(R.id.emam_name_forum);
        bookName=(TextView)findViewById(R.id.book_name_forum);
        forumRecyclerView =(RecyclerView)findViewById(R.id.forum_recyclerView);
        forumRecyclerView.setHasFixedSize(true);
        forumRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        forumAdapter= new ForumAdapter(ForumsShowActivity.this);
        feachBookForumFromApi();
        forumRecyclerView.setAdapter(forumAdapter);

    }
    public void feachBookForumFromApi(){
        ForumRequest body = new ForumRequest();
        body.setBookID(BookID);
        body.setUserID(UserHelper.getUserId(this));
        API.getUserAPIs().getBookForum(body,getBookForumListener(),
                getBookForumFailedListener(),ForumsShowActivity.this);


    }
    public static Intent newIntentForum(Context context,int bookId){
        Intent intent = new Intent(context,ForumsShowActivity.class);
        intent.putExtra(ARG_BOOK_id,bookId);
        return intent;
    }
    public int retrieveDataFromIntent(){

        Intent intent=this.getIntent();
        int bookId =  intent.getIntExtra(ARG_BOOK_id,0);
        return bookId;
    }

    private Response.Listener<ForumResponse> getBookForumListener(){

        return new Response.Listener<ForumResponse>() {
            @Override
            public void onResponse(ForumResponse response) {
                Log.e(TAG, "onResponse: "+response.getBookName());
                emamName.setText(response.getTeacherName());
                bookName.setText(response.getBookName());
                List<News> posts = response.BookPosts;
                forumAdapter.addAll(posts);

            }
        };
    }
    private Response.ErrorListener getBookForumFailedListener(){

        return new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "onErrorResponse: ".concat(error.toString()));

                Toast.makeText(ForumsShowActivity.this, R.string.network_error, Toast.LENGTH_SHORT).show();
            }
        };
    }
}

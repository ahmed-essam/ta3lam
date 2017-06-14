package com.yackeen.ta3allam.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.squareup.picasso.Picasso;
import com.yackeen.ta3allam.Capsule.Category;
import com.yackeen.ta3allam.Capsule.Comment;
import com.yackeen.ta3allam.Capsule.News;
import com.yackeen.ta3allam.R;
import com.yackeen.ta3allam.adapter.CommentsAdapter;
import com.yackeen.ta3allam.model.dto.request.CommentsRequest;
import com.yackeen.ta3allam.model.dto.request.FirstLogin1Request;
import com.yackeen.ta3allam.model.dto.response.CommentsResponse;
import com.yackeen.ta3allam.model.dto.response.FirstLoginResponse1;
import com.yackeen.ta3allam.server.api.API;
import com.yackeen.ta3allam.util.UserHelper;

import java.util.List;

import static com.raizlabs.android.dbflow.config.FlowManager.getContext;

public class ForumComentsActivity extends AppCompatActivity {
    private static final String ARG_COMMENT = "postID";
    private String TAG="forum_comments_activity";
    private ImageView profileImageView;
    private TextView emmamName;
    private TextView courseNmae;
    private TextView detailTextView;
    private RecyclerView commentsRecyclerView;
    private TextView shareNum;
    private TextView likeNum;
    private TextView commentsNum;
    private EditText addComment;
    private Button addCommentButton;
    private CommentsAdapter commentsAdapter;

    private News news;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forum_coments);
        bindViewToLayout();
        news = retriveDataIntent();
        commentsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        commentsAdapter = new CommentsAdapter(this);
        feachCommentsFromApi();
        commentsRecyclerView.setAdapter(commentsAdapter);

    }
    public void addValueToViews(){
        Picasso.with(this).load(news.getImage()).error(R.drawable.default_emam).into(profileImageView);
        emmamName.setText(news.getName());
        detailTextView.setText(news.getDescription());
        shareNum.setText(Integer.toString(news.getShare()));
        likeNum.setText(Integer.toString(news.getLike()));
        commentsNum.setText(Integer.toString(news.getComment()));
    }
    public void feachCommentsFromApi(){
        CommentsRequest body = new CommentsRequest();
        body.setUserID(UserHelper.getUserId(this));
        body.setPostID(news.getPostId());
        API.getUserAPIs().getForumComments(body,getCommentsListener(),
                getCommentsFailedListener(),this);


    }
    public Intent newCommentIntent(Context context,News news){
        Intent intent = new Intent(context,ForumComentsActivity.class);
        intent.putExtra(ARG_COMMENT,news);
        return intent;
    }
    public News retriveDataIntent(){
        Intent intent = getIntent();
        return (News) intent.getSerializableExtra(ARG_COMMENT);
    }
    public void bindViewToLayout(){
        profileImageView = (ImageView)findViewById(R.id.profile_image_comment);
        emmamName= (TextView)findViewById(R.id.emam_name_comments);
        commentsRecyclerView=(RecyclerView)findViewById(R.id.comments_recyclerView);
        courseNmae= (TextView)findViewById(R.id.course_name_comments);
        detailTextView=(TextView)findViewById(R.id.descritption);
        shareNum = (TextView)findViewById(R.id.share);
        likeNum=(TextView)findViewById(R.id.like);
        commentsNum=(TextView)findViewById(R.id.comment);
        addComment = (EditText)findViewById(R.id.add_comment_edit);
        addCommentButton=(Button)findViewById(R.id.add_comment_button);


    }
    //network response
    private Response.Listener<CommentsResponse> getCommentsListener(){
        return new Response.Listener<CommentsResponse>() {
            @Override
            public void onResponse(CommentsResponse response) {
                Log.e(TAG,"network_response:"+response.comments);
                List<Comment> commentList = response.comments;
                Log.d(TAG,"network_response:"+commentList.size());
                commentsAdapter.addAll(commentList);

            }
        };
    }
    private Response.ErrorListener getCommentsFailedListener(){
        return new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "onErrorResponse: ".concat(error.toString()));
                Toast.makeText(ForumComentsActivity.this, R.string.network_error, Toast.LENGTH_SHORT).show();

            }
        };
    }
}

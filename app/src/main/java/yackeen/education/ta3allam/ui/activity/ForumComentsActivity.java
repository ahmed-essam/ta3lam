package yackeen.education.ta3allam.ui.activity;

import android.app.Activity;
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
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.squareup.picasso.Picasso;

import yackeen.education.ta3allam.Capsule.AddedComment;
import yackeen.education.ta3allam.Capsule.Comment;
import yackeen.education.ta3allam.Capsule.News;
import yackeen.education.ta3allam.Capsule.SendMessage;
import yackeen.education.ta3allam.R;
import yackeen.education.ta3allam.adapter.CommentsAdapter;
import yackeen.education.ta3allam.model.dto.request.AddCommentRequest;
import yackeen.education.ta3allam.model.dto.request.CommentsRequest;
import yackeen.education.ta3allam.model.dto.request.SendMessageRequest;
import yackeen.education.ta3allam.model.dto.response.CommentsResponse;
import yackeen.education.ta3allam.model.dto.response.EmptyResponse;
import yackeen.education.ta3allam.server.api.API;
import yackeen.education.ta3allam.util.UserHelper;

import java.util.Date;
import java.util.List;

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
    private EditText addCommentEditText;
    private Button addCommentButton;
    private CommentsAdapter commentsAdapter;
    private Toolbar toolbar;


    private News news;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forum_coments);
        bindViewToLayout();
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        news = retriveDataIntent();
        addValueToViews();
        commentsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        commentsAdapter = new CommentsAdapter(this);
        feachCommentsFromApi();
        commentsRecyclerView.setAdapter(commentsAdapter);
        addCommentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (addCommentEditText.getText() != null) {
                    AddedComment comment = new AddedComment();
                    comment.setComment(addCommentEditText.getText().toString());
                    comment.setPostID(news.getPostId());
                    addCommentUsingAPI(comment);
                }
            }
        });

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId()==android.R.id.home){
            this.finish();
        }
        return super.onOptionsItemSelected(item);
    }

    public void addValueToViews(){
        Picasso.with(this).load(news.getImage()).error(R.drawable.default_emam).into(profileImageView);
        emmamName.setText(news.getName());
        detailTextView.setText(news.getDescription());
        shareNum.setText(Integer.toString(news.getShare()));
        likeNum.setText(Integer.toString(news.getLike()));
        commentsNum.setText(Integer.toString(news.getComment()));
    }
    //network call
    public void feachCommentsFromApi(){
        CommentsRequest body = new CommentsRequest();
        body.setUserID(UserHelper.getUserId(this));
        body.setPostID(news.getPostId());
        Log.e(TAG, "feachCommentsFromApi:"+body.getPostID());
        API.getUserAPIs().getForumComments(body,getCommentsListener(),
                getCommentsFailedListener(),this);


    }
    public void addCommentUsingAPI(AddedComment addedComment){
        AddCommentRequest body = new AddCommentRequest();
        body.setUserID(UserHelper.getUserId(this));
        body.setComment(addedComment);
        API.getUserAPIs().addComment(body,getAddCommentListener(),
                getCommentsFailedListener(),this);
    }
    public static Intent newCommentIntent(Context context,News news){
        Intent intent = new Intent(context,ForumComentsActivity.class);
        intent.putExtra(ARG_COMMENT,news);
        return intent;
    }
    public News retriveDataIntent(){
        Intent intent = getIntent();
        News news=(News) intent.getSerializableExtra(ARG_COMMENT);
        Log.e(TAG, "retriveDataIntent: "+news.getPostId() );
        return news;
    }
    public void bindViewToLayout(){
        toolbar =(Toolbar)findViewById(R.id.forum_toolbar);
        toolbar.setNavigationIcon(R.drawable.close);
        profileImageView = (ImageView)findViewById(R.id.profile_image_comment);
        emmamName= (TextView)findViewById(R.id.emam_name_comments);
        commentsRecyclerView=(RecyclerView)findViewById(R.id.comments_recyclerView);
        courseNmae= (TextView)findViewById(R.id.course_name_comments);
        detailTextView=(TextView)findViewById(R.id.descritption);
        shareNum = (TextView)findViewById(R.id.share);
        likeNum=(TextView)findViewById(R.id.like);
        commentsNum=(TextView)findViewById(R.id.comment);
        addCommentEditText = (EditText)findViewById(R.id.add_comment_edit);
        addCommentButton=(Button)findViewById(R.id.add_comment_button);


    }
    public static void hideSoftKeyboard(Activity activity) {
        InputMethodManager inputMethodManager =
                (InputMethodManager) activity.getSystemService(
                        Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(
                activity.getCurrentFocus().getWindowToken(), 0);
    }
    //network response
    private Response.Listener<CommentsResponse> getCommentsListener(){
        return new Response.Listener<CommentsResponse>() {
            @Override
            public void onResponse(CommentsResponse response) {
                Log.e(TAG,"network_response:"+response.PostComments.size());
                List<Comment> commentList = response.PostComments;
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
    private Response.Listener<EmptyResponse> getAddCommentListener(){
        return new Response.Listener<EmptyResponse>() {
            @Override
            public void onResponse(EmptyResponse response) {
                if (response.isSuccess()){
                    Toast.makeText(ForumComentsActivity.this, "comment added", Toast.LENGTH_SHORT).show();
                    Comment comment = new Comment();
                    comment.setUserID(UserHelper.getUserId(ForumComentsActivity.this));
                    comment.setDateTime(Long.toString(new Date().getTime()));
                    comment.setBody(addCommentEditText.getText().toString());
                    commentsAdapter.addItem(comment);
                    addCommentEditText.setText(null);
                    hideSoftKeyboard(ForumComentsActivity.this);
                }
            }
        };
    }
}

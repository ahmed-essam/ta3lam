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
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;

import yackeen.education.ta3allam.Capsule.News;
import yackeen.education.ta3allam.Capsule.Post;
import yackeen.education.ta3allam.R;
import yackeen.education.ta3allam.adapter.ForumAdapter;
import yackeen.education.ta3allam.model.dto.request.AddPostRequest;
import yackeen.education.ta3allam.model.dto.request.ForumRequest;
import yackeen.education.ta3allam.model.dto.response.EmptyResponse;
import yackeen.education.ta3allam.model.dto.response.ForumResponse;
import yackeen.education.ta3allam.server.api.API;
import yackeen.education.ta3allam.util.TextHelper;
import yackeen.education.ta3allam.util.UserHelper;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class ForumsShowActivity extends AppCompatActivity {
    private final static String ARG_BOOK_id="bookId";
    private final String TAG ="Forum_show_activity";
    private int BookID;
    private TextView emamName;
    private TextView bookName;
    private TextView courseName;
    private EditText addPostEdit;
    private Button addPostButton;

    private RecyclerView forumRecyclerView;
    private ForumAdapter forumAdapter;
    private Toolbar forumToolbar;
    private TextView noDataText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(yackeen.education.ta3allam.R.layout.activity_forums_show);
        BookID=retrieveDataFromIntent();
        forumToolbar= (Toolbar) findViewById(R.id.forum_toolbar);
        setSupportActionBar(forumToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        emamName= (TextView)findViewById(yackeen.education.ta3allam.R.id.emam_name_forum);
        bookName=(TextView)findViewById(yackeen.education.ta3allam.R.id.book_name_forum);
        noDataText = (TextView) findViewById(R.id.no_data_text);
        noDataText.setEnabled(false);
        noDataText.setVisibility(View.INVISIBLE);
        addPostEdit=(EditText) findViewById(R.id.add_post);
        addPostButton=(Button) findViewById(R.id.add_post_btton);
        if (UserHelper.getUserType(this)== 1){
            addPostEdit.setEnabled(false);
            addPostEdit.setVisibility(View.INVISIBLE);
            addPostButton.setEnabled(false);
            addPostButton.setVisibility(View.INVISIBLE);
        }
        addPostButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!TextHelper.isEditTextEmpty(new EditText[]{addPostEdit})) {
                    Post post = new Post();
                    post.setBookID(BookID);
                    post.setBody(addPostEdit.getText().toString());
                    addPost(post);
                }
            }
        });
        forumRecyclerView =(RecyclerView)findViewById(yackeen.education.ta3allam.R.id.forum_recyclerView);
        forumRecyclerView.setHasFixedSize(true);
        forumRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        forumAdapter= new ForumAdapter(ForumsShowActivity.this);
        feachBookForumFromApi();
        forumRecyclerView.setAdapter(forumAdapter);


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId()==android.R.id.home){
            this.finish();
        }
        return super.onOptionsItemSelected(item);
    }

    public void feachBookForumFromApi(){
        ForumRequest body = new ForumRequest();
        body.setBookID(BookID);
        body.setUserID(UserHelper.getUserId(this));
        API.getUserAPIs().getBookForum(body,getBookForumListener(),
                getBookForumFailedListener(),ForumsShowActivity.this);


    }
    public void addPost(Post post){
        AddPostRequest body = new AddPostRequest();
        body.setPost(post);
        body.setUserID(UserHelper.getUserId(this));
        API.getUserAPIs().addBookPost(body,getAddPostListener(),
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
    public static void hideSoftKeyboard(Activity activity) {
        InputMethodManager inputMethodManager =
                (InputMethodManager) activity.getSystemService(
                        Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(
                activity.getCurrentFocus().getWindowToken(), 0);
    }

    private Response.Listener<ForumResponse> getBookForumListener(){

        return new Response.Listener<ForumResponse>() {
            @Override
            public void onResponse(ForumResponse response) {
                if (response.BookPosts.size() == 0){
                    noDataText.setVisibility(View.VISIBLE);
                    noDataText.setText("لاتوجد بيانات");
                    noDataText.setEnabled(true);
                }
                Log.e(TAG, "onResponse: "+response.getBookName());
                emamName.setText(response.getTeacherName());
                bookName.setText(response.getBookName());
                List<News> posts = response.BookPosts;
                forumAdapter.addAll(posts);

            }
        };
    }

    private Response.Listener<EmptyResponse> getAddPostListener(){

        return new Response.Listener<EmptyResponse>() {
            @Override
            public void onResponse(EmptyResponse response) {
                if (response.isSuccess()){
                    News news = new News();
                    news.setLiked(false);
                    news.setDescription(addPostEdit.getText().toString());
                    news.setLike(0);
                    news.setComment(0);
                    news.setImage(UserHelper.getPhotoUrl(ForumsShowActivity.this));
                    news.setName(UserHelper.getUserName(ForumsShowActivity.this));
                    DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.S");
                    String dateString = String.valueOf(new Date().getTime());

                    try {
                        news.setTime(String.valueOf(dateFormat.parse(dateString)));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    addPostEdit.setText(null);
                    forumAdapter.addItem(news);
                    hideSoftKeyboard(ForumsShowActivity.this);
                }

            }
        };
    }
    private Response.ErrorListener getBookForumFailedListener(){

        return new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                noDataText.setVisibility(View.VISIBLE);
                noDataText.setText("خطأ بالشبكه");
                noDataText.setEnabled(true);
                Log.e(TAG, "onErrorResponse: ".concat(error.toString()));

                Toast.makeText(ForumsShowActivity.this, yackeen.education.ta3allam.R.string.network_error, Toast.LENGTH_SHORT).show();
            }
        };
    }
}

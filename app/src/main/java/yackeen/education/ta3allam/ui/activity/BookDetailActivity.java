package yackeen.education.ta3allam.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.squareup.picasso.Picasso;
import yackeen.education.ta3allam.Capsule.Book;
import yackeen.education.ta3allam.Capsule.BookDetail;
import yackeen.education.ta3allam.R;
import yackeen.education.ta3allam.model.dto.request.BookDetailRequest;
import yackeen.education.ta3allam.model.dto.request.BookFollowRequest;
import yackeen.education.ta3allam.model.dto.response.BookDetailResponse;
import yackeen.education.ta3allam.model.dto.response.EmptyResponse;
import yackeen.education.ta3allam.server.api.API;
import yackeen.education.ta3allam.util.UserHelper;

public class BookDetailActivity extends AppCompatActivity {
    private final static String ARG_BOOK="bookData";
    private final static String TAG="BookDetail";
    private ImageView forumIcon;
    private ImageView openBookIcon;
    private TextView bookName;
    private TextView bookLevel;
    private TextView bookCategory;
    private Button followButton;
    private Button shareButton;
    private TextView aboutText;
    private ImageView teacherImage;
    private TextView teacherName;
    private TextView teacherPosition;
    private TextView duration;
    private TextView shareNumText;
    private TextView followerNumText;
    private Book book;
    private BookDetail bookDetail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(yackeen.education.ta3allam.R.layout.activity_book_detail);
        addViewToActivity();
        initiateOnClickListener();
        book = retrieveDataFromIntent();
        bookName.setText(book.getName());
        bookLevel.setText(Integer.toString(book.getLevel()));
        bookCategory.setText(book.getCourseName());
        Log.e(TAG, "onCreate: "+ UserHelper.getUserId(this));
        feachBookDetailsFromApi();


    }
    public void feachBookDetailsFromApi(){
        BookDetailRequest body = new BookDetailRequest();
        body.setBookID(book.getId());
        body.setUserID(UserHelper.getUserId(this));
        API.getUserAPIs().getBookDetails(body,getBookDetailListener(), getBookDetailFailedListener(),BookDetailActivity.this);
    }

    public void followBookUsingApi(){
        BookFollowRequest body = new BookFollowRequest();
        body.setBookID(book.getId());
        body.setUserID(UserHelper.getUserId(this));
        API.getUserAPIs().followBook(body,getfollowListener(),
                getBookDetailFailedListener(),BookDetailActivity.this);
    }
    public void unfollowBookUsingApi(){
        BookFollowRequest body = new BookFollowRequest();
        body.setBookID(book.getId());
        body.setUserID(UserHelper.getUserId(this));
        API.getUserAPIs().unfollowBook(body,getfollowListener(),
                getBookDetailFailedListener(),BookDetailActivity.this);
    }
    public void initiateOnClickListener(){
        forumIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = ForumsShowActivity.newIntentForum(BookDetailActivity.this,book.getId());
                startActivity(intent);

            }
        });
        followButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (bookDetail.isFollower()){
                    followBookUsingApi();
                }else{
                    unfollowBookUsingApi();
                }

            }
        });
        shareButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }


    public void addViewToActivity(){
        forumIcon = (ImageView)findViewById(yackeen.education.ta3allam.R.id.form_icon);
        openBookIcon = (ImageView)findViewById(yackeen.education.ta3allam.R.id.open_book);
        bookName = (TextView) findViewById(yackeen.education.ta3allam.R.id.book_name_id_detail);
        bookLevel = (TextView) findViewById(yackeen.education.ta3allam.R.id.book_level);
        bookCategory = (TextView) findViewById(yackeen.education.ta3allam.R.id.book_category);
        followButton=(Button)findViewById(yackeen.education.ta3allam.R.id.follow_book_button);
        shareButton=(Button)findViewById(yackeen.education.ta3allam.R.id.share_book_button);
        aboutText = (TextView) findViewById(yackeen.education.ta3allam.R.id.book_detail_text);
        teacherImage = (ImageView) findViewById(yackeen.education.ta3allam.R.id.teacher_image_detail);
        teacherName = (TextView) findViewById(yackeen.education.ta3allam.R.id.teacher_name_detail);
        teacherPosition = (TextView) findViewById(yackeen.education.ta3allam.R.id.teacher_position_detail);
        duration = (TextView) findViewById(yackeen.education.ta3allam.R.id.book_duration_text);
        shareNumText = (TextView) findViewById(yackeen.education.ta3allam.R.id.book_share_num_text);
        followerNumText = (TextView) findViewById(yackeen.education.ta3allam.R.id.book_follower_num_text);
    }
    public static Intent newDetailIntent(Context context,Book book){
        Intent intent = new Intent(context,BookDetailActivity.class);
        intent.putExtra(ARG_BOOK,book);
        return intent;
    }
    public Book retrieveDataFromIntent(){

        Intent intent=this.getIntent();
        Book book = (Book) intent.getSerializableExtra(ARG_BOOK);
        return book;
    }
    public void addValuesToView(BookDetail bookDetail){
        aboutText.setText(bookDetail.getAbout());
        Picasso.with(this).load(bookDetail.getTeacherPicture()).into(teacherImage);
        teacherName.setText(bookDetail.getTeacherName());
        teacherPosition.setText(bookDetail.getTeacherTitle());
        String durationString = "من"+bookDetail.getFromDate()+"- الي "+bookDetail.getFromDate();
        duration.setText(durationString);
        followerNumText.setText(Integer.toString(bookDetail.getFollowersNumber()));
        shareNumText.setText(Integer.toString(bookDetail.getParticipantsNumber()));
    }

    private Response.Listener<BookDetailResponse> getBookDetailListener(){

        return new Response.Listener<BookDetailResponse>() {
            @Override
            public void onResponse(BookDetailResponse response) {
                bookDetail = response.BookDetails;
                addValuesToView(bookDetail);

            }
        };
    }
    private Response.ErrorListener getBookDetailFailedListener(){

        return new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "onErrorResponse: ".concat(error.toString()));

                Toast.makeText(BookDetailActivity.this, yackeen.education.ta3allam.R.string.network_error, Toast.LENGTH_SHORT).show();
            }
        };
    }
    //follow response
    private Response.Listener<EmptyResponse> getfollowListener(){

        return new Response.Listener<EmptyResponse>() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onResponse(EmptyResponse response) {
                if (bookDetail.isFollower()){
                    followButton.setBackground(getResources().getDrawable(R.drawable.rounded_with_border_button));
                    followButton.setTextColor(getResources().getColor(R.color.colorPrimary));
                    followButton.setText(R.string.alreadyafollower);
                }else{
                    followButton.setBackground(getResources().getDrawable(R.drawable.rounded_buton_with_white_border));
                    followButton.setTextColor(getResources().getColor(R.color.colorTextTitle));
                    followButton.setText(R.string.follow_button_text);
                }


            }
        };
    }

}

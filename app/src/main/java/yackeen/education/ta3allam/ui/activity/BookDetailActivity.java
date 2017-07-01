package yackeen.education.ta3allam.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
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
import yackeen.education.ta3allam.model.dto.request.SetUserBookRequest;
import yackeen.education.ta3allam.model.dto.request.UnSetUserBookRequest;
import yackeen.education.ta3allam.model.dto.response.BookDetailResponse;
import yackeen.education.ta3allam.model.dto.response.EmptyResponse;
import yackeen.education.ta3allam.model.dto.response.SetUserBookResponse;
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
    private TextView fromTextView;
    private TextView toTextView;
    private TextView shareNumText;
    private TextView followerNumText;
    private Book book;
    private BookDetail bookDetail;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(yackeen.education.ta3allam.R.layout.activity_book_detail);
        addViewToActivity();
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        initiateOnClickListener();
        book = retrieveDataFromIntent();
        bookName.setText(book.getName());
        bookLevel.setText(Integer.toString(book.getLevel()));
        bookCategory.setText(book.getCourseName());
        Log.e(TAG, "onCreate: "+ UserHelper.getUserId(this));
        feachBookDetailsFromApi();

        }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId()==android.R.id.home){
            this.finish();
        }
        return super.onOptionsItemSelected(item);
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
        API.getUserAPIs().unfollowBook(body,getunfollowListener(),
                getBookDetailFailedListener(),BookDetailActivity.this);
    }
    public void setUserBooksToApi(int[] bookIds){
        SetUserBookRequest body = new SetUserBookRequest();
        body.setUserID(UserHelper.getUserId(this));
        body.setBooksIDs(bookIds);
        API.getUserAPIs().setUserBook(body,setBookListener(),
                getBookDetailFailedListener(),this);


    }
    public void unSetUserBooksToApi(){
        UnSetUserBookRequest body = new UnSetUserBookRequest();
        body.setUserID(UserHelper.getUserId(this));
        body.setBooksID(bookDetail.getID());
        API.getUserAPIs().unSetUserBook(body,unsetBookListener(),
                getBookDetailFailedListener(),this);


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
                followButton.setEnabled(false);
                if (bookDetail.isFollower()){
                    unfollowBookUsingApi();
                }else{
                    followBookUsingApi();
                }


            }
        });
        shareButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                shareButton.setEnabled(false);
                if (bookDetail.isSubscriber()){
                    unSetUserBooksToApi();
                }else{
                    int[] bookIds={bookDetail.getID()};
                    setUserBooksToApi(bookIds);

                }

            }
        });
    }


    public void addViewToActivity(){
        forumIcon = (ImageView)findViewById(yackeen.education.ta3allam.R.id.form_icon);
        toolbar = (Toolbar) findViewById(R.id.detail_toolbar);
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
        fromTextView = (TextView) findViewById(R.id.from_text);
        toTextView = (TextView) findViewById(R.id.to_text);
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
        String durationfromString = " من "+bookDetail.getFromDate();
        String durationtoString = " الي "+bookDetail.getFromDate();
        fromTextView.setText(durationfromString);
        toTextView.setText(durationtoString);
        followerNumText.setText(Integer.toString(bookDetail.getFollowersNumber()));
        shareNumText.setText(Integer.toString(bookDetail.getParticipantsNumber()));
        if (bookDetail.isFollower()){
            followButton.setBackgroundDrawable(getResources().getDrawable(R.drawable.send_orage_rounded_button));
            followButton.setTextColor(getResources().getColor(R.color.colorTextTitle));
            followButton.setText(R.string.alreadyafollower);
        }else{
            followButton.setBackgroundDrawable(getResources().getDrawable(R.drawable.rounded_buton_with_white_border));
            followButton.setTextColor(getResources().getColor(R.color.colorTextTitle));
            followButton.setText(R.string.follow_button_text);
        }
        if (bookDetail.isSubscriber()){
            shareButton.setBackgroundDrawable(getResources().getDrawable(R.drawable.send_orage_rounded_button));
            shareButton.setTextColor(getResources().getColor(R.color.colorTextTitle));
            shareButton.setText(R.string.alreadysubscribe);
        }else{
            shareButton.setBackgroundDrawable(getResources().getDrawable(R.drawable.rounded_buton_with_white_border));
            shareButton.setTextColor(getResources().getColor(R.color.colorTextTitle));
            shareButton.setText(R.string.share_button_text);
        }
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
                    followButton.setEnabled(true);
                    followButton.setBackgroundDrawable(getResources().getDrawable(R.drawable.send_orage_rounded_button));
                    followButton.setTextColor(getResources().getColor(R.color.colorTextTitle));
                    followButton.setText(R.string.alreadyafollower);



            }
        };
    }
    private Response.Listener<EmptyResponse> getunfollowListener(){

        return new Response.Listener<EmptyResponse>() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onResponse(EmptyResponse response) {
                    followButton.setEnabled(true);
                    followButton.setBackgroundDrawable(getResources().getDrawable(R.drawable.rounded_buton_with_white_border));
                    followButton.setTextColor(getResources().getColor(R.color.colorTextTitle));
                    followButton.setText(R.string.follow_button_text);

            }
        };
    }
    //subscribe response
    private Response.Listener<SetUserBookResponse> setBookListener(){
        return new Response.Listener<SetUserBookResponse>() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onResponse(SetUserBookResponse response) {
                shareButton.setEnabled(true);
                    shareButton.setBackgroundDrawable(getResources().getDrawable(R.drawable.send_orage_rounded_button));
                    shareButton.setTextColor(getResources().getColor(R.color.colorTextTitle));
                    shareButton.setText(R.string.alreadysubscribe);

            }
        };
    }
    private Response.Listener<SetUserBookResponse> unsetBookListener(){
        return new Response.Listener<SetUserBookResponse>() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onResponse(SetUserBookResponse response) {
                shareButton.setEnabled(true);
                shareButton.setBackgroundDrawable(getResources().getDrawable(R.drawable.rounded_buton_with_white_border));
                shareButton.setTextColor(getResources().getColor(R.color.colorTextTitle));
                shareButton.setText(R.string.share_button_text);

            }
        };
    }

}

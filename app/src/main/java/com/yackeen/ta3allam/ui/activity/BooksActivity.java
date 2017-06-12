package com.yackeen.ta3allam.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.yackeen.ta3allam.Capsule.Book;
import com.yackeen.ta3allam.R;
import com.yackeen.ta3allam.adapter.BooksAdapter;
import com.yackeen.ta3allam.model.dto.request.FirstLogin2Request;
import com.yackeen.ta3allam.model.dto.response.FirstLoginResponse2;
import com.yackeen.ta3allam.server.api.API;
import com.yackeen.ta3allam.ui.Fragment.Tracks;

import java.util.List;

/**
 * Created by ahmed essam on 12/06/2017.
 */

public class BooksActivity extends AppCompatActivity{

    private static final String ARG_PARAM1 = "course_id_parameter";
    private static final String ARG_PARAM2 = "param2";
    private RecyclerView booksRecyclerView;
    private BooksAdapter booksAdapter;
    private String TAG="Books_activity";


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        setContentView(R.layout.activity_books);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        booksRecyclerView = (RecyclerView)findViewById(R.id.books_recyclerView);
        booksRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        booksAdapter = new BooksAdapter(this);


    }
    public void feachBooksFromApi(){
        FirstLogin2Request body = new FirstLogin2Request();
        API.getUserAPIs().getAllbooks(body,getCoursesListener(),
                getCoursesFailedListener(),this);
    }

    public static Intent newIntent(Context context, int param1) {
        Intent intent = new Intent(context,BooksActivity.class);
        intent.putExtra(ARG_PARAM1,param1);
        return intent;
    }

    // /network response
    private Response.Listener<FirstLoginResponse2> getCoursesListener(){
        return new Response.Listener<FirstLoginResponse2>() {
            @Override
            public void onResponse(FirstLoginResponse2 response) {
                Log.e(TAG, "network_response:" + response.BooksList.size());
                List<Book> books = response.BooksList;
                Log.d(TAG, "network_response:" + books.size());
                booksAdapter.addAll(books);
            }
        };
    }
    private Response.ErrorListener getCoursesFailedListener(){
        return new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "onErrorResponse: ".concat(error.toString()));
                Toast.makeText(BooksActivity.this, R.string.network_error, Toast.LENGTH_SHORT).show();

            }
        };
    }
}

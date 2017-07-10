package yackeen.education.ta3allam.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.facebook.internal.CollectionMapper;

import yackeen.education.ta3allam.Capsule.Book;
import yackeen.education.ta3allam.R;
import yackeen.education.ta3allam.adapter.BooksAdapter;
import yackeen.education.ta3allam.model.dto.request.FirstLogin2Request;
import yackeen.education.ta3allam.model.dto.response.FirstLoginResponse2;
import yackeen.education.ta3allam.server.api.API;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by ahmed essam on 12/06/2017.
 */

public class BooksActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private static final String ARG_PARAM1 = "course_id_parameter";
    private static final String COURSE_NAME = "course_name";
    private RecyclerView booksRecyclerView;
    private BooksAdapter booksAdapter;
    private TextView courseNameText;
    private String courseName;
    private String TAG = "Books_activity";
    private int corseId;
    private Toolbar bookToolbar;
    private TextView orderBy;
    private Spinner orderSpinner;
    List<Book> books;
    private TextView noDataText;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(yackeen.education.ta3allam.R.layout.activity_books);
        getCourseIdsFromIntent();
        bookToolbar = (Toolbar) findViewById(yackeen.education.ta3allam.R.id.book_toolbar);
        courseNameText = (TextView) findViewById(R.id.course_name);
        courseNameText.setText("" + courseName);
        orderBy = (TextView) findViewById(R.id.order_by);
        orderSpinner = (Spinner) findViewById(R.id.order_spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.planets_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        orderSpinner.setAdapter(adapter);
        orderSpinner.setOnItemSelectedListener(this);
        noDataText = (TextView) findViewById(R.id.no_data_text);
        noDataText.setEnabled(false);
        noDataText.setVisibility(View.INVISIBLE);
        setSupportActionBar(bookToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        booksRecyclerView = (RecyclerView) findViewById(yackeen.education.ta3allam.R.id.books_recyclerView);
        booksRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        booksAdapter = new BooksAdapter(this);
        feachBooksFromApi();
        booksRecyclerView.setAdapter(booksAdapter);


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            this.finish();
        }
        return super.onOptionsItemSelected(item);
    }

    public void feachBooksFromApi() {
        FirstLogin2Request body = new FirstLogin2Request();
        int[] courseIds = {corseId};
        body.setCourseIDs(courseIds);
        API.getUserAPIs().getAllbooks(body, getCoursesListener(),
                getCoursesFailedListener(), this);
    }

    public static Intent newIntent(Context context, int param1, String courseName) {
        Intent intent = new Intent(context, BooksActivity.class);
        intent.putExtra(ARG_PARAM1, param1);
        intent.putExtra(COURSE_NAME, courseName);
        return intent;
    }

    public void getCourseIdsFromIntent() {
        Intent intent = getIntent();
        corseId = intent.getIntExtra(ARG_PARAM1, 1);
        courseName = intent.getStringExtra(COURSE_NAME);
    }

    // /network response
    private Response.Listener<FirstLoginResponse2> getCoursesListener() {
        return new Response.Listener<FirstLoginResponse2>() {
            @Override
            public void onResponse(FirstLoginResponse2 response) {
                if (response.BooksList.size() == 0) {
                    noDataText.setVisibility(View.VISIBLE);
                    noDataText.setText("لاتوجد بيانات");
                    noDataText.setEnabled(true);
                }
                Log.e(TAG, "network_response:" + response.BooksList.size());
                books = response.BooksList;
                Log.d(TAG, "network_response:" + books.size());
                booksAdapter.addAll(books);
            }
        };
    }

    private Response.ErrorListener getCoursesFailedListener() {
        return new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                noDataText.setVisibility(View.VISIBLE);
                noDataText.setText("خطأ بالشبكه");
                noDataText.setEnabled(true);
                Log.e(TAG, "onErrorResponse: ".concat(error.toString()));
                Toast.makeText(BooksActivity.this, yackeen.education.ta3allam.R.string.network_error, Toast.LENGTH_SHORT).show();

            }
        };
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        if (books == null) {
            return;
        }
        if (i == 0) {
            orderBy.setText("المستوي");

            Collections.sort(books, new Comparator<Book>() {
                @Override
                public int compare(Book book, Book t1) {
                    if (book.getLevel() > t1.getLevel()) {
                        return 1;
                    } else if (book.getLevel() < t1.getLevel()) {
                        return -1;
                    } else
                        return 0;
                }
            });
        } else if (i == 1) {
            orderBy.setText("عدد المشتركين");
            Collections.sort(books, new Comparator<Book>() {
                @Override
                public int compare(Book book, Book t1) {
                    if (book.getParticipantsNumber() > t1.getParticipantsNumber()) {
                        return 1;
                    } else if (book.getParticipantsNumber() < t1.getParticipantsNumber()) {
                        return -1;
                    } else
                        return 0;
                }
            });
        }

        booksAdapter.notifyDataSetChanged();

}

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}

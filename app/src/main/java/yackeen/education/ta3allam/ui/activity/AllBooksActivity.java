package yackeen.education.ta3allam.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.GridView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;

import java.io.Serializable;
import java.util.List;

import yackeen.education.ta3allam.Capsule.FollowerDetail;
import yackeen.education.ta3allam.Capsule.UserBooks;
import yackeen.education.ta3allam.R;
import yackeen.education.ta3allam.adapter.FriendsAdapter;
import yackeen.education.ta3allam.adapter.GridCoursesAdapter;
import yackeen.education.ta3allam.model.dto.request.FriendsRequest;
import yackeen.education.ta3allam.model.dto.response.FriendsResponse;
import yackeen.education.ta3allam.server.api.API;

public class AllBooksActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private GridView allBooksGridView;
    private GridCoursesAdapter gridCoursesAdapter;
    private List<UserBooks> userBooks;
    private static final String ARGID ="selected_id";
    private String TAG="forum_comments_activity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_books);
        createView();
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        userBooks = getUSERIDFromIntent();
        gridCoursesAdapter.addAll(userBooks);
        allBooksGridView.setAdapter(gridCoursesAdapter);
    }
    public void createView(){
        toolbar = (Toolbar)findViewById(R.id.friends_toolbar);
        toolbar.setNavigationIcon(R.drawable.close);
        allBooksGridView = (GridView) findViewById(R.id.all_books_grid_view);
        gridCoursesAdapter = new GridCoursesAdapter(this,R.layout.grid_item_laayout);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId()==android.R.id.home){
            this.finish();
        }
        return super.onOptionsItemSelected(item);
    }

    public static Intent newِAllBooksIntent(Context context, List<UserBooks> userBooksList){
        Intent intent = new Intent(context,AllBooksActivity.class);
        intent.putExtra(ARGID, (Serializable) userBooksList);
        return intent;
    }
    private List<UserBooks> getUSERIDFromIntent() {
        Intent intent = getIntent();
        List<UserBooks> bookses = (List<UserBooks>) intent.getSerializableExtra(ARGID);
        return bookses;
    }

}

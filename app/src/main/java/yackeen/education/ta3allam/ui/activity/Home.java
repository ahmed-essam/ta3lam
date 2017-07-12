package yackeen.education.ta3allam.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;

import yackeen.education.ta3allam.Capsule.Follower;
import yackeen.education.ta3allam.Capsule.SearchProfile;
import yackeen.education.ta3allam.Capsule.UserBooks;
import yackeen.education.ta3allam.R;
import yackeen.education.ta3allam.adapter.FirstLoginAdapter2;
import yackeen.education.ta3allam.adapter.SearchAutoCompleteAdapter;
import yackeen.education.ta3allam.model.dto.request.FollowerRequest;
import yackeen.education.ta3allam.model.dto.request.NewsRequest;
import yackeen.education.ta3allam.model.dto.request.SearchRequest;
import yackeen.education.ta3allam.model.dto.response.FollwerResponse;
import yackeen.education.ta3allam.model.dto.response.SearchResponse;
import yackeen.education.ta3allam.model.dto.response.UnreadeMessageNumResponse;
import yackeen.education.ta3allam.server.api.API;
import yackeen.education.ta3allam.ui.Fragment.NewsFeed;
import yackeen.education.ta3allam.ui.Fragment.Notifications;
import yackeen.education.ta3allam.ui.Fragment.Profile;
import yackeen.education.ta3allam.ui.Fragment.Tracks;
import yackeen.education.ta3allam.util.UserHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


public class Home extends AppCompatActivity implements NewsFeed.OnFragmentInteractionListener,Tracks.OnFragmentInteractionListener,Notifications.OnFragmentInteractionListener,Profile.OnFragmentInteractionListener {
    public static final String TAG = Home.class.getSimpleName();
    FloatingActionButton floatingActionButton;
    ViewPager viewPager;
    TabLayout tabLayout;
    ImageView messagesIcon;
    ImageView unreadeMessages;
    AutoCompleteTextView autoCompleteTextView;
    SearchAutoCompleteAdapter searchAutoCompleteAdapter;
    private int[] tabIcons = {
           yackeen.education.ta3allam.R.drawable.profile,
            yackeen.education.ta3allam.R.drawable.notifications,
            yackeen.education.ta3allam.R.drawable.tracks,
            yackeen.education.ta3allam.R.drawable.home
    };
    public void onFragmentInteraction(Uri uri)
    {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String languageToLoad = "ar"; // your language
        Locale locale = new Locale(languageToLoad);
        Locale.setDefault(locale);

        Configuration config = new Configuration();
        config.locale = locale;
        getBaseContext().getResources().updateConfiguration(config,
                getBaseContext().getResources().getDisplayMetrics());
        setContentView(yackeen.education.ta3allam.R.layout.activity_home);
        autoCompleteTextView = (AutoCompleteTextView)findViewById(R.id.home_search);
        autoCompleteTextView.setText(null);
        floatingActionButton = (FloatingActionButton)findViewById(R.id.edit_floating_button);
        floatingActionButton.setEnabled(false);
        floatingActionButton.setVisibility(View.INVISIBLE);
        searchAutoCompleteAdapter = new SearchAutoCompleteAdapter(this,R.layout.search_item_layout);
        autoCompleteTextView.setAdapter(searchAutoCompleteAdapter);
        autoCompleteTextView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String query = autoCompleteTextView.getText().toString();
                searchUserFromApi(query);

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=  EditProfileActivity.newEditProfileActivtyIntent(Home.this,UserHelper.getPhotoUrl(Home.this),UserHelper.getUserName(Home.this),"");
                startActivity(intent);
            }
        });
        viewPager = (ViewPager) findViewById(yackeen.education.ta3allam.R.id.viewpager);
        setupUI(viewPager);
        setupViewPager(viewPager);
        messagesIcon= (ImageView)findViewById(R.id.messages);
        unreadeMessages= (ImageView)findViewById(R.id.unreade_messages);
        unreadeMessages.setVisibility(View.INVISIBLE);
        messagesIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Home.this,MessagesListActivity.class);
                startActivity(intent);
            }
        });

        tabLayout = (TabLayout) findViewById(yackeen.education.ta3allam.R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
        setupTabIcons();
        viewPager.setCurrentItem(3);
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

                switch (tab.getPosition()){
                    case 0:
                        tab.setIcon(yackeen.education.ta3allam.R.drawable.profile_active);
                        tabLayout.getTabAt(1).setIcon(tabIcons[1]);
                        tabLayout.getTabAt(2).setIcon(tabIcons[2]);
                        tabLayout.getTabAt(3).setIcon(tabIcons[3]);
                        floatingActionButton.setVisibility(View.VISIBLE);
                        floatingActionButton.setEnabled(true);
                        break;
                    case 1:
                        tab.setIcon(yackeen.education.ta3allam.R.drawable.notifications_active);
                        tabLayout.getTabAt(0).setIcon(tabIcons[0]);
                        tabLayout.getTabAt(2).setIcon(tabIcons[2]);
                        tabLayout.getTabAt(3).setIcon(tabIcons[3]);
                        break;
                    case 2:
                        tab.setIcon(yackeen.education.ta3allam.R.drawable.tracks_active);
                        tabLayout.getTabAt(0).setIcon(tabIcons[0]);
                        tabLayout.getTabAt(1).setIcon(tabIcons[1]);
                        tabLayout.getTabAt(3).setIcon(tabIcons[3]);
                        break;
                    case 3:
                        tab.setIcon(yackeen.education.ta3allam.R.drawable.home_active);
                        tabLayout.getTabAt(0).setIcon(tabIcons[0]);
                        tabLayout.getTabAt(1).setIcon(tabIcons[1]);
                        tabLayout.getTabAt(2).setIcon(tabIcons[2]);
                        break;
                    default:
                        break;
                }



            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                floatingActionButton.setEnabled(false);
                floatingActionButton.setVisibility(View.INVISIBLE);

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                if (tab.getPosition()==0){
                    floatingActionButton.setVisibility(View.VISIBLE);
                    floatingActionButton.setEnabled(true);
                }

            }
        });

    }



    public void searchUserFromApi(String searchQuery){
        SearchRequest body = new SearchRequest();
        body.setSearchQuery(searchQuery);
        API.getUserAPIs().searchUser(body,getSearchListener(),
                getSearchFailedListener(),this);

    }
    public void getMessagesNum(){
        NewsRequest body = new NewsRequest();
        body.setUserID(UserHelper.getUserId(this));
        API.getUserAPIs().getUnreadeMessagesNum(body,getMessagesNumListener(),
                getSearchFailedListener(),this);

    }
    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new Profile());
        adapter.addFragment(new Notifications());
        adapter.addFragment(new Tracks());
        adapter.addFragment(new NewsFeed());
        viewPager.setAdapter(adapter);
    }
    private void setupTabIcons() {
        tabLayout.getTabAt(0).setIcon(tabIcons[0]);
        tabLayout.getTabAt(1).setIcon(tabIcons[1]);
        tabLayout.getTabAt(2).setIcon(tabIcons[2]);
        tabLayout.getTabAt(3).setIcon(yackeen.education.ta3allam.R.drawable.home_active);
    }
    public void setupUI(View view) {

        // Set up touch listener for non-text box views to hide keyboard.
        if (!(view instanceof EditText)) {
            view.setOnTouchListener(new View.OnTouchListener() {
                public boolean onTouch(View v, MotionEvent event) {
                    hideSoftKeyboard(Home.this);
                    return false;
                }
            });
        }
    }
    public static void hideSoftKeyboard(Activity activity) {
        InputMethodManager inputMethodManager =
                (InputMethodManager) activity.getSystemService(
                        Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(
                activity.getCurrentFocus().getWindowToken(), 0);
    }


    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment) {
            mFragmentList.add(fragment);
        }

    }
    //network response
    private Response.Listener<SearchResponse> getSearchListener(){
        return new Response.Listener<SearchResponse>() {
            @Override
            public void onResponse(SearchResponse response) {
                if (response.isSuccess()) {
                    List<SearchProfile> searchProfiles = response.SearchUsers;
                    Log.e(TAG, "onResponse: " + searchProfiles.size());
                    searchAutoCompleteAdapter.addAll(searchProfiles);
                    autoCompleteTextView.showDropDown();
                }
            }
        };
    }

    private Response.ErrorListener getSearchFailedListener(){
        return new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "onErrorResponse: ".concat(error.toString()));
                Toast.makeText(Home.this, yackeen.education.ta3allam.R.string.network_error, Toast.LENGTH_SHORT).show();

            }
        };
    }
    private Response.Listener<UnreadeMessageNumResponse> getMessagesNumListener(){
        return new Response.Listener<UnreadeMessageNumResponse>() {
            @Override
            public void onResponse(UnreadeMessageNumResponse response) {
                if (response.isSuccess()) {
                    if (response.getCount()>0){
                        unreadeMessages.setVisibility(View.VISIBLE);
                    }
                }
            }
        };
    }
}

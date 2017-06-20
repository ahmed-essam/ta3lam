package yackeen.education.ta3allam.ui.activity;

import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import yackeen.education.ta3allam.R;
import yackeen.education.ta3allam.ui.Fragment.NewsFeed;
import yackeen.education.ta3allam.ui.Fragment.Notifications;
import yackeen.education.ta3allam.ui.Fragment.Profile;
import yackeen.education.ta3allam.ui.Fragment.Tracks;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class Home extends AppCompatActivity implements NewsFeed.OnFragmentInteractionListener,Tracks.OnFragmentInteractionListener,Notifications.OnFragmentInteractionListener,Profile.OnFragmentInteractionListener {
    ViewPager viewPager;
    TabLayout tabLayout;
    ImageView messagesIcon;
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
        viewPager = (ViewPager) findViewById(yackeen.education.ta3allam.R.id.viewpager);
        setupViewPager(viewPager);
        messagesIcon= (ImageView)findViewById(R.id.messages);
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

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

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
}

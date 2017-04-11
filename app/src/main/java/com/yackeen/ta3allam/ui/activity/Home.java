package com.yackeen.ta3allam.ui.activity;

import android.content.res.Configuration;
import android.net.Uri;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.yackeen.ta3allam.ui.Fragment.NewsFeed;
import com.yackeen.ta3allam.ui.Fragment.Notifications;
import com.yackeen.ta3allam.ui.Fragment.Profile;
import com.yackeen.ta3allam.ui.Fragment.Tracks;
import com.yackeen.ta3allam.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class Home extends AppCompatActivity implements NewsFeed.OnFragmentInteractionListener,Tracks.OnFragmentInteractionListener,Notifications.OnFragmentInteractionListener,Profile.OnFragmentInteractionListener {
    ViewPager viewPager;
    TabLayout tabLayout;
    private int[] tabIcons = {
           R.drawable.profile,
            R.drawable.notifications,
            R.drawable.tracks,
            R.drawable.home
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
        setContentView(R.layout.activity_home);
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
        setupTabIcons();
        viewPager.setCurrentItem(3);
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
        tabLayout.getTabAt(3).setIcon(tabIcons[3]);
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

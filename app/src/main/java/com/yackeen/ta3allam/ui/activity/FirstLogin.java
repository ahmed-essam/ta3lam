package com.yackeen.ta3allam.ui.activity;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.LinearLayout;

import com.yackeen.ta3allam.R;
import com.yackeen.ta3allam.Capsule.Category;
import com.yackeen.ta3allam.adapter.FirstLoginAdapter;
import com.yackeen.ta3allam.ui.Fragment.FirstLogin1Fragment;
import com.yackeen.ta3allam.ui.Fragment.NewPasswordFragment;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class FirstLogin extends AppCompatActivity
{
    private FirstLoginAdapter firstLoginAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_login);

        initializeLayoutElements();

        setInitialFragment(savedInstanceState);
    }

    private void initializeLayoutElements() {

    }

    private void setInitialFragment(Bundle savedInstanceState) {

        // Check that the activity is using the layout version with
        // the fragment_container FrameLayout
        if (findViewById(R.id.fragment_container) != null) {

            // However, if we're being restored from a previous state,
            // then we don't need to do anything and should return or else
            // we could end up with overlapping fragments.
            if (savedInstanceState != null) {
                return;
            }

            FirstLogin1Fragment fragment = new FirstLogin1Fragment();
            // In case this activity was started with special instructions from an
            // Intent, pass the Intent'registerNewUser extras to the fragment as arguments
            //StagesFragment stagesFragment = new StagesFragment();
            //stagesFragment.setArguments(getIntent().getExtras());

            // Add the fragment to the 'fragment_container' FrameLayout
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment_container, fragment, "").commit();

        }
    }

    //responsible for switching fragments
    public void switchFragment(Fragment fragment){

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        // Replace whatever is in the fragment_container view with this fragment,
        // and add the transaction to the back stack so the user can navigate back
        transaction.replace(R.id.fragment_container, fragment);
        transaction.addToBackStack(null);

        // Commit the transaction
        transaction.commit();

    }

    }


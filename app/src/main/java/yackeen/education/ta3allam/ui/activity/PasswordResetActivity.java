package yackeen.education.ta3allam.ui.activity;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import yackeen.education.ta3allam.ui.Fragment.NewPasswordFragment;

public class PasswordResetActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(yackeen.education.ta3allam.R.layout.activity_password_reset);

        initializeLayoutElements();

        setInitialFragment(savedInstanceState);
    }

    private void initializeLayoutElements() {

        Toolbar toolbar = (Toolbar) findViewById(yackeen.education.ta3allam.R.id.toolbar);
        toolbar.setTitle(yackeen.education.ta3allam.R.string.title_activity_forget_password);
        setSupportActionBar(toolbar);
    }

    private void setInitialFragment(Bundle savedInstanceState) {

        // Check that the activity is using the layout version with
        // the fragment_container FrameLayout
        if (findViewById(yackeen.education.ta3allam.R.id.fragment_container) != null) {

            // However, if we're being restored from a previous state,
            // then we don't need to do anything and should return or else
            // we could end up with overlapping fragments.
            if (savedInstanceState != null) {
                return;
            }

            NewPasswordFragment fragment = new NewPasswordFragment();
            // In case this activity was started with special instructions from an
            // Intent, pass the Intent'registerNewUser extras to the fragment as arguments
            //StagesFragment stagesFragment = new StagesFragment();
            //stagesFragment.setArguments(getIntent().getExtras());

            // Add the fragment to the 'fragment_container' FrameLayout
            getSupportFragmentManager().beginTransaction()
                    .add(yackeen.education.ta3allam.R.id.fragment_container, fragment, "").commit();

        }
    }

    //responsible for switching fragments
    public void switchFragment(Fragment fragment){

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        // Replace whatever is in the fragment_container view with this fragment,
        // and add the transaction to the back stack so the user can navigate back
        transaction.replace(yackeen.education.ta3allam.R.id.fragment_container, fragment);
        transaction.addToBackStack(null);

        // Commit the transaction
        transaction.commit();
    }

}

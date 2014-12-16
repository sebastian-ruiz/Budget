package uk.co.sruiz.budget.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import uk.co.sruiz.budget.Fragments.OverviewsFragment;
import uk.co.sruiz.budget.MyDDPState;
import uk.co.sruiz.budget.R;


public class MainActivity extends FragmentActivity {

    /** handle to Login menu item */
    private MenuItem mMenuLogin;

    /** handle to Logout menu item */
    private MenuItem mMenuLogout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_party_detail);

        // Show the Up button in the action bar.
        getActionBar().setDisplayHomeAsUpEnabled(true);

        // savedInstanceState is non-null when there is fragment state
        // saved from previous configurations of this activity
        // (e.g. when rotating the screen from portrait to landscape).
        // In this case, the fragment will automatically be re-added
        // to its container so we don't need to manually add it.
        // For more information, see the Fragments API guide at:
        //
        // http://developer.android.com/guide/components/fragments.html
        //
        if (savedInstanceState == null) {
            // Create the detail fragment and add it to the activity
            // using a fragment transaction.
            Bundle arguments = new Bundle();
            arguments.putString(OverviewsFragment.ARG_ITEM_ID, getIntent().getStringExtra(OverviewsFragment.ARG_ITEM_ID));
            OverviewsFragment fragment = new OverviewsFragment();
            fragment.setArguments(arguments);
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.party_detail_container, fragment).commit();
        }
    }

    @Override
    public void onResume() {
        super.onResume();  // Always call the superclass method first
        MyDDPState ddp = MyDDPState.getInstance();
        Log.v("boo", "(onResume) login status: " + ddp.isLoggedIn());
        attemptLogin();
//        updateLoginButtons();
    }

    /** used by fragment to force update of login buttons */
//    @Override
    public void updateLoginButtons() {
        if (mMenuLogin != null) {
            MyDDPState ddp = MyDDPState.getInstance();
            mMenuLogin.setVisible(!ddp.isLoggedIn());
            mMenuLogout.setVisible(ddp.isLoggedIn());
            Log.v("boo", "login status: " + ddp.isLoggedIn());
        }
    }
    public void attemptLogin() {
        MyDDPState ddp = MyDDPState.getInstance();
        String resumeToken = ddp.getResumeToken();
        if (resumeToken != null) {
            // start DDP login process
            Log.v("boo", "resumeToken exists. " + ddp.isLoggedIn());
            ddp.login(resumeToken);
        } else {
            // fire up login window
//            Intent intent = new Intent(this, LoginActivity.class);
//            this.startActivity(intent);
        }

        if(!ddp.isLoggedIn()) {
            showLoginActivity();
        }else {

        }

        updateLoginButtons();
    }

    public void showLoginActivity() {
        Intent intent = new Intent(this, LoginActivity.class);
        this.startActivity(intent);
    }

    /**
     * Updates login action bar menu buttons
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        mMenuLogin = menu.findItem(R.id.action_login);
        mMenuLogout = menu.findItem(R.id.action_logout);
        updateLoginButtons();
        return true;
    }

    /**
     * Handles login/logout button presses
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.refresh:
                updateLoginButtons();
                break;
            case R.id.action_login:
                attemptLogin();
                break;
            case R.id.action_logout:
                MyDDPState ddp = MyDDPState.getInstance();
                ddp.logout();
                showLoginActivity();
                break;
            default:
                break;
        }
        return true;
    }

}

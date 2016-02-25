package webb8.wathub.hub;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.ActionBar;
import android.app.FragmentManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.support.v4.widget.DrawerLayout;
import android.view.View;
import android.widget.TextView;

import com.parse.ParseUser;

import webb8.wathub.R;
import webb8.wathub.hub.fragments.NavigationDrawerFragment;
import webb8.wathub.init.MainActivity;
import webb8.wathub.hub.fragments.HubFragment;

public class HubActivity extends AppCompatActivity
        implements NavigationDrawerFragment.NavigationDrawerCallbacks {

    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    private NavigationDrawerFragment mNavigationDrawerFragment;

    /**
     * FAB buttons for posting
     */
    private FloatingActionButton mPostFab;
    private FloatingActionButton mGeneralPostFab;
    private FloatingActionButton mBookExchangeFab;
    private FloatingActionButton mGroupStudyFab;
    private TextView mGeneralPostFabTextView;
    private TextView mBookExchangeFabTextView;
    private TextView mGroupStudyFabTextView;

    /**
     * Used to store the last screen title. For use in {@link #restoreActionBar()}.
     */
    private CharSequence mTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hub);
        HubFragment.setHubActivity(this);

        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getFragmentManager().findFragmentById(R.id.navigation_drawer);
        mTitle = getTitle();

        // Set up the drawer.
        mNavigationDrawerFragment.setUp(
                R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout));

        mPostFab = (FloatingActionButton) findViewById(R.id.fab_post);
        mGeneralPostFab = (FloatingActionButton) findViewById(R.id.fab_general_post);
        mBookExchangeFab = (FloatingActionButton) findViewById(R.id.fab_book_exchange);
        mGroupStudyFab = (FloatingActionButton) findViewById(R.id.fab_group_study);
        mGeneralPostFabTextView = (TextView) findViewById(R.id.fab_text_general);
        mBookExchangeFabTextView = (TextView) findViewById(R.id.fab_text_book_exchange);
        mGroupStudyFabTextView = (TextView) findViewById(R.id.fab_text_group_study);
        mPostFab.setTag(R.drawable.ic_add_white_24dp);

        mPostFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int drawableId = (Integer) mPostFab.getTag();
                if (drawableId == R.drawable.ic_add_white_24dp) {
                    togglePostFab(View.VISIBLE, R.drawable.ic_clear_white_24dp);
                } else {
                    togglePostFab(View.INVISIBLE, R.drawable.ic_add_white_24dp);
                }

            }
        });

        mGeneralPostFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    // toggle post FAB
    private void togglePostFab(int visibility, int drawable) {
        mPostFab.setTag(drawable);
        mPostFab.setImageResource(drawable);
        mGeneralPostFab.setVisibility(visibility);
        mBookExchangeFab.setVisibility(visibility);
        mGroupStudyFab.setVisibility(visibility);
        mGeneralPostFabTextView.setVisibility(visibility);
        mBookExchangeFabTextView.setVisibility(visibility);
        mGroupStudyFabTextView.setVisibility(visibility);
    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {
        // update the main content by replacing fragments
        FragmentManager fragmentManager = getFragmentManager();

        // replace the fragment depending on action
        if (position == Action.LOG_OUT.getId()) {
            ParseUser.logOut();
            Intent mainIntent = new Intent(this, MainActivity.class);
            startActivity(mainIntent);
            finish();
        } else {
            fragmentManager.beginTransaction()
                    .replace(R.id.container, HubFragment.newInstance(position))
                    .commit();
        }
    }

    public void onSectionAttached(int section) {

        if (section == Action.PROFILE.getId()) {
            mTitle = getString(Action.PROFILE.getNameId());
        }

        if (section == Action.MESSAGES.getId()) {
            mTitle = getString(Action.MESSAGES.getNameId());
        }

        if (section == Action.ALL_POSTS.getId()) {
            mTitle = getString(Action.ALL_POSTS.getNameId());
        }

        if (section == Action.BOOK_EXCHANGE_POSTS.getId()) {
            mTitle = getString(Action.BOOK_EXCHANGE_POSTS.getNameId());
        }

        if (section == Action.CARPOOL_POSTS.getId()) {
            mTitle = getString(Action.CARPOOL_POSTS.getNameId());
        }

        if (section == Action.GROUP_STUDY_POSTS.getId()) {
            mTitle = getString(Action.GROUP_STUDY_POSTS.getNameId());
        }

        if (section == Action.FAVORITES.getId()) {
            mTitle = getString(Action.FAVORITES.getNameId());
        }

        if (section == Action.LOG_OUT.getId()) {
            mTitle = getString(Action.LOG_OUT.getNameId());
        }
    }

    public void restoreActionBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(mTitle);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!mNavigationDrawerFragment.isDrawerOpen()) {
            // Only show items in the action bar relevant to this screen
            // if the drawer is not showing. Otherwise, let the drawer
            // decide what to show in the action bar.
            getMenuInflater().inflate(R.menu.post, menu);
            restoreActionBar();
            return true;
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}

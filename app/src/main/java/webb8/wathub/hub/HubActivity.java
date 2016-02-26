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
import android.widget.FrameLayout;
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
    private FrameLayout mContainerPostFab;
    private FloatingActionButton mPostFab;
    private FloatingActionButton mGeneralPostFab;
    private FloatingActionButton mBookExchangeFab;
    private FloatingActionButton mGroupStudyFab;
    private TextView mGeneralPostFabTextView;
    private TextView mBookExchangeFabTextView;
    private TextView mGroupStudyFabTextView;

    /**
     * Fragments
     */
    private HubFragment mPostFragment;
    private HubFragment mProfileFragment;
    private HubFragment mMessageFragment;
    private HubFragment mBookExchangeFragment;
    private HubFragment mGroupStudyFragment;
    private HubFragment mCarpoolFragment;
    private HubFragment mFavoriteFragment;
    private HubFragment mActionPostFragment;
    private HubFragment mActionBookExchangePostFragment;
    private HubFragment mActionGroupStudyPostFragment;

    /**
     * FAB states
     */
    private static final int FAB_ADD = 0;
    private static final int FAB_CANCEL = 1;

    /**
     * Used to store the last screen title. For use in {@link #restoreActionBar()}.
     */
    private CharSequence mTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hub);
        HubFragment.setHubActivity(this);

        mActionPostFragment = HubFragment.newInstance(Action.ACTION_POST_GENERAL.getId());
        mActionBookExchangePostFragment = HubFragment.newInstance(Action.ACTION_POST_BOOK_EXCHANGE.getId());
        mActionGroupStudyPostFragment = HubFragment.newInstance(Action.ACTION_POST_GROUP_STUDY.getId());

        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getFragmentManager().findFragmentById(R.id.navigation_drawer);

        mTitle = getTitle();

        // Set up the drawer.
        mNavigationDrawerFragment.setUp(
                R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout));

        setUpPostFab();
    }

    private void setUpPostFab() {

        mContainerPostFab = (FrameLayout) findViewById(R.id.container_fab_post);
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
                    togglePostFab(FAB_CANCEL);
                } else {
                    togglePostFab(FAB_ADD);
                }

            }
        });

        mGeneralPostFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getFragmentManager();

                fragmentManager.beginTransaction()
                        .replace(R.id.container, mActionPostFragment)
                        .commit();
            }
        });

        mBookExchangeFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getFragmentManager();

                fragmentManager.beginTransaction()
                        .replace(R.id.container, mActionBookExchangePostFragment)
                        .commit();
            }
        });

        mGroupStudyFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getFragmentManager();

                fragmentManager.beginTransaction()
                        .replace(R.id.container, mActionGroupStudyPostFragment)
                        .commit();
            }
        });
    }

    // toggle post FAB
    private void togglePostFab(int state) {
        // state FAB_ADD
        int drawable = R.drawable.ic_add_white_24dp;
        int optionsVisibility = View.GONE;

        if (state == FAB_CANCEL) {
            drawable = R.drawable.ic_clear_white_24dp;
            optionsVisibility = View.VISIBLE;
        }

        mPostFab.setTag(drawable);
        mPostFab.setImageResource(drawable);
        mGeneralPostFab.setVisibility(optionsVisibility);
        mBookExchangeFab.setVisibility(optionsVisibility);
        mGroupStudyFab.setVisibility(optionsVisibility);
//        mGeneralPostFabTextView.setVisibility(optionsVisibility);
//        mBookExchangeFabTextView.setVisibility(optionsVisibility);
//        mGroupStudyFabTextView.setVisibility(optionsVisibility);
    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {
        // update the main content by replacing fragments
        FragmentManager fragmentManager = getFragmentManager();
        HubFragment fragment = null;

        if (position == NavItem.LOG_OUT.getId()) {
            ParseUser.logOut();
            Intent mainIntent = new Intent(this, MainActivity.class);
            startActivity(mainIntent);
            finish();
            return;
        }

        // replace the fragment depending on action
        if (position == NavItem.PROFILE.getId()) {
            if (mProfileFragment == null) mProfileFragment = HubFragment.newInstance(NavItem.PROFILE.getId());
            fragment = mProfileFragment;
        } else

        if (position == NavItem.MESSAGES.getId()) {
            if (mMessageFragment == null) mMessageFragment = HubFragment.newInstance(NavItem.MESSAGES.getId());
            fragment = mMessageFragment;
        } else

        if (position == NavItem.ALL_POSTS.getId()) {
            if (mPostFragment == null) mPostFragment = HubFragment.newInstance(NavItem.ALL_POSTS.getId());
            fragment = mPostFragment;
        } else

        if (position == NavItem.BOOK_EXCHANGE_POSTS.getId()) {
            if (mBookExchangeFragment == null) mBookExchangeFragment = HubFragment.newInstance(NavItem.BOOK_EXCHANGE_POSTS.getId());
            fragment = mBookExchangeFragment;
        } else

        if (position == NavItem.CARPOOL_POSTS.getId()) {
            if (mCarpoolFragment == null) mCarpoolFragment = HubFragment.newInstance(NavItem.CARPOOL_POSTS.getId());
            fragment = mCarpoolFragment;
        } else

        if (position == NavItem.GROUP_STUDY_POSTS.getId()) {
            if (mGroupStudyFragment == null) mGroupStudyFragment = HubFragment.newInstance(NavItem.GROUP_STUDY_POSTS.getId());
            fragment = mGroupStudyFragment;
        } else

        if (position == NavItem.FAVORITES.getId()) {
            if (mFavoriteFragment == null) mFavoriteFragment = HubFragment.newInstance(NavItem.FAVORITES.getId());
            fragment = mFavoriteFragment;
        }

        fragmentManager.beginTransaction()
                .replace(R.id.container, fragment)
                .commit();
    }

    public void onSectionAttached(int section) {

        togglePostFab(FAB_ADD);

        if (section == NavItem.PROFILE.getId()) {
            mTitle = getString(NavItem.PROFILE.getNameId());
            mContainerPostFab.setVisibility(View.GONE);
        } else

        if (section == NavItem.MESSAGES.getId()) {
            mTitle = getString(NavItem.MESSAGES.getNameId());
            mContainerPostFab.setVisibility(View.GONE);
        } else

        if (section == NavItem.ALL_POSTS.getId()) {
            mTitle = getString(NavItem.ALL_POSTS.getNameId());
            mContainerPostFab.setVisibility(View.VISIBLE);
        } else

        if (section == NavItem.BOOK_EXCHANGE_POSTS.getId()) {
            mTitle = getString(NavItem.BOOK_EXCHANGE_POSTS.getNameId());
            mContainerPostFab.setVisibility(View.VISIBLE);
        } else

        if (section == NavItem.CARPOOL_POSTS.getId()) {
            mTitle = getString(NavItem.CARPOOL_POSTS.getNameId());
            mContainerPostFab.setVisibility(View.VISIBLE);
        } else

        if (section == NavItem.GROUP_STUDY_POSTS.getId()) {
            mTitle = getString(NavItem.GROUP_STUDY_POSTS.getNameId());
            mContainerPostFab.setVisibility(View.VISIBLE);
        } else

        if (section == NavItem.FAVORITES.getId()) {
            mTitle = getString(NavItem.FAVORITES.getNameId());
            mContainerPostFab.setVisibility(View.VISIBLE);
        } else

        if (section == NavItem.LOG_OUT.getId()) {
            mTitle = getString(NavItem.LOG_OUT.getNameId());
        } else

        if (section == Action.ACTION_POST_GENERAL.getId()) {
            mTitle = getString(Action.ACTION_POST_GENERAL.getNameId());
            mContainerPostFab.setVisibility(View.VISIBLE);
            restoreActionBar();
        } else

        if (section == Action.ACTION_POST_BOOK_EXCHANGE.getId()) {
            mTitle = getString(Action.ACTION_POST_BOOK_EXCHANGE.getNameId());
            mContainerPostFab.setVisibility(View.VISIBLE);
            restoreActionBar();
        } else

        if (section == Action.ACTION_POST_GROUP_STUDY.getId()) {
            mTitle = getString(Action.ACTION_POST_GROUP_STUDY.getNameId());
            mContainerPostFab.setVisibility(View.VISIBLE);
            restoreActionBar();
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

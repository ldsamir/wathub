package webb8.wathub;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.widget.DrawerLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

import webb8.wathub.models.Post;

public class HubActivity extends AppCompatActivity
        implements NavigationDrawerFragment.NavigationDrawerCallbacks {

    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    private NavigationDrawerFragment mNavigationDrawerFragment;

    /**
     * Used to store the last screen title. For use in {@link #restoreActionBar()}.
     */
    private CharSequence mTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hub);
        HubFragment.setThisActivity(this);

        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);
        mTitle = getTitle();

        // Set up the drawer.
        mNavigationDrawerFragment.setUp(
                R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout));
    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {
        // update the main content by replacing fragments
        FragmentManager fragmentManager = getSupportFragmentManager();

        if (position == Action.PROFILE.getId()) {

        }

        if (position == Action.MESSAGES.getId()) {

        }

        if (position == Action.ALL_POSTS.getId()) {
            fragmentManager.beginTransaction()
                    .replace(R.id.container, HubFragment.newInstance(position))
                    .commit();
        }

        if (position == Action.BOOK_EXCHANGE_POSTS.getId()) {

        }

        if (position == Action.CARPOOL_POSTS.getId()) {

        }

        if (position == Action.GROUP_STUDY_POSTS.getId()) {

        }

        if (position == Action.FAVORITES.getId()) {

        }

        if (position == Action.LOG_OUT.getId()) {
            ParseUser.logOut();
            Intent mainIntent = new Intent(this, MainActivity.class);
            startActivity(mainIntent);
        }
    }

    public void onSectionAttached(int number) {

        if (number == Action.PROFILE.getId()) { mTitle = getString(Action.PROFILE.getNameId()); }

        if (number == Action.MESSAGES.getId()) { mTitle = getString(Action.MESSAGES.getNameId()); }

        if (number == Action.ALL_POSTS.getId()) { mTitle = getString(Action.ALL_POSTS.getNameId()); }

        if (number == Action.BOOK_EXCHANGE_POSTS.getId()) { mTitle = getString(Action.BOOK_EXCHANGE_POSTS.getNameId()); }

        if (number == Action.CARPOOL_POSTS.getId()) { mTitle = getString(Action.CARPOOL_POSTS.getNameId()); }

        if (number == Action.GROUP_STUDY_POSTS.getId()) { mTitle = getString(Action.GROUP_STUDY_POSTS.getNameId()); }

        if (number == Action.FAVORITES.getId()) { mTitle = getString(Action.FAVORITES.getNameId()); }

        if (number == Action.LOG_OUT.getId()) { mTitle = getString(Action.LOG_OUT.getNameId()); }
    }

    public void restoreActionBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
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

    /**
     * General Post Fragment
     */
    public static class HubFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";
        private static Activity thisActivity;
        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static HubFragment newInstance(int sectionNumber) {
            HubFragment fragment = new HubFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        public HubFragment() {
        }

        public static void setThisActivity(Activity activity) {
            thisActivity = activity;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            int sectionNumber = getArguments().getInt(ARG_SECTION_NUMBER);
            View rootView = inflater.inflate(R.layout.fragment_hub, container, false);
            final LinearLayout fragmentContainer = (LinearLayout) rootView;

            if (sectionNumber == Action.PROFILE.getId()) {

            }

            if (sectionNumber == Action.MESSAGES.getId()) {

            }

            if (sectionNumber == Action.ALL_POSTS.getId()) {
                ParseQuery<ParseObject> query = Post.getQuery();
                query.orderByDescending("updatedAt");
                query.findInBackground(new FindCallback<ParseObject>() {
                    @Override
                    public void done(List<ParseObject> objects, ParseException e) {
                        if (e == null) {
                            ArrayList<Post> posts = new ArrayList<Post>();

                            for (ParseObject object : objects) {
                                Post post = new Post(object);
                                posts.add(post);
                                View postView = getPostView(post);
                                fragmentContainer.addView(postView);
                            }

                        } else {
                            Toast.makeText(thisActivity.getApplicationContext(), R.string.error_loading_posts, Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }

            if (sectionNumber == Action.BOOK_EXCHANGE_POSTS.getId()) {

            }

            if (sectionNumber == Action.CARPOOL_POSTS.getId()) {

            }

            if (sectionNumber == Action.GROUP_STUDY_POSTS.getId()) {

            }

            if (sectionNumber == Action.FAVORITES.getId()) {

            }

            if (sectionNumber == Action.LOG_OUT.getId()) {

            }

            return rootView;
        }

        public static View getPostView(Post post) {
            CardView cardView = new CardView(thisActivity.getApplicationContext());
            TextView textView = new TextView(thisActivity.getApplicationContext());
            textView.setText(post.getContent());
            textView.setTextColor(thisActivity.getResources().getColor(R.color.black));
            textView.setGravity(Gravity.CENTER);
            cardView.addView(textView);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 200);
            cardView.setLayoutParams(layoutParams);
            return cardView;
        }

        @Override
        public void onAttach(Activity activity) {
            super.onAttach(activity);
            ((HubActivity) activity).onSectionAttached(
                    getArguments().getInt(ARG_SECTION_NUMBER));
        }
    }

    public enum Action {
        PROFILE(0, R.string.title_profile),
        MESSAGES(1, R.string.title_messaging),
        ALL_POSTS(2, R.string.title_all_posts),
        BOOK_EXCHANGE_POSTS(3, R.string.title_book_exchange_posts),
        CARPOOL_POSTS(4, R.string.title_carpool_posts),
        GROUP_STUDY_POSTS(5, R.string.title_group_study_posts),
        FAVORITES(6, R.string.title_favorites),
        LOG_OUT(7, R.string.title_log_out);

        private int id;
        private int nameId;

        Action(int id, int nameId) {
            this.id = id;
            this.nameId = nameId;
        }

        public int getId() {
            return id;
        }

        public int getNameId() {
            return nameId;
        }
    }
}

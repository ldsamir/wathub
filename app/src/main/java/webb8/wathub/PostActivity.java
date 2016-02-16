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

public class PostActivity extends AppCompatActivity
        implements NavigationDrawerFragment.NavigationDrawerCallbacks {

    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    private NavigationDrawerFragment mNavigationDrawerFragment;

    /**
     * Used to store the last screen title. For use in {@link #restoreActionBar()}.
     */
    private CharSequence mTitle;

    private static final int PROFILE = 0;
    private static final int MESSAGES = 1;
    private static final int ALL_POSTS = 2;
    private static final int BOOK_EXCHANGE_POSTS = 3;
    private static final int CARPOOL_POSTS = 4;
    private static final int GROUP_STUDY_POSTS = 5;
    private static final int FAVORITES = 6;
    private static final int LOG_OUT = 7;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);
        PostFragment.setThisActivity(this);

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
                    .replace(R.id.container, PostFragment.newInstance(position))
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

        if (number == Action.PROFILE.getId()) { mTitle = Action.PROFILE.getName(); }

        if (number == Action.MESSAGES.getId()) { mTitle = Action.MESSAGES.getName(); }

        if (number == Action.ALL_POSTS.getId()) { mTitle = Action.ALL_POSTS.getName(); }

        if (number == Action.BOOK_EXCHANGE_POSTS.getId()) { mTitle = Action.BOOK_EXCHANGE_POSTS.getName(); }

        if (number == Action.CARPOOL_POSTS.getId()) { mTitle = Action.CARPOOL_POSTS.getName(); }

        if (number == Action.GROUP_STUDY_POSTS.getId()) { mTitle = Action.GROUP_STUDY_POSTS.getName(); }

        if (number == Action.FAVORITES.getId()) { mTitle = Action.FAVORITES.getName(); }

        if (number == Action.LOG_OUT.getId()) { mTitle = Action.LOG_OUT.getName(); }
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
    public static class PostFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";
        private static PostActivity thisActivity;
        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PostFragment newInstance(int sectionNumber) {
            PostFragment fragment = new PostFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        public PostFragment() {
        }

        public static void setThisActivity(PostActivity activity) {
            thisActivity = activity;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            int sectionNumber = getArguments().getInt(ARG_SECTION_NUMBER);
            View rootView = inflater.inflate(R.layout.fragment_post, container, false);
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
            ((PostActivity) activity).onSectionAttached(
                    getArguments().getInt(ARG_SECTION_NUMBER));
        }
    }

    public enum Action {
        PROFILE(0, "Profile"),
        MESSAGES(1, "Messages"),
        ALL_POSTS(2, "All posts"),
        BOOK_EXCHANGE_POSTS(3, "Book Exchange"),
        CARPOOL_POSTS(4, "Carpool"),
        GROUP_STUDY_POSTS(5, "Group Study"),
        FAVORITES(6, "Favorites"),
        LOG_OUT(7, "Log out");

        private int id;
        private String name;

        Action(int id, String name) {
            this.id = id;
            this.name = name;
        }

        public int getId() {
            return id;
        }

        public String getName() {
            return name;
        }
    }
}

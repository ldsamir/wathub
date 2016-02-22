package webb8.wathub;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.widget.DrawerLayout;
import android.widget.Button;
import android.widget.Toast;
import android.widget.EditText;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import webb8.wathub.models.Parsable;
import webb8.wathub.models.Post;
import webb8.wathub.models.Profile;

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

        Button postButton = (Button) findViewById(R.id.post_button);
    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {
        // update the main content by replacing fragments
        FragmentManager fragmentManager = getSupportFragmentManager();

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

    public void onSectionAttached(int number) {

        if (number == Action.PROFILE.getId()) {
            mTitle = getString(Action.PROFILE.getNameId());
        }

        if (number == Action.MESSAGES.getId()) {
            mTitle = getString(Action.MESSAGES.getNameId());
        }

        if (number == Action.ALL_POSTS.getId()) {
            mTitle = getString(Action.ALL_POSTS.getNameId());
        }

        if (number == Action.BOOK_EXCHANGE_POSTS.getId()) {
            mTitle = getString(Action.BOOK_EXCHANGE_POSTS.getNameId());
        }

        if (number == Action.CARPOOL_POSTS.getId()) {
            mTitle = getString(Action.CARPOOL_POSTS.getNameId());
        }

        if (number == Action.GROUP_STUDY_POSTS.getId()) {
            mTitle = getString(Action.GROUP_STUDY_POSTS.getNameId());
        }

        if (number == Action.FAVORITES.getId()) {
            mTitle = getString(Action.FAVORITES.getNameId());
        }

        if (number == Action.LOG_OUT.getId()) {
            mTitle = getString(Action.LOG_OUT.getNameId());
        }
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
     * Hub Fragment
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

        // set current activity
        public static void setThisActivity(Activity activity) {
            thisActivity = activity;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup viewContainer, Bundle savedInstanceState) {
            int sectionNumber = getArguments().getInt(ARG_SECTION_NUMBER);
            View rootView = null;

            // fill fragment depending on action
            if (sectionNumber == Action.PROFILE.getId()) {
                rootView = inflater.inflate(R.layout.fragment_profile, viewContainer, false);
                ParseQuery<ParseObject> query = Profile.getQuery();
                ParseUser user = ParseUser.getCurrentUser();
                query.whereEqualTo("owner", user);
                try {
                    List<ParseObject> objects = query.find();
                    Profile cur_user = Profile.getInstance(objects.get(0));
                    EditText firstNameView = (EditText) rootView.findViewById(R.id.firstName);
                    EditText lastNameView = (EditText) rootView.findViewById(R.id.lastName);
                    EditText birthDayView = (EditText) rootView.findViewById(R.id.birthDay);
                    EditText phoneView = (EditText) rootView.findViewById(R.id.phoneNumber);
                    firstNameView.setText(cur_user.getFirstName());
                    lastNameView.setText(cur_user.getLastName());
                    SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy HH:mm a", Locale.CANADA);
                    if(cur_user.getBirthday() != null){
                        birthDayView.setText(format.format(cur_user.getBirthday()));
                    }
                    phoneView.setText(cur_user.getPhone());
                } catch (ParseException e) {

                }
            }

            if (sectionNumber == Action.MESSAGES.getId()) {

            }

            if (sectionNumber == Action.ALL_POSTS.getId()) {
                rootView = inflater.inflate(R.layout.fragment_post, viewContainer, false);
                RecyclerView postContainer = (RecyclerView) rootView;
                LinearLayoutManager llm = new LinearLayoutManager(thisActivity.getApplicationContext());
                postContainer.setLayoutManager(llm);
                ParseQuery<ParseObject> query = Post.getQuery();
                query.orderByDescending(Parsable.KEY_UPDATED_AT);

                try {
                    List<ParseObject> objects = query.find();
                    List<Post> posts = new ArrayList<>();

                    for (ParseObject object : objects) {
                        Post post = Post.getInstance(object);
                        posts.add(post);
                    }

                    PostAdapter postAdapter = new PostAdapter(posts);
                    postContainer.setAdapter(postAdapter);
                } catch (ParseException e) {
                    Toast.makeText(thisActivity.getApplicationContext(), R.string.error_loading_posts, Toast.LENGTH_SHORT).show();
                }

//                query.findInBackground(new FindCallback<ParseObject>() {
//                    @Override
//                    public void done(List<ParseObject> objects, ParseException e) {
//                        if (e == null) {
//                            List<Post> posts = new ArrayList<Post>();
//
//                            for (ParseObject object : objects) {
//                                Post post = Post.getInstance(object);
//                                posts.add(post);
//                            }
//
//                            PostAdapter postAdapter = new PostAdapter(posts);
//                            postContainer.setAdapter(postAdapter);
//
//                        } else {
//                            Toast.makeText(thisActivity.getApplicationContext(), R.string.error_loading_posts, Toast.LENGTH_SHORT).show();
//                        }
//                    }
//                });
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

package webb8.wathub.hub;

import android.app.Activity;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import webb8.wathub.R;
import webb8.wathub.hub.fragments.PostFeedFragment;
import webb8.wathub.models.Parsable;
import webb8.wathub.models.Post;
import webb8.wathub.models.Profile;
import webb8.wathub.util.PostCard;

public class ProfileActivity extends AppCompatActivity {

    public static final String EXTRA_USER_NAME = "EXTRA_USER_NAME";

    private Activity self;
    private ImageView mProfilePhotoView;
    private TextView mProfileUserNameView;
    private TextView mProfileUserBirthdayView;
    private TextView mProfileUserMajorView;
    private LinearLayout mProfileUserContactsView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        self = this;
        String username = getIntent().getStringExtra(EXTRA_USER_NAME);
        setTitle(username);
        mProfilePhotoView = (ImageView) findViewById(R.id.profile_photo);
        mProfileUserNameView = (TextView) findViewById(R.id.profile_user_name);
        mProfileUserBirthdayView = (TextView) findViewById(R.id.profile_user_birthday);
        mProfileUserMajorView = (TextView) findViewById(R.id.profile_user_major);
        mProfileUserContactsView = (LinearLayout) findViewById(R.id.profile_user_contact_links);

        ParseQuery<ParseUser> userQuery = ParseUser.getQuery();
        userQuery.whereEqualTo(Parsable.KEY_USERNAME, username);
        userQuery.findInBackground(new FindCallback<ParseUser>() {
            @Override
            public void done(List<ParseUser> objects, ParseException e) {
                if (e == null) {
                    if (objects.size() > 0) {
                        final ParseUser user = objects.get(0);
                        ParseQuery<ParseObject> profileQuery = Profile.getQuery();
                        profileQuery.whereEqualTo(Profile.KEY_OWNER, user);
                        profileQuery.findInBackground(new FindCallback<ParseObject>() {
                            @Override
                            public void done(List<ParseObject> objects, ParseException e) {
                                if (e == null) {
                                    if (objects.size() > 0) {
                                        Profile profile = Profile.getInstance(objects.get(0));
                                        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
                                        mProfileUserNameView.setText(profile.getFirstName() + " " + profile.getLastName());
                                        mProfileUserBirthdayView.setText(format.format(profile.getBirthday()));
                                        mProfileUserMajorView.setText(profile.getMajor());
//                                        JSONArray contactsArray = profile.getContactLinks();
//
//                                        for (int i = 0; i < contactsArray.length(); i++) {
//                                            try {
//                                                JSONObject contactLink = contactsArray.getJSONObject(i);
//                                                contactLink.
//                                            } catch (JSONException je) {
//
//                                            }
//
//                                        }

                                        ParseQuery<ParseObject> postQuery = Post.getQuery();
                                        postQuery.whereEqualTo(Post.KEY_USER, user);
                                        postQuery.orderByDescending(Parsable.KEY_UPDATED_AT);
                                        postQuery.findInBackground(new FindCallback<ParseObject>() {
                                            @Override
                                            public void done(List<ParseObject> objects, ParseException e) {
                                                if (e == null) {
                                                    List<PostCard> postCards = new ArrayList<>();

                                                    for (ParseObject object : objects) {
                                                        Post post = Post.getInstance(object);
                                                        postCards.add(new PostCard(self, post));
                                                    }

                                                    PostFeedFragment postFeedFragment = PostFeedFragment.newInstance(postCards);
                                                    getFragmentManager().beginTransaction()
                                                            .replace(R.id.profile_user_posts, postFeedFragment)
                                                            .commit();
                                                }
                                            }
                                        });

                                    } else {
                                        Toast.makeText(getApplicationContext(), R.string.error_no_user_profile_found, Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }
                        });
                    } else {
                        Toast.makeText(getApplicationContext(), R.string.error_no_user_found, Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
}

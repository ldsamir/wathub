package webb8.wathub.hub;

import android.app.Activity;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

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
    private TextView mProfileUserEmailView;
    private TextView mProfileUserPhoneView;
    private LinearLayout mProfileUserContactsView;
    private LinearLayout mProfileNameContainer;
    private LinearLayout mProfileBirthdayContainer;
    private LinearLayout mProfileMajorContainer;
    private LinearLayout mProfileEmailContainer;
    private LinearLayout mProfilePhoneContainer;
    private LinearLayout mProfilePostContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        self = this;
        final String username = getIntent().getStringExtra(EXTRA_USER_NAME);
        setTitle(username);

        mProfilePhotoView = (ImageView) findViewById(R.id.profile_photo);
        mProfileUserNameView = (TextView) findViewById(R.id.profile_name);
        mProfileUserBirthdayView = (TextView) findViewById(R.id.profile_birthday);
        mProfileUserMajorView = (TextView) findViewById(R.id.profile_major);
        mProfileUserEmailView = (TextView) findViewById(R.id.profile_email);
        mProfileUserPhoneView = (TextView) findViewById(R.id.profile_phone);
        mProfileUserContactsView = (LinearLayout) findViewById(R.id.profile_contact_links);
        mProfileNameContainer = (LinearLayout) findViewById(R.id.profile_name_container);
        mProfileBirthdayContainer = (LinearLayout) findViewById(R.id.profile_birthday_container);
        mProfileMajorContainer = (LinearLayout) findViewById(R.id.profile_major_container);
        mProfileEmailContainer = (LinearLayout) findViewById(R.id.profile_email_container);
        mProfilePhoneContainer = (LinearLayout) findViewById(R.id.profile_phone_container);
        mProfilePostContainer = (LinearLayout) findViewById(R.id.profile_post_container);

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
                                        SimpleDateFormat format = new SimpleDateFormat("MMMM dd, yyyy");
                                        String name = profile.getFirstName() + " " + profile.getLastName();
                                        Date birthday = profile.getBirthday();
                                        String major = profile.getMajor();
                                        String phone = profile.getPhone();
                                        ParseFile avatarFile = profile.getAvatar();

                                        if (!name.isEmpty()) mProfileUserNameView.setText(name);
                                        else mProfileNameContainer.setVisibility(View.GONE);

                                        if (birthday != null) mProfileUserBirthdayView.setText(format.format(birthday));
                                        else mProfileBirthdayContainer.setVisibility(View.GONE);

                                        if (major != null && !major.isEmpty()) mProfileUserMajorView.setText(major);
                                        else mProfileMajorContainer.setVisibility(View.GONE);

                                        mProfileUserEmailView.setText(user.getEmail());

                                        if (phone != null && !phone.isEmpty()) mProfileUserPhoneView.setText(phone);
                                        else mProfilePhoneContainer.setVisibility(View.GONE);

                                        JSONArray contactsArray = profile.getContactLinks();

                                        if (contactsArray != null) {
                                            for (int i = 0; i < contactsArray.length(); i++) {
                                                try {
                                                    Object contactLink = contactsArray.get(i);
                                                    View contactLinkView = getLayoutInflater().inflate(R.layout.contact_link_text, null, false);
                                                    TextView textView = (TextView) contactLinkView.findViewWithTag(getString(R.string.profile_contact_link_tag));
                                                    textView.setText(contactLink.toString());
                                                    mProfileUserContactsView.addView(contactLinkView);
                                                } catch (JSONException je) {

                                                }

                                            }
                                        }


                                        ParseQuery<ParseObject> postQuery = Post.getQuery();
                                        postQuery.whereEqualTo(Post.KEY_USER, user);
                                        postQuery.orderByDescending(Parsable.KEY_UPDATED_AT);
                                        postQuery.findInBackground(new FindCallback<ParseObject>() {
                                            @Override
                                            public void done(List<ParseObject> objects, ParseException e) {
                                                if (e == null) {
                                                    for (ParseObject object : objects) {
                                                        Post post = Post.getInstance(object);
                                                        PostCard postCard = new PostCard(self, post);
                                                        mProfilePostContainer.addView(postCard.getView());
                                                    }
                                                }
                                            }
                                        });

                                        try {
                                            if (avatarFile != null) {
                                                Uri avatar = Uri.fromFile(avatarFile.getFile());
                                                mProfilePhotoView.setImageURI(avatar);
                                            }
                                        } catch (ParseException p) {

                                        }

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

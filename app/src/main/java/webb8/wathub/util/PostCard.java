package webb8.wathub.util;

import android.app.Activity;
import webb8.wathub.R;
import android.support.v7.widget.CardView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import webb8.wathub.models.BookExchange;
import webb8.wathub.models.Carpool;
import webb8.wathub.models.GroupStudy;
import webb8.wathub.models.Post;
import webb8.wathub.models.PostType;
import webb8.wathub.models.Profile;

/**
 * Created by mismayil on 23/02/16.
 */
public class PostCard {

    // post types
    private static final String GENERIC = "GENERIC";
    private static final String BOOK_EXCHANGE = BookExchange.class.getSimpleName();
    private static final String GROUP_STUDY = GroupStudy.class.getSimpleName();
    private static final String CARPOOL = Carpool.class.getSimpleName();

    // UI elements
    private Activity mActivity;
    private CardView mPostView;
    private ImageView mPostAvatarView;
    private TextView mPostUserView;
    private TextView mPostDateView;
    private TextView mPostContentView;

    // member fields
    private Post mPost;

    public PostCard() {}

    public PostCard(Activity activity, Post post) {
        mActivity = activity;
        mPost = post;
    }

    public View getView(ViewGroup root) {
        View view = mActivity.getLayoutInflater().inflate(R.layout.item_post, root, false);
        mPostView = (CardView) mActivity.findViewById(R.id.post_card);
        mPostAvatarView = (ImageView) mActivity.findViewById(R.id.post_avatar);
        mPostUserView = (TextView) mActivity.findViewById(R.id.post_user);
        mPostDateView = (TextView) mActivity.findViewById(R.id.post_date);
        mPostContentView = (TextView) mActivity.findViewById(R.id.post_content);
        String content = mPost.getContent();
        ParseUser user = mPost.getUser();
        PostType postType = mPost.getPostType();
        Date postDate = mPost.getUpdatedAt();
        String typeName = GENERIC;
        Profile profile = null;

        if (postType != null) typeName = postType.getTypeName();

        ParseQuery<ParseObject> query = Profile.getQuery();
        query.whereEqualTo(Profile.KEY_OWNER, user);

        try {
            List<ParseObject> objects = query.find();
            if (objects.size() > 0) {
                profile = Profile.getInstance(objects.get(0));
            }

        } catch (ParseException e) {

        }

        //
        if (typeName.equalsIgnoreCase(BOOK_EXCHANGE)) {

        }

        if (typeName.equalsIgnoreCase(GROUP_STUDY)) {

        }

        if (typeName.equalsIgnoreCase(CARPOOL)) {

        }

        mPostAvatarView.setImageResource(R.drawable.bb8);
        if (profile != null) mPostUserView.setText(profile.getFirstName() + " " + profile.getLastName());
        SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy HH:mm a", Locale.CANADA);
        mPostDateView.setText(format.format(postDate));
        mPostContentView.setText(content);
        return view;
    }
}

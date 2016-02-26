package webb8.wathub.util;

import android.app.Activity;
import webb8.wathub.R;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
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
import webb8.wathub.models.PostTypes;
import webb8.wathub.models.Profile;

/**
 * Created by mismayil on 23/02/16.
 */
public class PostCard {

    // member fields
    protected Activity mActivity;
    protected Post mPost;

    public PostCard() {}

    public PostCard(Activity activity, Post post) {
        mActivity = activity;
        mPost = post;
    }

    public View getView() {
        View view = mActivity.getLayoutInflater().inflate(R.layout.card_post, null, false);
        CardView mPostView = (CardView) view.findViewById(R.id.post_card);
        ImageView mPostAvatarView = (ImageView) view.findViewById(R.id.post_avatar);
        TextView mPostUserView = (TextView) view.findViewById(R.id.post_user);
        TextView mPostDateView = (TextView) view.findViewById(R.id.post_date);
        TextView mPostContentView = (TextView) view.findViewById(R.id.post_content);
        ImageView mPostIconView = (ImageView) view.findViewById(R.id.post_type_icon);
        String content = mPost.getContent();
        ParseUser user = mPost.getUser();
        PostType postType = mPost.getPostType();
        Date postDate = mPost.getUpdatedAt();
        Profile profile = null;
        LinearLayout innerPostCard = (LinearLayout) view.findViewById(R.id.inner_postCard);

        mPostIconView.setImageResource(R.drawable.ic_lens_black_24dp);

        ParseQuery<ParseObject> query = Profile.getQuery();
        query.whereEqualTo(Profile.KEY_OWNER, user);

        try {
            List<ParseObject> objects = query.find();
            if (objects.size() > 0) {
                profile = Profile.getInstance(objects.get(0));
            }

        } catch (ParseException e) {

        }

        if (postType == PostTypes.BOOK_EXCHANGE.getType()) {
            mPostIconView.setImageResource(R.drawable.ic_book_black_24dp);
            ParseQuery<ParseObject> postQuery = BookExchange.getQuery();
            query.whereEqualTo(BookExchange.KEY_POST, mPost);

            try {
                ParseObject object = postQuery.getFirst();
                innerPostCard.addView(new BookExchangeCard(mActivity, BookExchange.getInstance(object)).getView());
            } catch (ParseException e) {

            }
        }

        if (postType == PostTypes.GROUP_STUDY.getType()) {

        }

        if (postType == PostTypes.CARPOOL.getType()) {

        }

        mPostAvatarView.setImageResource(R.drawable.no_avatar);
        if (profile != null) mPostUserView.setText(profile.getFirstName() + " " + profile.getLastName());
//        SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy HH:mm a", Locale.CANADA);
//        mPostDateView.setText(format.format(postDate));
        mPostContentView.setText(content);
        return view;
    }
}

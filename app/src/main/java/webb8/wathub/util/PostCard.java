package webb8.wathub.util;

import android.app.Activity;
import webb8.wathub.R;

import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import java.util.Date;
import java.util.List;

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

    private Profile mProfile;
    private BookExchangeCard mBookExchangeCard;
    private GroupStudyCard mGroupStudyCard;
    private CarpoolCard mCarpoolCard;

    public PostCard() {}

    public PostCard(Activity activity, Post post) {
        mActivity = activity;
        mPost = post;
        init();
    }

    public void init() {
        // get the profile
        ParseQuery<ParseObject> profileQuery = Profile.getQuery();
        profileQuery.whereEqualTo(Profile.KEY_OWNER, mPost.getUser());

        try {
            List<ParseObject> objects = profileQuery.find();
            mProfile = Profile.getInstance(objects.get(0));
        } catch(ParseException e) {

        }

        // get the specific post
        if (mPost.getPostType() == PostTypes.BOOK_EXCHANGE.getType()) {
            ParseQuery<ParseObject> postQuery = BookExchange.getQuery();
            postQuery.whereEqualTo(BookExchange.KEY_POST, mPost);
            try {
                List<ParseObject> objects = postQuery.find();
                mBookExchangeCard = new BookExchangeCard(mActivity, BookExchange.getInstance(objects.get(0)));
            } catch (ParseException e) {

            }
        }

        if (mPost.getPostType() == PostTypes.GROUP_STUDY.getType()) {
            ParseQuery<ParseObject> postQuery = GroupStudy.getQuery();
            postQuery.whereEqualTo(GroupStudy.KEY_POST, mPost);
            try {
                List<ParseObject> objects = postQuery.find();
                mGroupStudyCard = new GroupStudyCard(mActivity, GroupStudy.getInstance(objects.get(0)));
            } catch(ParseException e) {

            }
        }

        if (mPost.getPostType() == PostTypes.CARPOOL.getType()) {
            ParseQuery<ParseObject> postQuery = Carpool.getQuery();
            postQuery.whereEqualTo(Carpool.KEY_POST, mPost);
            try {
                List<ParseObject> objects = postQuery.find();
                mCarpoolCard = new CarpoolCard(mActivity, Carpool.getInstance(objects.get(0)));
            } catch(ParseException e) {

            }
        }
    }

    public Post getPost() {
        return mPost;
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
        PostType postType = mPost.getPostType();
        Date postDate = mPost.getUpdatedAt();
        LinearLayout innerPostCard = (LinearLayout) view.findViewById(R.id.inner_postCard);

        mPostIconView.setImageResource(R.drawable.ic_create_white_24dp);

        if (postType == PostTypes.BOOK_EXCHANGE.getType()) {
            mPostIconView.setImageResource(R.drawable.ic_book_black_24dp);
            innerPostCard.addView(mBookExchangeCard.getView());
        }

        if (postType == PostTypes.GROUP_STUDY.getType()) {
            mPostIconView.setImageResource(R.drawable.ic_group_black_24dp);
            innerPostCard.addView(mGroupStudyCard.getView());
        }

        if (postType == PostTypes.CARPOOL.getType()) {
            mPostIconView.setImageResource(R.drawable.ic_directions_car_white_24dp);
            innerPostCard.addView(mCarpoolCard.getView());
        }

        mPostAvatarView.setImageResource(R.drawable.no_avatar);
        if (mProfile != null){
            mPostUserView.setText(mProfile.getFirstName() + " " + mProfile.getLastName());
        }
//        SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy HH:mm a", Locale.CANADA);
//        mPostDateView.setText(format.format(postDate));
        mPostContentView.setText(content);
        return view;
    }

    public void refresh() {
        try {
            mProfile.fetch();
            mBookExchangeCard.refresh();
            mGroupStudyCard.refresh();
        } catch (ParseException e) {

        }
    }
}

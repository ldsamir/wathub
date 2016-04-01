package webb8.wathub.util;

import android.app.Activity;
import webb8.wathub.R;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import webb8.wathub.hub.ProfileActivity;
import webb8.wathub.models.BookExchange;
import webb8.wathub.models.Carpool;
import webb8.wathub.models.Comment;
import webb8.wathub.models.GroupStudy;
import webb8.wathub.models.Post;
import webb8.wathub.models.PostType;
import webb8.wathub.models.PostTypes;
import webb8.wathub.models.Profile;
import webb8.wathub.models.Vote;

/**
 * Created by mismayil on 23/02/16.
 */
public class PostCard {

    // member fields
    private PostCard self;
    protected Activity mActivity;
    protected Post mPost;
    protected View mPostCardView;
    protected CardView mPostView;
    protected LinearLayout mInnerPostCardView;
    protected ImageView mPostAvatarView;
    protected TextView mPostUserView;
    protected TextView mPostDateView;
    protected TextView mPostContentView;
    protected ImageView mPostIconView;
    protected ImageView mPostActionJoinView;
    protected ImageView mPostActionVoteView;
    protected ImageView mPostActionCommentView;
    protected LinearLayout mPostActionJoinBarView;
    protected LinearLayout mPostActionJoinNumBarView;
    protected TextView mPostActionVoteNumView;
    protected TextView mPostActionCommentNumView;


    private Profile mProfile;
    private BookExchangeCard mBookExchangeCard;
    private GroupStudyCard mGroupStudyCard;
    private CarpoolCard mCarpoolCard;
    private int mVoteCount;
    private int mCommentCount;
    private boolean mCurUserVoted;
    private boolean mCurUserCommented;

    public PostCard() {}

    public PostCard(Activity activity, Post post) {
        self = this;
        mActivity = activity;
        mPost = post;
        init();
    }

    public void init() {
        mVoteCount = 0;
        mCommentCount = 0;
        mCurUserVoted = false;
        mCurUserCommented = false;

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

        refresh();
    }

    public Post getPost() {
        return mPost;
    }

    public View getView() {
        mPostCardView = mActivity.getLayoutInflater().inflate(R.layout.card_post, null, false);
        mInnerPostCardView = (LinearLayout) mPostCardView.findViewById(R.id.inner_postCard);
        mPostView = (CardView) mPostCardView.findViewById(R.id.post_card);
        mPostAvatarView = (ImageView) mPostCardView.findViewById(R.id.post_avatar);
        mPostUserView = (TextView) mPostCardView.findViewById(R.id.post_user);
        mPostDateView = (TextView) mPostCardView.findViewById(R.id.post_date);
        mPostContentView = (TextView) mPostCardView.findViewById(R.id.post_content);
        mPostIconView = (ImageView) mPostCardView.findViewById(R.id.post_type_icon);
        mPostActionVoteView = (ImageView) mPostCardView.findViewById(R.id.post_action_vote);
        mPostActionCommentView = (ImageView) mPostCardView.findViewById(R.id.post_action_comment);
        mPostActionVoteNumView = (TextView) mPostCardView.findViewById(R.id.post_action_vote_num);
        mPostActionCommentNumView = (TextView) mPostCardView.findViewById(R.id.post_action_comment_num);

        String content = mPost.getContent();
        PostType postType = mPost.getPostType();

        mPostIconView.setImageResource(R.drawable.ic_event_black_24dp);

        if (postType == PostTypes.BOOK_EXCHANGE.getType()) {
            mPostIconView.setImageResource(R.drawable.ic_book_black_24dp);
            mInnerPostCardView.addView(mBookExchangeCard.getView(mPostCardView));
        } else

        if (postType == PostTypes.GROUP_STUDY.getType()) {
            mPostIconView.setImageResource(R.drawable.ic_group_black_24dp);
            mInnerPostCardView.addView(mGroupStudyCard.getView(mPostCardView));
        } else

        if (postType == PostTypes.CARPOOL.getType()) {
            mPostIconView.setImageResource(R.drawable.ic_directions_car_black_24dp);
            mInnerPostCardView.addView(mCarpoolCard.getView(mPostCardView));
        }

        mPostAvatarView.setImageResource(R.drawable.no_avatar);
        mPostUserView.setText(mProfile.getFirstName() + " " + mProfile.getLastName());
        mPostContentView.setText(content);

        updateVoteCount(0);
        updateCommentCount(0);

        mPostUserView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ParseUser user = mProfile.getOwner();
                try {
                    user.fetch();
                    Intent intent = new Intent(mActivity, ProfileActivity.class);
                    intent.putExtra(ProfileActivity.EXTRA_USER_NAME, user.getUsername());
                    mActivity.startActivity(intent);
                } catch (ParseException p) {

                }
            }
        });

        mPostActionVoteView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mCurUserVoted) {
                    ParseQuery<ParseObject> voteQuery = Vote.getQuery();
                    voteQuery.whereEqualTo(Vote.KEY_USER, ParseUser.getCurrentUser());
                    voteQuery.whereEqualTo(Vote.KEY_POST, mPost);
                    voteQuery.findInBackground(new FindCallback<ParseObject>() {
                        @Override
                        public void done(List<ParseObject> objects, ParseException e) {
                            for (ParseObject object : objects) {
                                object.deleteInBackground();
                            }
                        }
                    });
                    updateVoteCount(-1);
                } else {
                    Vote vote = new Vote();
                    vote.setUser(ParseUser.getCurrentUser());
                    vote.setPost(mPost);
                    vote.saveInBackground();
                    updateVoteCount(1);
                    Toast.makeText(mActivity.getApplicationContext(), R.string.action_voted, Toast.LENGTH_SHORT).show();
                }
            }
        });

        mPostActionVoteNumView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ParseQuery<ParseObject> voteQuery = Vote.getQuery();
                voteQuery.whereEqualTo(Vote.KEY_POST, mPost);
                voteQuery.findInBackground(new FindCallback<ParseObject>() {
                    @Override
                    public void done(List<ParseObject> objects, ParseException e) {
                        final String[] userNames = new String[objects.size()];
                        int i = 0;

                        for (ParseObject object : objects) {
                            Vote vote = Vote.getInstance(object);
                            ParseUser user = vote.getUser();
                            try {
                                user.fetch();
                            } catch (ParseException p) {

                            }
                            userNames[i++] = user.getUsername();
                        }

                        AlertDialog.Builder builder = new AlertDialog.Builder(mActivity);
                        builder.setTitle(R.string.title_users_liked);
                        builder.setItems(userNames, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intent = new Intent(mActivity, ProfileActivity.class);
                                intent.putExtra(ProfileActivity.EXTRA_USER_NAME, userNames[which]);
                                mActivity.startActivity(intent);
                            }
                        });
                        AlertDialog dialog = builder.create();
                        dialog.setCanceledOnTouchOutside(true);
                        dialog.show();
                    }
                });
            }
        });

        mPostActionCommentView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CommentDialogFragment commentDialogFragment = CommentDialogFragment.newInstance(self);
                commentDialogFragment.show(mActivity.getFragmentManager(), "commentDialog");
            }
        });

        Calendar today = Calendar.getInstance();
        Calendar postDate = Calendar.getInstance();
        postDate.setTime(mPost.getUpdatedAt());
        mPostDateView.setText(Util.getTimeDiff(today, postDate));

        return mPostCardView;
    }

    public void refresh() {
        try {
            mProfile.fetch();
            if (mBookExchangeCard != null) mBookExchangeCard.refresh();
            if (mGroupStudyCard != null) mGroupStudyCard.refresh();
            if (mCarpoolCard != null) mCarpoolCard.refresh();
        } catch (ParseException e) {

        }

        ParseQuery<ParseObject> voteQuery = Vote.getQuery();
        voteQuery.whereEqualTo(Vote.KEY_POST, mPost);

        try {
            mVoteCount = voteQuery.count();
            List<ParseObject> objects = voteQuery.find();

            for (ParseObject object : objects) {
                Vote vote = Vote.getInstance(object);
                if (vote.getUser() == ParseUser.getCurrentUser()) {
                    mCurUserVoted = true;
                    break;
                }

            }

        } catch (ParseException p) {

        }

        ParseQuery<ParseObject> commentQuery = Comment.getQuery();
        commentQuery.whereEqualTo(Vote.KEY_POST, mPost);

        try {
            mCommentCount = commentQuery.count();
            List<ParseObject> objects = commentQuery.find();

            for (ParseObject object : objects) {
                Comment comment = Comment.getInstance(object);
                if (comment.getUser() == ParseUser.getCurrentUser()) {
                    mCurUserCommented = true;
                    break;
                }
            }
        } catch (ParseException p) {

        }
    }

    public void updateVoteCount(int count) {
        if (count > 0) mCurUserVoted = true;
        if (count < 0) mCurUserVoted = false;
        if (mCurUserVoted) mPostActionVoteView.setImageResource(R.drawable.ic_favorite_black_24dp);
        else mPostActionVoteView.setImageResource(R.drawable.ic_favorite_border_black_24dp);
        mVoteCount += count;
        if (mVoteCount > 0) mPostActionVoteNumView.setText(String.valueOf(mVoteCount));
        else mPostActionVoteNumView.setText("");
    }

    public void updateCommentCount(int count) {
        if (count > 0) mCurUserCommented = true;
        if (count < 0) mCurUserCommented = false;
        if (mCurUserCommented) mPostActionCommentView.setImageResource(R.drawable.ic_mode_comment_black_24dp);
        else mPostActionCommentView.setImageResource(R.drawable.ic_comment_black_24dp);
        mCommentCount += count;
        if (mCommentCount > 0) mPostActionCommentNumView.setText(String.valueOf(mCommentCount));
        else mPostActionCommentNumView.setText("");
    }
}

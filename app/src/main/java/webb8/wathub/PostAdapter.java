package webb8.wathub;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
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
 * Created by mismayil on 20/02/16.
 */
public class PostAdapter extends RecyclerView.Adapter<PostAdapter.PostViewHolder> {
    List<Post> posts;

    // post types
    public static final String GENERIC = "GENERIC";
    public static final String BOOK_EXCHANGE = BookExchange.class.getSimpleName();
    public static final String GROUP_STUDY = GroupStudy.class.getSimpleName();
    public static final String CARPOOL = Carpool.class.getSimpleName();

    public static class PostViewHolder extends RecyclerView.ViewHolder {
        CardView postView;
        ImageView postAvatarView;
        TextView postUserView;
        TextView postDateView;
        TextView postContentView;

        PostViewHolder(View itemView) {
            super(itemView);
            postView = (CardView) itemView.findViewById(R.id.post_card);
            postAvatarView = (ImageView) itemView.findViewById(R.id.post_avatar);
            postUserView = (TextView) itemView.findViewById(R.id.post_user);
            postDateView = (TextView) itemView.findViewById(R.id.post_date);
            postContentView = (TextView) itemView.findViewById(R.id.post_content);
        }
    }

    PostAdapter(List<Post> posts) {
        this.posts = posts;
    }

    @Override
    public PostViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_post, viewGroup, false);
        return new PostViewHolder(view);
    }

    @Override
    public void onBindViewHolder(PostViewHolder postViewHolder, int i) {
        Post post = posts.get(i);
        String content = post.getContent();
        ParseUser user = post.getUser();
        PostType postType = post.getPostType();
        Date postDate = post.getUpdatedAt();
        String typeName = GENERIC;
        Profile profile = null;

        if (postType != null) typeName = postType.getTypeName();

        ParseQuery<ParseObject> query = Profile.getQuery();
        query.whereEqualTo("owner", user);

        try {
            List<ParseObject> objects = query.find();
            if (objects.size() > 0) {
                profile = Profile.getInstance(objects.get(0));
            }

        } catch (ParseException e) {

        }

        if (typeName.equalsIgnoreCase(BOOK_EXCHANGE)) {

        }

        if (typeName.equalsIgnoreCase(GROUP_STUDY)) {

        }

        if (typeName.equalsIgnoreCase(CARPOOL)) {

        }

        postViewHolder.postAvatarView.setImageResource(R.drawable.bb8);
        if (profile != null) postViewHolder.postUserView.setText(profile.getFirstName() + " " + profile.getLastName());
        SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy HH:mm a", Locale.CANADA);
        postViewHolder.postDateView.setText(format.format(postDate));
        postViewHolder.postContentView.setText(post.getContent());
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public int getItemCount() {
        return posts.size();
    }
}

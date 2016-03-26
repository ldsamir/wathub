package webb8.wathub.util;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.List;

import webb8.wathub.R;
import webb8.wathub.models.Done;
import webb8.wathub.models.Favorite;
import webb8.wathub.models.Post;

/**
 * Created by mismayil on 20/02/16.
 */
public class PostAdapter extends RecyclerView.Adapter<PostAdapter.PostCardViewHolder> {
    List<PostCard> mPostCards;

    class PostCardViewHolder extends RecyclerView.ViewHolder {

        PostCardViewHolder(View itemView) {
            super(itemView);
        }
    }

    public PostAdapter(List<PostCard> postCards) {
        mPostCards = postCards;
    }

    @Override
    public PostCardViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_holder, viewGroup, false);
        return new PostCardViewHolder(view);
    }

    @Override
    public void onBindViewHolder(PostCardViewHolder postViewHolder, int i) {
        PostCard postCard = mPostCards.get(i);
        LinearLayout holderView = (LinearLayout) postViewHolder.itemView;
        holderView.removeAllViews();
        holderView.addView(postCard.getView());
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    public void addPostCard(PostCard postCard) {
        int position = mPostCards.size();
        for (int i = 0; i < mPostCards.size(); i++) {
            if (postCard.getPost().getUpdatedAt().after(mPostCards.get(i).getPost().getUpdatedAt())) {
                position = i;
                break;
            }
        }
        mPostCards.add(position, postCard);
        notifyDataSetChanged();
    }

    public void removePostCard(int position, final PostState state) {
        final Post post = mPostCards.get(position).getPost();
        ParseQuery<ParseObject> favoriteQuery = Favorite.getQuery();
        ParseQuery<ParseObject> doneQuery = Done.getQuery();
        favoriteQuery.whereEqualTo(Favorite.KEY_POST, post);
        doneQuery.whereEqualTo(Done.KEY_POST, post);

        favoriteQuery.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                if (e == null) {
                    for (ParseObject object : objects) {
                        object.deleteInBackground();
                    }

                    if (state == PostState.FAVORITED) {
                        Favorite favorite = new Favorite();
                        favorite.setUser(ParseUser.getCurrentUser());
                        favorite.setPost(post);
                        favorite.saveInBackground();
                    }

                }
            }
        });


        doneQuery.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                if (e == null) {
                    for (ParseObject object : objects) {
                        object.deleteInBackground();
                    }

                    if (state == PostState.DONE) {
                        Done done = new Done();
                        done.setUser(ParseUser.getCurrentUser());
                        done.setPost(post);
                        done.saveInBackground();
                    }

                }
            }
        });

        mPostCards.remove(position);
        notifyItemRemoved(position);
    }

    public void refreshPostCards() {
        for (PostCard postCard : mPostCards) {
            postCard.refresh();
        }
        notifyDataSetChanged();
    }

    public void setPostCards(List<PostCard> postCards) {
        mPostCards = postCards;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return mPostCards.size();
    }
}

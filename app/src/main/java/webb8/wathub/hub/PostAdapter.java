package webb8.wathub.hub;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import java.util.List;

import webb8.wathub.R;
import webb8.wathub.util.PostCard;

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

    @Override
    public int getItemCount() {
        return mPostCards.size();
    }
}

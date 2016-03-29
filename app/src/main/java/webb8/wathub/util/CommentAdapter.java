package webb8.wathub.util;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.parse.ParseException;
import com.parse.ParseUser;

import java.util.List;

import webb8.wathub.R;
import webb8.wathub.models.Comment;

/**
 * Created by mismayil on 3/28/16.
 */
public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.CommentHolder> {
    private List<Comment> mComments;

    class CommentHolder extends RecyclerView.ViewHolder {
        CommentHolder(View itemView) { super(itemView); }
    }

    public CommentAdapter(List<Comment> comments) {
        mComments = comments;
    }

    @Override
    public CommentHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.dialog_comment, viewGroup, false);
        return new CommentHolder(view);
    }

    @Override
    public void onBindViewHolder(CommentHolder holder, int position) {
        Comment comment = mComments.get(position);
        View view = holder.itemView;
        TextView authorView = (TextView) view.findViewById(R.id.dialog_comment_author);
        TextView contentView = (TextView) view.findViewById(R.id.dialog_comment_content);
        ParseUser author = comment.getUser();
        try {
            author.fetch();
        } catch (ParseException p) {

        }
        authorView.setText(author.getUsername());
        contentView.setText(comment.getComment());
    }

    public void addComment(Comment comment) {
        mComments.add(comment);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return mComments.size();
    }

    public void setComments(List<Comment> comments) {
        mComments = comments;
        notifyDataSetChanged();
    }
}

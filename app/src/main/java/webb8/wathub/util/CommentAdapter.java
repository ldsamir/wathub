package webb8.wathub.util;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.parse.ParseException;
import com.parse.ParseUser;

import java.util.Calendar;
import java.util.List;

import webb8.wathub.R;
import webb8.wathub.hub.ProfileActivity;
import webb8.wathub.models.Comment;

/**
 * Created by mismayil on 3/28/16.
 */
public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.CommentHolder> {
    private List<Comment> mComments;
    private Activity mActivity;

    class CommentHolder extends RecyclerView.ViewHolder {
        CommentHolder(View itemView) { super(itemView); }
    }

    public CommentAdapter(Activity activity, List<Comment> comments) {
        mActivity = activity;
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
        TextView authorView = (TextView) view.findViewById(R.id.comment_user);
        TextView contentView = (TextView) view.findViewById(R.id.comment_content);
        TextView dateView = (TextView) view.findViewById(R.id.comment_date);

        final ParseUser author = comment.getUser();

        try {
            author.fetch();
        } catch (ParseException p) {

        }

        authorView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mActivity, ProfileActivity.class);
                intent.putExtra(ProfileActivity.EXTRA_USER_NAME, author.getUsername());
                mActivity.startActivity(intent);
            }
        });

        Calendar commentDate = Calendar.getInstance();
        commentDate.setTime(comment.getUpdatedAt());

        authorView.setText(author.getUsername());
        contentView.setText(comment.getComment());
        dateView.setText(Util.getTimeDiff(Calendar.getInstance(), commentDate));
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

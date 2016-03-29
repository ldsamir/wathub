package webb8.wathub.util;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

import webb8.wathub.R;
import webb8.wathub.models.Comment;
import webb8.wathub.models.Parsable;

/**
 * Created by mismayil on 3/28/16.
 */
public class CommentDialogFragment extends DialogFragment {
    private PostCard mPostCard;
    private int newCommentCount;

    public static CommentDialogFragment newInstance(PostCard postCard) {
        CommentDialogFragment fragment = new CommentDialogFragment();
        fragment.setPostCard(postCard);
        return fragment;
    }

    public void setPostCard(PostCard postCard) {
        mPostCard = postCard;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View commentView = inflater.inflate(R.layout.dialog_comment_list, null);
        final RecyclerView commentListView = (RecyclerView) commentView.findViewById(R.id.dialog_comment_container);
        final EditText commentContentView = (EditText) commentView.findViewById(R.id.edit_dialog_comment);
        Button commentBtnView = (Button) commentView.findViewById(R.id.btn_dialog_comment);

        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        commentListView.setLayoutManager(llm);
        final CommentAdapter commentAdapter = new CommentAdapter(new ArrayList<Comment>());
        commentListView.setAdapter(commentAdapter);

        ParseQuery<ParseObject> commentQuery = Comment.getQuery();
        commentQuery.whereEqualTo(Comment.KEY_POST, mPostCard.getPost());
        commentQuery.orderByDescending(Parsable.KEY_UPDATED_AT);
        commentQuery.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                List<Comment> comments = new ArrayList<>();

                for (ParseObject object : objects) {
                    Comment comment = Comment.getInstance(object);
                    comments.add(comment);
                }

                commentAdapter.setComments(comments);
            }
        });

        commentBtnView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String content = commentContentView.getText().toString();
                Comment comment = new Comment();
                comment.setUser(ParseUser.getCurrentUser());
                comment.setPost(mPostCard.getPost());
                comment.setComment(content);
                comment.saveInBackground();
                commentAdapter.addComment(comment);
                newCommentCount++;
            }
        });

        builder.setView(commentView);
        builder.setTitle(R.string.title_comment);
        builder.setPositiveButton(R.string.action_ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                mPostCard.updateCommentCount(newCommentCount);
            }
        });
        builder.setNegativeButton(R.string.action_cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                mPostCard.updateCommentCount(newCommentCount);
            }
        });

        AlertDialog dialog = builder.create();
        dialog.setCanceledOnTouchOutside(false);

        return dialog;
    }
}

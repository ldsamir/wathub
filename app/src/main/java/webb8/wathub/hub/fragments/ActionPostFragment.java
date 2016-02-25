package webb8.wathub.hub.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.parse.ParseUser;

import webb8.wathub.R;
import webb8.wathub.hub.Action;
import webb8.wathub.models.Post;

/**
 * This fragment handles posting actions
 */
public class ActionPostFragment extends HubFragment {

    public ActionPostFragment() {
        // Required empty public constructor
    }

    public static ActionPostFragment newInstance(int action) {
        ActionPostFragment fragment = null;

        if (action == Action.ACTION_POST_GENERAL.getId()) {
            fragment = new ActionPostFragment();
        } else

        if (action == Action.ACTION_POST_BOOK_EXCHANGE.getId()) {
            fragment = new ActionBookExchangePostFragment();
        } else

        if (action == Action.ACTION_POST_GROUP_STUDY.getId()) {
            fragment = new ActionGroupStudyPostFragment();
        }

        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View actionPostView = inflater.inflate(R.layout.fragment_action_post, container, false);
        final EditText editContent = (EditText) actionPostView.findViewById(R.id.edit_post_content);
        Button buttonPost = (Button) actionPostView.findViewById(R.id.action_post_go);

        final Post post = new Post();

        buttonPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                post.setUser(ParseUser.getCurrentUser());
                post.setContent(editContent.getText().toString());
                post.setPostType(null);

                post.saveInBackground();
            }
        });
        return actionPostView;
    }
}

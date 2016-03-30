package webb8.wathub.hub.fragments.actions;

import android.app.FragmentManager;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import webb8.wathub.R;
import webb8.wathub.util.Action;
import webb8.wathub.util.NavItem;
import webb8.wathub.hub.fragments.HubFragment;
import webb8.wathub.models.Post;

/**
 * This fragment handles posting actions
 */
public class ActionPostFragment extends HubFragment {

    // UI fields
    protected EditText mContentView;
    protected Button mPostBtnView;
    protected RelativeLayout mProgressBar;

    public ActionPostFragment() {}

    public static ActionPostFragment newInstance(int action) {
        ActionPostFragment fragment = null;
        if (action == Action.ACTION_POST_GENERAL.getId()) {
            fragment = new ActionPostFragment();
        } else

        if (action == Action.ACTION_POST_BOOK_EXCHANGE.getId()) {
            fragment = new ActionPostBookExchangeFragment();
        } else

        if (action == Action.ACTION_POST_GROUP_STUDY.getId()) {
            fragment = new ActionPostGroupStudyFragment();
        } else

        if (action == Action.ACTION_CARPOOL.getId()) {
            fragment = new ActionPostCarpoolFragment();
        }

        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View actionPostView = inflater.inflate(R.layout.fragment_action_post, container, false);
        mContentView = (EditText) actionPostView.findViewById(R.id.edit_post_content);
        mPostBtnView = (Button) actionPostView.findViewById(R.id.action_post_go);
        mProgressBar = (RelativeLayout) actionPostView.findViewById(R.id.progress_bar);

        final Post post = new Post();

        mPostBtnView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkInput()) {
                    mProgressBar.setVisibility(View.VISIBLE);
                    post.setUser(ParseUser.getCurrentUser());
                    post.setContent(mContentView.getText().toString());

                    post.saveInBackground(new SaveCallback() {
                        @Override
                        public void done(ParseException e) {
                            if (e == null) {
                                mProgressBar.setVisibility(View.GONE);
                                FragmentManager fragmentManager = getFragmentManager();
                                Toast.makeText(mHubActivity.getApplicationContext(), R.string.info_post_published, Toast.LENGTH_SHORT).show();
                                fragmentManager.beginTransaction().replace(R.id.container, HubFragment.newInstance(NavItem.ALL_POSTS.getId())).commit();
                                mHubActivity.setTitle(mHubActivity.getString(NavItem.ALL_POSTS.getNameId()));
                            } else {
                                Toast.makeText(mHubActivity.getApplicationContext(), R.string.error_publishing_post, Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });

        return actionPostView;
    }

    private boolean checkInput() {
        mContentView.setError(null);

        if (TextUtils.isEmpty(mContentView.getText().toString())) {
            mContentView.setError(getString(R.string.error_post_empty_content));
            return false;
        }

        return true;
    }
}

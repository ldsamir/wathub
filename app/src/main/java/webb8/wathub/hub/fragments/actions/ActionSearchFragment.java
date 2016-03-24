package webb8.wathub.hub.fragments.actions;

/**
 * Created by mismayil on 3/23/16.
 */

import android.app.FragmentManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.Spinner;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

import webb8.wathub.R;
import webb8.wathub.hub.Action;
import webb8.wathub.hub.NavItem;
import webb8.wathub.hub.fragments.HubFragment;
import webb8.wathub.hub.fragments.PostFeedFragment;
import webb8.wathub.models.Post;
import webb8.wathub.util.PostCard;

/**
 * This fragment handles searching for posts.
 */
public class ActionSearchFragment extends HubFragment {

    // UI fields
    protected EditText mContentView;
    protected Button mSearchBtnView;
    protected Spinner mSearchTypeView;
    protected FrameLayout mActionSearchContainer;

    public ActionSearchFragment() {}

    public static ActionSearchFragment newInstance(int action) {
        ActionSearchFragment fragment = null;

        if (action == Action.ACTION_SEARCH.getId()) {
            fragment = new ActionSearchFragment();
        } else

        if (action == Action.ACTION_SEARCH_BOOK_EXCHANGE.getId()) {
            fragment = new ActionSearchBookExchangeFragment();
        } else

        if (action == Action.ACTION_SEARCH_GROUP_STUDY.getId()) {
            fragment = new ActionSearchGroupStudyFragment();
        } else

        if (action == Action.ACTION_SEARCH_CARPOOL.getId()) {
            fragment = new ActionSearchCarpoolFragment();
        }

        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View actionSearchView = inflater.inflate(R.layout.fragment_action_search, container, false);

        mContentView = (EditText) actionSearchView.findViewById(R.id.edit_search_content);
        mSearchTypeView = (Spinner) actionSearchView.findViewById(R.id.select_search_type);
        mSearchBtnView = (Button) actionSearchView.findViewById(R.id.btn_search);

        mSearchTypeView.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                FragmentManager fragmentManager = getFragmentManager();

                if (parent.getItemAtPosition(position).toString().equalsIgnoreCase(getString(NavItem.ALL_POSTS.getNameId()))) {
                    fragmentManager.beginTransaction()
                            .replace(R.id.container, ActionSearchFragment.newInstance(Action.ACTION_SEARCH.getId()))
                            .commit();
                } else if (parent.getItemAtPosition(position).toString().equalsIgnoreCase(getString(NavItem.BOOK_EXCHANGE_POSTS.getNameId()))) {
                    fragmentManager.beginTransaction()
                            .replace(R.id.container, ActionSearchFragment.newInstance(Action.ACTION_SEARCH_BOOK_EXCHANGE.getId()))
                            .commit();
                } else if (parent.getItemAtPosition(position).toString().equalsIgnoreCase(getString(NavItem.GROUP_STUDY_POSTS.getNameId()))) {
                    fragmentManager.beginTransaction()
                            .replace(R.id.container, ActionSearchFragment.newInstance(Action.ACTION_SEARCH_GROUP_STUDY.getId()))
                            .commit();
                } else if (parent.getItemAtPosition(position).toString().equalsIgnoreCase(getString(NavItem.CARPOOL_POSTS.getNameId()))) {
                    fragmentManager.beginTransaction()
                            .replace(R.id.container, ActionSearchFragment.newInstance(Action.ACTION_SEARCH_CARPOOL.getId()))
                            .commit();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        mSearchBtnView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String content = mContentView.getText().toString();
                ParseQuery<ParseObject> postQuery = Post.getQuery();
                postQuery.whereContains(Post.KEY_CONTENT, content);
                postQuery.findInBackground(new FindCallback<ParseObject>() {
                    @Override
                    public void done(List<ParseObject> objects, ParseException e) {
                        List<PostCard> postCards = new ArrayList<>();

                        for (ParseObject object : objects) {
                            Post post = Post.getInstance(object);
                            postCards.add(new PostCard(mHubActivity, post));
                        }

                        PostFeedFragment postFeedFragment = PostFeedFragment.newInstance(postCards);
                        FragmentManager fragmentManager = getFragmentManager();
                        fragmentManager.beginTransaction()
                                .replace(R.id.container, postFeedFragment)
                                .commit();
                    }
                });

            }
        });

        return actionSearchView;
    }
}

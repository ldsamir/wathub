package webb8.wathub.hub.fragments.navigation;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.github.brnunes.swipeablerecyclerview.SwipeableRecyclerViewTouchListener;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

import webb8.wathub.R;
import webb8.wathub.hub.NavItem;
import webb8.wathub.hub.PostAdapter;
import webb8.wathub.hub.fragments.HubFragment;
import webb8.wathub.models.Parsable;
import webb8.wathub.models.Post;
import webb8.wathub.util.PostCard;
import webb8.wathub.util.Util;

/**
 * Created by mismayil on 23/02/16.
 */
public class PostFragment extends HubFragment {

    protected RecyclerView mPostContainerView;
    protected ParseQuery<ParseObject> mPostQuery;
    protected FrameLayout mProgressBar;
    protected PostAdapter mPostAdapter;

    public PostFragment() {}
    
    public static PostFragment newInstance(int action) {
        PostFragment fragment = null;

        if (action == NavItem.ALL_POSTS.getId()) {
            fragment = new PostFragment();
        } else

        if (action == NavItem.BOOK_EXCHANGE_POSTS.getId()) {
            fragment = new BookExchangeFragment();
        } else

        if (action == NavItem.GROUP_STUDY_POSTS.getId()) {
            fragment = new GroupStudyFragment();
        } else

        if (action == NavItem.CARPOOL_POSTS.getId()) {
            fragment = new CarpoolFragment();
        } else

        if (action == NavItem.FAVORITES.getId()) {
            fragment = new FavoriteFragment();
        } else

        if (action == NavItem.DONE.getId()) {
            fragment = new DoneFragment();
        }

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPostQuery = Post.getQuery();
        mPostQuery.orderByDescending(Parsable.KEY_UPDATED_AT);
        mProgressBar = (FrameLayout) mHubActivity.findViewById(R.id.progress_bar);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup viewContainer, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_post, viewContainer, false);
        mPostContainerView = (RecyclerView) rootView.findViewById(R.id.post_container);
        LinearLayoutManager llm = new LinearLayoutManager(mHubActivity);
        mPostContainerView.setLayoutManager(llm);
        mPostAdapter = new PostAdapter(new ArrayList<PostCard>());
        mPostContainerView.setAdapter(mPostAdapter);
        mPostContainerView.addOnItemTouchListener(Util.getSwipeTouchListener(mPostContainerView, mPostAdapter));
        mProgressBar.setVisibility(View.VISIBLE);
        final SwipeRefreshLayout refreshLayout = (SwipeRefreshLayout) rootView;
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refresh();
                refreshLayout.setRefreshing(false);
            }
        });
        return rootView;
    }

    @Override
    public void onViewCreated(final View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        refresh();
    }

    public void refresh() {
        mPostQuery.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                if (e == null) {
                    final List<PostCard> postCards = new ArrayList<>();
                    for (ParseObject object : objects) {
                        Post post = Post.getInstance(object);
                        postCards.add(new PostCard(mHubActivity, post));
                    }
                    mProgressBar.setVisibility(View.GONE);
                    mPostAdapter.setPostCards(postCards);
                } else {
                    Toast.makeText(mHubActivity.getApplicationContext(), R.string.error_loading_posts, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
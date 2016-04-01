package webb8.wathub.hub.fragments.navigation;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.parse.CountCallback;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

import webb8.wathub.R;
import webb8.wathub.models.Done;
import webb8.wathub.models.Favorite;
import webb8.wathub.util.NavItem;
import webb8.wathub.util.PostAdapter;
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
    protected RelativeLayout mProgressBar;
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
        mProgressBar = (RelativeLayout) mHubActivity.findViewById(R.id.progress_bar);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup viewContainer, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_post, viewContainer, false);
        mPostContainerView = (RecyclerView) rootView.findViewById(R.id.post_container);
        LinearLayoutManager llm = new LinearLayoutManager(mHubActivity);
        mPostContainerView.setLayoutManager(llm);
        mPostAdapter = new PostAdapter(mHubActivity, new ArrayList<PostCard>());
        mPostContainerView.setAdapter(mPostAdapter);
        mPostContainerView.addOnItemTouchListener(Util.getSwipeTouchListener(mPostContainerView, mPostAdapter));
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
        mProgressBar.setVisibility(View.VISIBLE);
        mPostQuery.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(final List<ParseObject> objects, ParseException e) {
                if (e == null) {
                    if (objects.size() == 0) mProgressBar.setVisibility(View.GONE);
                    mPostAdapter.setPostCards(new ArrayList<PostCard>());
                    Thread thread = new Thread() {
                        @Override
                        public void run() {
                            for (final ParseObject object : objects) {
                                final Post post = Post.getInstance(object);
                                ParseQuery<ParseObject> doneQuery = Done.getQuery();
                                doneQuery.whereEqualTo(Done.KEY_POST, post);
                                doneQuery.whereEqualTo(Done.KEY_USER, ParseUser.getCurrentUser());
                                doneQuery.countInBackground(new CountCallback() {
                                    @Override
                                    public void done(int count, ParseException e) {
                                        if (e == null) {
                                            if (count == 0) {
                                                ParseQuery<ParseObject> favoriteQuery = Favorite.getQuery();
                                                favoriteQuery.whereEqualTo(Favorite.KEY_POST, post);
                                                favoriteQuery.whereEqualTo(Favorite.KEY_USER, ParseUser.getCurrentUser());
                                                favoriteQuery.countInBackground(new CountCallback() {
                                                    @Override
                                                    public void done(int count, ParseException e) {
                                                        if (e == null) {
                                                            if (count == 0) {
                                                                mPostAdapter.addPostCard(new PostCard(mHubActivity, post));
                                                                mProgressBar.setVisibility(View.GONE);
                                                            }
                                                        }
                                                    }
                                                });
                                            }
                                        }
                                    }
                                });
                            }
                        }
                    };
                    thread.start();
                } else {
                    Toast.makeText(mHubActivity.getApplicationContext(), R.string.error_loading_posts, Toast.LENGTH_SHORT).show();
                    mProgressBar.setVisibility(View.GONE);
                }
            }
        });
    }
}

package webb8.wathub.hub.fragments.navigation;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

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

/**
 * Created by mismayil on 23/02/16.
 */
public class PostFragment extends HubFragment {

    protected RecyclerView mPostContainerView;
    protected ParseQuery<ParseObject> mPostQuery;

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
        }

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPostQuery = Post.getQuery();
        mPostQuery.orderByDescending(Parsable.KEY_UPDATED_AT);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup viewContainer, Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_post, viewContainer, false);
        mPostContainerView = (RecyclerView) rootView;
        LinearLayoutManager llm = new LinearLayoutManager(mHubActivity);
        mPostContainerView.setLayoutManager(llm);
        mPostContainerView.setAdapter(new PostAdapter(new ArrayList<PostCard>()));
        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mPostQuery.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                if (e == null) {
                    List<PostCard> postCards = new ArrayList<>();
                    for (ParseObject object : objects) {
                        Post post = Post.getInstance(object);
                        postCards.add(new PostCard(mHubActivity, post));
                    }
                    PostAdapter postAdapter = new PostAdapter(postCards);
                    mPostContainerView.setAdapter(postAdapter);
                } else {
                    Toast.makeText(mHubActivity.getApplicationContext(), R.string.error_loading_posts, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}

package webb8.wathub.hub.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import webb8.wathub.R;
import webb8.wathub.hub.PostAdapter;
import webb8.wathub.util.PostCard;

/**
 * Created by mismayil on 3/23/16.
 */
public class PostFeedFragment extends Fragment {
    protected List<PostCard> mPostCards;
    protected RecyclerView mPostContainerView;

    public PostFeedFragment() {}

    public static PostFeedFragment newInstance(List<PostCard> postCards) {
        PostFeedFragment postFeedFragment = new PostFeedFragment();
        postFeedFragment.setmPostCards(postCards);
        return postFeedFragment;
    }

    public void setmPostCards(List<PostCard> postCards) { mPostCards = postCards; }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View postFeedView = inflater.inflate(R.layout.fragment_post, container, false);
        mPostContainerView = (RecyclerView) postFeedView;
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        mPostContainerView.setLayoutManager(llm);
        mPostContainerView.setAdapter(new PostAdapter(new ArrayList<PostCard>()));
        return postFeedView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        PostAdapter postAdapter = new PostAdapter(mPostCards);
        mPostContainerView.setAdapter(postAdapter);
    }
}

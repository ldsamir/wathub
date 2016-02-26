package webb8.wathub.hub.fragments;

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
import webb8.wathub.hub.PostAdapter;
import webb8.wathub.models.Parsable;
import webb8.wathub.models.Post;
import webb8.wathub.util.PostCard;

/**
 * Created by mismayil on 23/02/16.
 */
public class PostFragment extends HubFragment {

    protected RecyclerView mPostContainerView;

    public PostFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup viewContainer, Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_post, viewContainer, false);
        mPostContainerView = (RecyclerView) rootView;
        LinearLayoutManager llm = new LinearLayoutManager(mHubActivity.getApplicationContext());
        mPostContainerView.setLayoutManager(llm);
        mPostContainerView.setAdapter(new PostAdapter(new ArrayList<View>()));
        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ParseQuery<ParseObject> query = Post.getQuery();
        query.orderByDescending(Parsable.KEY_UPDATED_AT);
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                if (e == null) {
                    List<View> mPostCardViews = new ArrayList<>();
                    for (ParseObject object : objects) {
                        Post post = Post.getInstance(object);
                        mPostCardViews.add(new PostCard(mHubActivity, post).getView());
                    }
                    PostAdapter postAdapter = new PostAdapter(mPostCardViews);
                    mPostContainerView.setAdapter(postAdapter);
                } else {
                    Toast.makeText(mHubActivity.getApplicationContext(), R.string.error_loading_posts, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}

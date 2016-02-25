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

    public PostFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup viewContainer, Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_post, viewContainer, false);
        ParseQuery<ParseObject> query = Post.getQuery();
        query.orderByDescending(Parsable.KEY_UPDATED_AT);
        final RecyclerView postContainer = (RecyclerView) rootView;
        LinearLayoutManager llm = new LinearLayoutManager(mHubActivity.getApplicationContext());
        postContainer.setLayoutManager(llm);
        postContainer.setAdapter(new PostAdapter(new ArrayList<PostCard>()));

        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                if (e == null) {
                    List<PostCard> postCards = new ArrayList<>();

                    for (ParseObject object : objects) {
                        Post post = Post.getInstance(object);
                        postCards.add(new PostCard(mHubActivity, post));
                    }

                    PostAdapter postAdapter = new PostAdapter(postCards);
                    postContainer.setAdapter(postAdapter);
                } else {
                    Toast.makeText(mHubActivity.getApplicationContext(), R.string.error_loading_posts, Toast.LENGTH_SHORT).show();
                }
            }
        });

        return rootView;
    }
}

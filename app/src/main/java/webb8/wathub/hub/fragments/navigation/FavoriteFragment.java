package webb8.wathub.hub.fragments.navigation;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

import webb8.wathub.R;
import webb8.wathub.hub.fragments.HubFragment;
import webb8.wathub.models.Done;
import webb8.wathub.models.Favorite;
import webb8.wathub.models.Parsable;
import webb8.wathub.models.Post;
import webb8.wathub.util.PostCard;

/**
 * Created by mismayil on 25/02/16.
 */
public class FavoriteFragment extends PostFragment {
    private ParseQuery<ParseObject> mFavoriteQuery;

    public FavoriteFragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mFavoriteQuery = Favorite.getQuery();
        mFavoriteQuery.orderByDescending(Parsable.KEY_UPDATED_AT);
        mFavoriteQuery.whereEqualTo(Favorite.KEY_USER, ParseUser.getCurrentUser());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        refresh();
    }

    @Override
    public void refresh() {
        mProgressBar.setVisibility(View.VISIBLE);
        mFavoriteQuery.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                if (e == null) {
                    List<PostCard> postCards = new ArrayList<>();
                    for (ParseObject object : objects) {
                        Favorite favorite = Favorite.getInstance(object);
                        Post post = favorite.getPost();
                        try {
                            post.fetch();
                            postCards.add(new PostCard(getActivity(), post));
                        } catch (ParseException p) {

                        }
                    }

                    mProgressBar.setVisibility(View.GONE);
                    mPostAdapter.setPostCards(postCards);
                } else {
                    Toast.makeText(mHubActivity, R.string.error_loading_posts, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}

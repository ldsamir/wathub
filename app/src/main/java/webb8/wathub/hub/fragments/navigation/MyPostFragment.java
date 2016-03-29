package webb8.wathub.hub.fragments.navigation;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.parse.ParseUser;

import webb8.wathub.hub.fragments.HubFragment;
import webb8.wathub.models.Post;
import webb8.wathub.models.PostTypes;

/**
 * Created by mismayil on 25/02/16.
 */
public class MyPostFragment extends PostFragment {

    public MyPostFragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPostQuery.whereEqualTo(Post.KEY_USER, ParseUser.getCurrentUser());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup viewContainer, Bundle savedInstanceState) {
        return super.onCreateView(inflater, viewContainer, savedInstanceState);
    }
}

package webb8.wathub.hub.fragments.navigation;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import webb8.wathub.models.Post;
import webb8.wathub.models.PostTypes;

/**
 * Created by mismayil on 23/02/16.
 */
public class CarpoolFragment extends PostFragment {

    public CarpoolFragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPostQuery.whereEqualTo(Post.KEY_POST_TYPE, PostTypes.CARPOOL.getType());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup viewContainer, Bundle savedInstanceState) {
        return super.onCreateView(inflater, viewContainer, savedInstanceState);
    }
}

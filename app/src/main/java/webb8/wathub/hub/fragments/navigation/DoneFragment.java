package webb8.wathub.hub.fragments.navigation;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import webb8.wathub.models.Done;

/**
 * Created by mismayil on 3/25/16.
 */
public class DoneFragment extends PostFragment {
    private ParseQuery<ParseObject> mDoneQuery;

    public DoneFragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mDoneQuery = Done.getQuery();
        mDoneQuery.whereEqualTo(Done.KEY_USER, ParseUser.getCurrentUser());
//        mPostQuery.whereEqualTo(Post.KEY_POST_TYPE, PostTypes.BOOK_EXCHANGE.getType());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup viewContainer, Bundle savedInstanceState) {
        return super.onCreateView(inflater, viewContainer, savedInstanceState);
    }
}

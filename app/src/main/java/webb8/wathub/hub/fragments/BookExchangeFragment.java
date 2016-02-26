package webb8.wathub.hub.fragments;

import android.os.Bundle;
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
import webb8.wathub.models.PostTypes;
import webb8.wathub.util.PostCard;

/**
 * Created by mismayil on 23/02/16.
 */
public class BookExchangeFragment extends PostFragment {

    public BookExchangeFragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPostQuery.whereEqualTo(Post.KEY_POST_TYPE, PostTypes.BOOK_EXCHANGE.getType());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup viewContainer, Bundle savedInstanceState) {
        return super.onCreateView(inflater, viewContainer, savedInstanceState);
    }
}

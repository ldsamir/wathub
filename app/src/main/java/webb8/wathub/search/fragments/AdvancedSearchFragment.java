package webb8.wathub.search.fragments;

import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.Spinner;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

import webb8.wathub.R;
import webb8.wathub.util.NavItem;
import webb8.wathub.hub.fragments.PostFeedFragment;
import webb8.wathub.models.Post;
import webb8.wathub.util.PostCard;

/**
 * Created by mismayil on 3/24/16.
 */
public class AdvancedSearchFragment extends Fragment {

    // UI fields
    protected EditText mContentView;
    protected Button mSearchBtnView;
    protected Spinner mSearchTypeView;
    protected FrameLayout mActionSearchContainer;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View actionSearchView = inflater.inflate(R.layout.fragment_advanced_search, container, false);

        mContentView = (EditText) actionSearchView.findViewById(R.id.edit_search_content);
        mSearchTypeView = (Spinner) actionSearchView.findViewById(R.id.select_search_type);
        mSearchBtnView = (Button) actionSearchView.findViewById(R.id.btn_search);

        ArrayAdapter<CharSequence> searchTypeAdapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.search_type_list, R.layout.simple_spinner_item);
        searchTypeAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        mSearchTypeView.setAdapter(searchTypeAdapter);

        mSearchTypeView.setSelection(1);

        mSearchTypeView.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                FragmentManager fragmentManager = getFragmentManager();

                if (parent.getItemAtPosition(position).toString().equalsIgnoreCase(getString(NavItem.BOOK_EXCHANGE_POSTS.getNameId()))) {
                    fragmentManager.beginTransaction()
                            .replace(R.id.search_fragment_container, new AdvancedSearchBookExchangeFragment())
                            .commit();
                } else if (parent.getItemAtPosition(position).toString().equalsIgnoreCase(getString(NavItem.GROUP_STUDY_POSTS.getNameId()))) {
                    fragmentManager.beginTransaction()
                            .replace(R.id.search_fragment_container, new AdvancedSearchGroupStudyFragment())
                            .commit();
                } else if (parent.getItemAtPosition(position).toString().equalsIgnoreCase(getString(NavItem.CARPOOL_POSTS.getNameId()))) {
                    fragmentManager.beginTransaction()
                            .replace(R.id.search_fragment_container, new AdvancedSearchCarpoolFragment())
                            .commit();
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        mSearchBtnView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String content = mContentView.getText().toString();
                ParseQuery<ParseObject> postQuery = Post.getQuery();
                postQuery.whereContains(Post.KEY_CONTENT, content);
                postQuery.findInBackground(new FindCallback<ParseObject>() {
                    @Override
                    public void done(List<ParseObject> objects, ParseException e) {
                        List<PostCard> postCards = new ArrayList<>();

                        for (ParseObject object : objects) {
                            Post post = Post.getInstance(object);
                            postCards.add(new PostCard(getActivity(), post));
                        }

                        PostFeedFragment postFeedFragment = PostFeedFragment.newInstance(postCards);

                        FragmentManager fragmentManager = getFragmentManager();

                        fragmentManager.beginTransaction()
                                .replace(R.id.search_fragment_container, postFeedFragment)
                                .commit();

                    }
                });

            }
        });

        return actionSearchView;
    }
}

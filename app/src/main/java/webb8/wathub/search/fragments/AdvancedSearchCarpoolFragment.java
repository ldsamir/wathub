package webb8.wathub.search.fragments;

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
import android.widget.RelativeLayout;
import android.widget.Spinner;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import webb8.wathub.R;
import webb8.wathub.hub.fragments.PostFeedFragment;
import webb8.wathub.models.Carpool;
import webb8.wathub.models.Post;
import webb8.wathub.util.NavItem;
import webb8.wathub.util.PostCard;

/**
 * Created by mismayil on 3/24/16.
 */
public class AdvancedSearchCarpoolFragment extends AdvancedSearchFragment {

    // UI fields
    private EditText mCarpoolFromView;
    private EditText mCarpoolToView;
    private EditText mCarpoolWhenView;
    private EditText mCarpoolMinPassengerView;
    private EditText mCarpoolMaxPassengerView;
    private EditText mCarpoolMinPriceView;
    private EditText mCarpoolMaxPriceView;
    private RelativeLayout mProgressBar;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View actionSearchView = inflater.inflate(R.layout.fragment_advanced_search, container, false);
        final View actionSearchCarpoolView = inflater.inflate(R.layout.fragment_advanced_search_carpool, container, false);
        mActionSearchContainer = (FrameLayout) actionSearchView.findViewById(R.id.advanced_search_container);

        mActionSearchContainer.addView(actionSearchCarpoolView);
        mProgressBar = (RelativeLayout) actionSearchView.findViewById(R.id.progress_bar);

        mContentView = (EditText) actionSearchView.findViewById(R.id.edit_search_content);
        mSearchTypeView = (Spinner) actionSearchView.findViewById(R.id.select_search_type);
        mSearchBtnView = (Button) actionSearchView.findViewById(R.id.btn_search);

        mCarpoolFromView = (EditText) actionSearchCarpoolView.findViewById(R.id.edit_search_carpool_from);
        mCarpoolToView = (EditText) actionSearchCarpoolView.findViewById(R.id.edit_search_carpool_to);
        mCarpoolWhenView = (EditText) actionSearchCarpoolView.findViewById(R.id.edit_search_carpool_when);
        mCarpoolMinPassengerView = (EditText) actionSearchCarpoolView.findViewById(R.id.edit_search_carpool_min_passenger);
        mCarpoolMaxPassengerView = (EditText) actionSearchCarpoolView.findViewById(R.id.edit_search_carpool_max_passenger);
        mCarpoolMinPriceView = (EditText) actionSearchCarpoolView.findViewById(R.id.edit_search_carpool_min_price);
        mCarpoolMaxPriceView = (EditText) actionSearchCarpoolView.findViewById(R.id.edit_search_carpool_max_price);

        ArrayAdapter<CharSequence> searchTypeAdapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.search_type_list, R.layout.simple_spinner_item);
        searchTypeAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        mSearchTypeView.setAdapter(searchTypeAdapter);

        mSearchTypeView.setSelection(4);

        mSearchTypeView.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                FragmentManager fragmentManager = getFragmentManager();

                if (parent.getItemAtPosition(position).toString().equalsIgnoreCase(getString(NavItem.ALL_POSTS.getNameId()))) {
                    fragmentManager.beginTransaction()
                            .replace(R.id.search_fragment_container, new AdvancedSearchFragment())
                            .commit();
                } else if (parent.getItemAtPosition(position).toString().equalsIgnoreCase(getString(NavItem.GROUP_STUDY_POSTS.getNameId()))) {
                    fragmentManager.beginTransaction()
                            .replace(R.id.search_fragment_container, new AdvancedSearchGroupStudyFragment())
                            .commit();
                } else if (parent.getItemAtPosition(position).toString().equalsIgnoreCase(getString(NavItem.BOOK_EXCHANGE_POSTS.getNameId()))) {
                    fragmentManager.beginTransaction()
                            .replace(R.id.search_fragment_container, new AdvancedSearchBookExchangeFragment())
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
                // Should start while clicking the search button:
                mProgressBar.setVisibility(View.VISIBLE);

                // Input data and verification of the inputs:
                String From = mCarpoolFromView.getText().toString();
                Boolean CheckFrom = !(From.equals(""));
                String To = mCarpoolToView.getText().toString();
                Boolean CheckTo = !(To.equals(""));
                // Reading date and time information in specific format:
                SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.CANADA);
                Calendar When = Calendar.getInstance();
                String WhenStr = mCarpoolWhenView.getText().toString();
                Boolean CheckWhen = !(WhenStr.equals(""));
                if (CheckWhen) {
                    try {
                        When.setTime(format.parse(WhenStr));
                    } catch (java.text.ParseException e) {
                        // Currently, we do not have anything to do here;
                        // I hope, we will never catch any exception here...
                    }
                }
                String MinPassenger = mCarpoolMinPassengerView.getText().toString();
                // For the case below we are going to ignore MinPassenger in the search:
                Boolean CheckMinPass = !(MinPassenger.equals("") || Integer.parseInt(MinPassenger) < 0);
                String MaxPassenger = mCarpoolMaxPassengerView.getText().toString();
                // For the case below we are going to ignore MaxPassenger in the search:
                Boolean CheckMaxPass = !(MaxPassenger.equals("") || Integer.parseInt(MaxPassenger) < 0);
                String MinPrice = mCarpoolMinPriceView.getText().toString();
                // For the case below we are going to ignore MinPrice in the search:
                Boolean CheckMinPrice = !(MinPrice.equals("") || Integer.parseInt(MinPrice) < 0);
                String MaxPrice = mCarpoolMaxPriceView.getText().toString();
                // For the case below we are going to ignore MaxPrice in the search:
                Boolean CheckMaxPrice = !(MaxPrice.equals("") || Integer.parseInt(MaxPrice) < 0);

                // Searching through Carpool posts
                // by taking each input into consideration:
                ParseQuery<ParseObject> CarpoolPosts = ParseQuery.getQuery("Carpool");
                final Collection<String> CarpoolPostPointers = new ArrayList<String>();
                if (CheckFrom) CarpoolPosts.whereContains("from", From);
                if (CheckTo) CarpoolPosts.whereContains("to", To);
                if (CheckWhen) CarpoolPosts.whereEqualTo("when", When.getTime());
                if (CheckMinPass) CarpoolPosts.whereGreaterThanOrEqualTo("maxPassengers", Integer.parseInt(MinPassenger));
                if (CheckMaxPass) CarpoolPosts.whereLessThanOrEqualTo("maxPassengers", Integer.parseInt(MaxPassenger));
                if (CheckMinPrice) CarpoolPosts.whereGreaterThanOrEqualTo("price", Integer.parseInt(MinPrice));
                if (CheckMaxPrice) CarpoolPosts.whereLessThanOrEqualTo("price", Integer.parseInt(MaxPrice));
                // Getting Post IDs of the found Carpool Posts:
                CarpoolPosts.findInBackground(new FindCallback<ParseObject>() {
                    @Override
                    public void done(List<ParseObject> objects, ParseException e) {
                        if (e == null) {
                            for (ParseObject object : objects) {
                                CarpoolPostPointers.add(object.get("post").toString());
                            }
                        }
                        // We do not need else case...
                    }
                });

                // Main search:
                ParseQuery<ParseObject> postQuery = ParseQuery.getQuery("Post");
                postQuery.whereContainsAll("objectId", CarpoolPostPointers);

                // Post-search-process:
                postQuery.findInBackground(new FindCallback<ParseObject>() {
                    @Override
                    public void done(List<ParseObject> objects, ParseException e) {
                        List<PostCard> postCards = new ArrayList<>();

                        for (ParseObject object : objects) {
                            Post post = Post.getInstance(object);
                            postCards.add(new PostCard(getActivity(), post));
                        }

                        PostFeedFragment postFeedFragment = PostFeedFragment.newInstance(postCards);
                        mProgressBar.setVisibility(View.GONE);

                        FragmentManager fragmentManager = getFragmentManager();

                        fragmentManager.beginTransaction()
                                .replace(R.id.search_container, postFeedFragment)
                                .commit();

                    }
                });

            }
        });

        return actionSearchView;
    }
}

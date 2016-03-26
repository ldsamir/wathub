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
import android.widget.Spinner;

import webb8.wathub.R;
import webb8.wathub.util.NavItem;

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

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View actionSearchView = inflater.inflate(R.layout.fragment_advanced_search, container, false);
        final View actionSearchCarpoolView = inflater.inflate(R.layout.fragment_advanced_search_carpool, container, false);
        mActionSearchContainer = (FrameLayout) actionSearchView.findViewById(R.id.advanced_search_container);

        mActionSearchContainer.addView(actionSearchCarpoolView);

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
            }
        });

        return actionSearchView;
    }
}

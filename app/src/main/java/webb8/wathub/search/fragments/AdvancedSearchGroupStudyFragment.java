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
import webb8.wathub.hub.NavItem;
import webb8.wathub.util.Util;

/**
 * Created by mismayil on 3/24/16.
 */
public class AdvancedSearchGroupStudyFragment extends AdvancedSearchFragment {

    // UI fields
    private EditText mGroupNameView;
    private EditText mGroupWhereView;
    private EditText mGroupWhenView;
    private EditText mGroupStartTimeView;
    private EditText mGroupEndTimeView;
    private Spinner mGroupCourseSubjectView;
    private Spinner mGroupCourseNumberView;
    private EditText mGroupMinPeopleView;
    private EditText mGroupMaxPeopleView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View actionSearchView = inflater.inflate(R.layout.fragment_advanced_search, container, false);
        final View actionSearchGroupStudyView = inflater.inflate(R.layout.fragment_advanced_search_groupstudy, container, false);
        mActionSearchContainer = (FrameLayout) actionSearchView.findViewById(R.id.advanced_search_container);

        mActionSearchContainer.addView(actionSearchGroupStudyView);

        mContentView = (EditText) actionSearchView.findViewById(R.id.edit_search_content);
        mSearchTypeView = (Spinner) actionSearchView.findViewById(R.id.select_search_type);
        mSearchBtnView = (Button) actionSearchView.findViewById(R.id.btn_search);

        mGroupNameView = (EditText) actionSearchGroupStudyView.findViewById(R.id.edit_search_group_name);
        mGroupWhereView = (EditText) actionSearchGroupStudyView.findViewById(R.id.edit_search_group_where);
        mGroupWhenView = (EditText) actionSearchGroupStudyView.findViewById(R.id.edit_search_group_when);
        mGroupStartTimeView = (EditText) actionSearchGroupStudyView.findViewById(R.id.edit_search_group_start_time);
        mGroupEndTimeView = (EditText) actionSearchGroupStudyView.findViewById(R.id.edit_search_group_end_time);
        mGroupCourseSubjectView = (Spinner) actionSearchGroupStudyView.findViewById(R.id.select_search_group_course_subject);
        mGroupCourseNumberView = (Spinner) actionSearchGroupStudyView.findViewById(R.id.select_search_group_course_number);
        mGroupMinPeopleView = (EditText) actionSearchGroupStudyView.findViewById(R.id.edit_search_group_min_people);
        mGroupMaxPeopleView = (EditText) actionSearchGroupStudyView.findViewById(R.id.edit_search_group_max_people);

        ArrayAdapter<CharSequence> searchTypeAdapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.search_type_list, R.layout.simple_spinner_item);
        searchTypeAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        mSearchTypeView.setAdapter(searchTypeAdapter);

        ArrayAdapter<CharSequence> courseSubjectAdapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.search_course_subject_list, R.layout.simple_spinner_item);
        courseSubjectAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);

        ArrayAdapter<CharSequence> courseNumberAdapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.search_course_number_list, R.layout.simple_spinner_item);
        courseNumberAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);

        Util.updateCourseSubjectsAdapter(getActivity(), mGroupCourseSubjectView);

        mSearchTypeView.setSelection(3);

        mSearchTypeView.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                FragmentManager fragmentManager = getFragmentManager();

                if (parent.getItemAtPosition(position).toString().equalsIgnoreCase(getString(NavItem.ALL_POSTS.getNameId()))) {
                    fragmentManager.beginTransaction()
                            .replace(R.id.search_fragment_container, new AdvancedSearchFragment())
                            .commit();
                } else if (parent.getItemAtPosition(position).toString().equalsIgnoreCase(getString(NavItem.BOOK_EXCHANGE_POSTS.getNameId()))) {
                    fragmentManager.beginTransaction()
                            .replace(R.id.search_fragment_container, new AdvancedSearchBookExchangeFragment())
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

        mGroupCourseSubjectView.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position != 0) {
                    Util.updateCourseNumbersAdapter(getActivity(), mGroupCourseNumberView, parent.getItemAtPosition(position).toString());
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

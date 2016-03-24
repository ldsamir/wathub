package webb8.wathub.hub.fragments.actions;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.Spinner;

import webb8.wathub.R;
import webb8.wathub.hub.fragments.actions.ActionSearchFragment;

/**
 * Created by mismayil on 3/23/16.
 */
public class ActionSearchGroupStudyFragment extends ActionSearchFragment {

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

    public ActionSearchGroupStudyFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View actionSearchView = super.onCreateView(inflater, container, savedInstanceState);
        final View actionSearchGroupStudyView = inflater.inflate(R.layout.fragment_action_search_groupstudy, container, false);
        mActionSearchContainer = (FrameLayout) actionSearchView.findViewById(R.id.action_search_container);

        mActionSearchContainer.addView(actionSearchGroupStudyView);

        mGroupNameView = (EditText) actionSearchGroupStudyView.findViewById(R.id.edit_search_group_name);
        mGroupWhereView = (EditText) actionSearchGroupStudyView.findViewById(R.id.edit_search_group_where);
        mGroupWhenView = (EditText) actionSearchGroupStudyView.findViewById(R.id.edit_search_group_when);
        mGroupStartTimeView = (EditText) actionSearchGroupStudyView.findViewById(R.id.edit_search_group_start_time);
        mGroupEndTimeView = (EditText) actionSearchGroupStudyView.findViewById(R.id.edit_search_group_end_time);
        mGroupCourseSubjectView = (Spinner) actionSearchGroupStudyView.findViewById(R.id.select_search_course_subject);
        mGroupCourseNumberView = (Spinner) actionSearchGroupStudyView.findViewById(R.id.select_search_course_number);
        mGroupMinPeopleView = (EditText) actionSearchGroupStudyView.findViewById(R.id.edit_search_group_min_people);
        mGroupMaxPeopleView = (EditText) actionSearchGroupStudyView.findViewById(R.id.edit_search_group_max_people);

        ArrayAdapter<CharSequence> courseSubjectAdapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.search_course_subject_list, R.layout.simple_spinner_item);
        courseSubjectAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);

        ArrayAdapter<CharSequence> courseNumberAdapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.search_course_number_list, R.layout.simple_spinner_item);
        courseNumberAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);

        updateCourseSubjectsAdapter(mGroupCourseSubjectView);

        mGroupCourseSubjectView.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position != 0) {
                    updateCourseNumbersAdapter(mGroupCourseNumberView, parent.getItemAtPosition(position).toString());
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        return actionSearchView;
    }
}

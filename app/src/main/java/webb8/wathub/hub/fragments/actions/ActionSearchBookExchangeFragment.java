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

/**
 * Created by mismayil on 3/23/16.
 */
public class ActionSearchBookExchangeFragment extends ActionSearchFragment {

    // UI fields
    private EditText mBookTitleView;
    private Spinner mBookCourseSubjectView;
    private Spinner mBookCourseNumberView;
    private EditText mBookMinPriceView;
    private EditText mBookMaxPriceView;
    private Spinner mBookConditionView;

    public ActionSearchBookExchangeFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View actionSearchView = super.onCreateView(inflater, container, savedInstanceState);
        final View actionSearchBookExchangeView = inflater.inflate(R.layout.fragment_action_search_bookexchange, container, false);
        mActionSearchContainer = (FrameLayout) actionSearchView.findViewById(R.id.action_search_container);

        mActionSearchContainer.addView(actionSearchBookExchangeView);

        mBookTitleView = (EditText) actionSearchBookExchangeView.findViewById(R.id.edit_search_book_title);
        mBookCourseSubjectView = (Spinner) actionSearchBookExchangeView.findViewById(R.id.select_search_course_subject);
        mBookCourseNumberView = (Spinner) actionSearchBookExchangeView.findViewById(R.id.select_search_course_number);
        mBookMinPriceView = (EditText) actionSearchBookExchangeView.findViewById(R.id.edit_search_min_price);
        mBookMaxPriceView = (EditText) actionSearchBookExchangeView.findViewById(R.id.edit_search_max_price);
        mBookConditionView = (Spinner) actionSearchBookExchangeView.findViewById(R.id.select_search_book_condition);

        ArrayAdapter<CharSequence> courseSubjectAdapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.search_course_subject_list, R.layout.simple_spinner_item);
        courseSubjectAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);

        ArrayAdapter<CharSequence> courseNumberAdapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.search_course_number_list, R.layout.simple_spinner_item);
        courseNumberAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);

        updateCourseSubjectsAdapter(mBookCourseSubjectView);

        mBookCourseSubjectView.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position != 0) {
                    updateCourseNumbersAdapter(mBookCourseNumberView, parent.getItemAtPosition(position).toString());
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        ArrayAdapter<CharSequence> conditionAdapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.book_conditions, R.layout.simple_spinner_item);
        conditionAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        mBookConditionView.setAdapter(conditionAdapter);

        return actionSearchView;
    }
}

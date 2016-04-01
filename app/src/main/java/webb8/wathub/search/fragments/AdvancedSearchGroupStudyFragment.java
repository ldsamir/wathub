package webb8.wathub.search.fragments;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.FragmentManager;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.List;
import java.util.Locale;

import webb8.wathub.R;
import webb8.wathub.hub.fragments.PostFeedFragment;
import webb8.wathub.models.Course;
import webb8.wathub.models.GroupStudy;
import webb8.wathub.models.Post;
import webb8.wathub.util.NavItem;
import webb8.wathub.util.PostCard;
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
    private RelativeLayout mProgressBar;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View actionSearchView = inflater.inflate(R.layout.fragment_advanced_search, container, false);
        final View actionSearchGroupStudyView = inflater.inflate(R.layout.fragment_advanced_search_groupstudy, container, false);
        mActionSearchContainer = (FrameLayout) actionSearchView.findViewById(R.id.advanced_search_container);

        mActionSearchContainer.addView(actionSearchGroupStudyView);
        mProgressBar = (RelativeLayout) actionSearchView.findViewById(R.id.progress_bar);

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


        mGroupWhenView.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean arg) {
                if (arg) {
                    showDatePickerDialog(v);
                }
            }
        });


        mGroupWhenView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog(v);
            }
        });

        mGroupStartTimeView.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean arg) {
                if (arg) {
                    showTimePickerDialog(v, 0);
                }
            }
        });

        mGroupStartTimeView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTimePickerDialog(v, 0);
            }
        });

        mGroupEndTimeView.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean arg) {
                if (arg) {
                    showTimePickerDialog(v, 1);
                }
            }
        });

        mGroupEndTimeView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTimePickerDialog(v, 1);
            }
        });

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
                // Should start while clicking the search button:
                mProgressBar.setVisibility(View.VISIBLE);
                final String selectStr = "select", emptyStr = "";

                // Input data and verification of the inputs:
                final String name = mGroupNameView.getText().toString();
                final Boolean checkName = !(name.equals(emptyStr));
                final String where = mGroupWhereView.getText().toString();
                final Boolean checkWhere = !(where.equals(emptyStr));
                final String whenStr = mGroupWhenView.getText().toString();
                Boolean checkWhenBuf = !(whenStr.equals(emptyStr));
                final String startTimeStr = mGroupStartTimeView.getText().toString();
                Boolean checkStartBuf = !(startTimeStr.equals(emptyStr));
                final String endTimeStr = mGroupEndTimeView.getText().toString();
                Boolean checkEndBuf = !(endTimeStr.equals(emptyStr));
                // Reading date and time information in specific format:
                SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.CANADA);
                final Calendar startTime = Calendar.getInstance();
                final Calendar endTime = Calendar.getInstance();
                final Calendar today = Calendar.getInstance();
                // Need to if at least one of them was given as input:
                Boolean checkDateBuf = checkWhenBuf || checkStartBuf || checkEndBuf;
                if (checkDateBuf) {
                    if (!checkWhenBuf) {
                        if (checkStartBuf) {
                            try {
                                startTime.setTime(format.parse(today.getTime().toString() + " " + startTimeStr));
                            } catch (java.text.ParseException e) {}
                        }
                        if (checkEndBuf) {
                            try {
                                endTime.setTime(format.parse(today.getTime().toString() + " " + endTimeStr));
                            } catch (java.text.ParseException e) {}
                        }
                        checkWhenBuf = true;
                    } else {
                        if (checkStartBuf) {
                            try {
                                startTime.setTime(format.parse(whenStr + " " + startTimeStr));
                            } catch (java.text.ParseException e) {}
                        } else {
                            try {
                                startTime.setTime(format.parse(whenStr + " 00:00"));
                            } catch (java.text.ParseException e) {}
                            checkStartBuf = true;
                        }
                        if (checkEndBuf) {
                            try {
                                endTime.setTime(format.parse(whenStr + " " + endTimeStr));
                            } catch (java.text.ParseException e) {}
                        } else {
                            try {
                                endTime.setTime(format.parse(whenStr + " 23:59"));
                            } catch (java.text.ParseException e) {}
                            checkEndBuf = true;
                        }
                    }
                }
                final Boolean checkWhen = checkWhenBuf;
                final Boolean checkStart = checkStartBuf;
                final Boolean checkEnd = checkEndBuf;
                final String courseSubject = mGroupCourseSubjectView.getSelectedItem().toString();
                final Boolean checkCourseSubject = !(courseSubject.toLowerCase().contains(selectStr));
                String courseNumberBuf = emptyStr;
                Boolean checkCourseNumberBuf = false;
                if (checkCourseSubject) {
                    while (mGroupCourseNumberView.getSelectedItem() == null) continue;
                    courseNumberBuf += mGroupCourseNumberView.getSelectedItem().toString();
                    checkCourseNumberBuf = checkCourseNumberBuf || !(courseNumberBuf.toLowerCase().contains(selectStr));
                }
                final String courseNumber = courseNumberBuf;
                final Boolean checkCourseNumber = checkCourseNumberBuf;
                final String minPeople = mGroupMinPeopleView.getText().toString();
                // For the case below we are going to ignore MinPeople in the search:
                final Boolean checkMinPeople = !(minPeople.equals(emptyStr));
                final String maxPeople = mGroupMaxPeopleView.getText().toString();
                // For the case below we are going to ignore MaxPeople in the search:
                final Boolean checkMaxPeople = !(maxPeople.equals(emptyStr));

                /* First, we have to search through the courses
                 * to obtain the course IDs for being able to use them
                 * in the main search:
                 */
                ParseQuery<ParseObject> courseQuery = Course.getQuery();
                final Collection<Course> courses = new ArrayList<Course>();
                if (checkCourseSubject) {
                    courseQuery.whereEqualTo(Course.KEY_SUBJECT, courseSubject);
                    // Since we can select the number after selecting the subject:
                    if (checkCourseNumber)
                        courseQuery.whereEqualTo(Course.KEY_NUMBER, courseNumber);
                    courseQuery.findInBackground(new FindCallback<ParseObject>() {
                        public void done(List<ParseObject> objects, ParseException e) {
                            if (e == null) {
                                for (ParseObject object : objects) {
                                    courses.add(Course.getInstance(object));
                                }
                                // Searching through GroupStudy posts
                                // by taking each input into consideration:
                                ParseQuery<ParseObject> groupStudyPostQuery = GroupStudy.getQuery();
                                if (checkName)
                                    groupStudyPostQuery.whereMatches(GroupStudy.KEY_GROUP_NAME, name, "i");
                                groupStudyPostQuery.whereContainedIn(GroupStudy.KEY_COURSE, courses);
                                if (checkWhen || checkStart)
                                    groupStudyPostQuery.whereGreaterThanOrEqualTo(GroupStudy.KEY_START_TIME, startTime.getTime());
                                System.out.println(startTime.getTime());
                                System.out.println(endTime.getTime());
                                if (checkWhen || checkEnd)
                                    groupStudyPostQuery.whereLessThanOrEqualTo(GroupStudy.KEY_END_TIME, endTime.getTime());
                                if (checkWhere)
                                    groupStudyPostQuery.whereMatches(GroupStudy.KEY_WHERE, where, "i");
                                if (checkMinPeople)
                                    groupStudyPostQuery.whereGreaterThanOrEqualTo(GroupStudy.KEY_MAX_PEOPLE, Integer.parseInt(minPeople));
                                if (checkMaxPeople)
                                    groupStudyPostQuery.whereLessThanOrEqualTo(GroupStudy.KEY_MAX_PEOPLE, Integer.parseInt(maxPeople));
                                // Getting Post IDs of the found Group Study Posts:
                                groupStudyPostQuery.findInBackground(new FindCallback<ParseObject>() {
                                    @Override
                                    public void done(List<ParseObject> objects, ParseException e) {
                                        if (objects.size() == 0) {
                                            mProgressBar.setVisibility(View.GONE);
                                            Toast.makeText(actionSearchView.getContext(), "No result found", Toast.LENGTH_SHORT).show();
                                        }
                                        else if (e == null) {
                                            List<PostCard> postCards = new ArrayList<>();
                                            for (ParseObject object : objects) {
                                                Post post = GroupStudy.getInstance(object).getPost();
                                                try {
                                                    post.fetch();
                                                } catch (ParseException e1) {
                                                    e1.printStackTrace();
                                                }
                                                postCards.add(new PostCard(getActivity(), post));
                                            }
                                            PostFeedFragment postFeedFragment = PostFeedFragment.newInstance(postCards);
                                            mProgressBar.setVisibility(View.GONE);

                                            FragmentManager fragmentManager = getFragmentManager();

                                            fragmentManager.beginTransaction()
                                                    .replace(R.id.search_fragment_container, postFeedFragment)
                                                    .commit();
                                        }
                                        // We do not need else case...
                                    }
                                });
                            }
                            // We do not need else case...
                        }
                    });
                } else {
                    // Searching through GroupStudy posts
                    // by taking each input into consideration:
                    ParseQuery<ParseObject> groupStudyPostQuery = GroupStudy.getQuery();
                    if (checkName)
                        groupStudyPostQuery.whereContains(GroupStudy.KEY_GROUP_NAME, name);
                    if (checkWhen || checkStart)
                        groupStudyPostQuery.whereGreaterThanOrEqualTo(GroupStudy.KEY_START_TIME, startTime.getTime());
                    System.out.println(startTime.getTime());
                    System.out.println(endTime.getTime());
                    if (checkWhen || checkEnd)
                        groupStudyPostQuery.whereLessThanOrEqualTo(GroupStudy.KEY_END_TIME, endTime.getTime());
                    if (checkWhere) groupStudyPostQuery.whereContains(GroupStudy.KEY_WHERE, where);
                    if (checkMinPeople)
                        groupStudyPostQuery.whereGreaterThanOrEqualTo(GroupStudy.KEY_MAX_PEOPLE, Integer.parseInt(minPeople));
                    if (checkMaxPeople)
                        groupStudyPostQuery.whereLessThanOrEqualTo(GroupStudy.KEY_MAX_PEOPLE, Integer.parseInt(maxPeople));
                    // Getting Post IDs of the found Group Study Posts:
                    groupStudyPostQuery.findInBackground(new FindCallback<ParseObject>() {
                        @Override
                        public void done(List<ParseObject> objects, ParseException e) {
                            if (objects.size() == 0) {
                                mProgressBar.setVisibility(View.GONE);
                                Toast.makeText(actionSearchView.getContext(), "No result found", Toast.LENGTH_SHORT).show();
                            }
                            else if (e == null) {
                                List<PostCard> postCards = new ArrayList<>();
                                for (ParseObject object : objects) {
                                    Post post = GroupStudy.getInstance(object).getPost();
                                    try {
                                        post.fetch();
                                    } catch (ParseException e1) {
                                        e1.printStackTrace();
                                    }
                                    postCards.add(new PostCard(getActivity(), post));
                                }
                                PostFeedFragment postFeedFragment = PostFeedFragment.newInstance(postCards);
                                mProgressBar.setVisibility(View.GONE);

                                FragmentManager fragmentManager = getFragmentManager();

                                fragmentManager.beginTransaction()
                                        .replace(R.id.search_fragment_container, postFeedFragment)
                                        .commit();
                            }
                            // We do not need else case...
                        }
                    });
                }
            }
        });

        return actionSearchView;
    }

    public static class DatePickerFragment extends DialogFragment
            implements DatePickerDialog.OnDateSetListener {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current date as the default date in the picker
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);

            // Create a new instance of DatePickerDialog and return it
            return new DatePickerDialog(getActivity(), this, year, month, day);
        }

        public void onDateSet(DatePicker view, int year, int month, int day) {
            EditText mDateView = (EditText) getActivity().findViewById(R.id.edit_search_group_when);
            mDateView.setText(new StringBuilder().append(day).append("/")
                    .append(month+1).append("/").append(year));
        }
    }

    public void showDatePickerDialog(View v) {
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getActivity().getFragmentManager(), "datePicker");
    }


    public static class TimePickerFragment extends DialogFragment
            implements TimePickerDialog.OnTimeSetListener {
        Bundle bundle = this.getArguments();
        private int start_or_end = 0;


        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current time as the default values for the picker
            final Calendar c = Calendar.getInstance();
            int hour = c.get(Calendar.HOUR_OF_DAY);
            int minute = c.get(Calendar.MINUTE);
            Bundle bundle = this.getArguments();
            start_or_end = bundle.getInt("st_or_nd");
            // Create a new instance of TimePickerDialog and return it
            return new TimePickerDialog(getActivity(), this, hour, minute,
                    DateFormat.is24HourFormat(getActivity()));
        }

        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            if(start_or_end == 0){
                EditText mStartTimeView = (EditText) getActivity().findViewById(R.id.edit_search_group_start_time);
                mStartTimeView.setText(new StringBuilder().append(hourOfDay < 10 ? "0" : "").append(hourOfDay).append(":")
                        .append(minute < 10 ? "0" : "").append(minute));
            }
            else if(start_or_end == 1){
                EditText mEndTimeView = (EditText) getActivity().findViewById(R.id.edit_search_group_end_time);
                mEndTimeView.setText(new StringBuilder().append(hourOfDay < 10 ? "0" : "").append(hourOfDay).append(":")
                        .append(minute < 10 ? "0" : "").append(minute));
            }
        }
    }

    public void showTimePickerDialog(View v, int st_or_nd) {
        DialogFragment newFragment = new TimePickerFragment();
        Bundle bundle = new Bundle();
        System.out.println("This is st_or_nd " + st_or_nd);
        bundle.putInt("st_or_nd", st_or_nd);
        System.out.println(bundle.getInt("st_or_nd"));
        newFragment.setArguments(bundle);
        newFragment.show(getActivity().getFragmentManager(), "timePicker");
    }


}

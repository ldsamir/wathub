package webb8.wathub.hub.fragments.actions;


import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.FragmentManager;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.text.TextUtils;
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
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import webb8.wathub.R;
import webb8.wathub.util.NavItem;
import webb8.wathub.hub.fragments.HubFragment;
import webb8.wathub.models.Course;
import webb8.wathub.models.GroupStudy;
import webb8.wathub.models.Post;
import webb8.wathub.models.PostTypes;
import webb8.wathub.util.Util;

/**
 * Created by mismayil on 25/02/16.
 */

/**
 * This fragment handles group study posts
 */
public class ActionPostGroupStudyFragment extends ActionPostFragment {

    public ActionPostGroupStudyFragment() {}

    // UI fields
    protected EditText mGroupNameView;
    protected Spinner mCourseSubjectView;
    protected Spinner mCourseNumberView;
    protected TextView mCourseTitleView;
    protected EditText mWhenView;
    protected EditText mStartTimeView;
    protected EditText mEndTimeView;
    protected EditText mWhereView;
    protected EditText mNumberPeopleView;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View actionPostView = inflater.inflate(R.layout.fragment_action_post, container, false);
        FrameLayout actionPostContainer = (FrameLayout) actionPostView.findViewById(R.id.post_action_container);
        View groupStudySectionView = inflater.inflate(R.layout.fragment_action_groupstudy, container, false);

        actionPostContainer.addView(groupStudySectionView);

        mContentView = (EditText) actionPostView.findViewById(R.id.edit_post_content);
        mPostBtnView = (Button) actionPostView.findViewById(R.id.action_post_go);
        mProgressBar = (RelativeLayout) actionPostView.findViewById(R.id.progress_bar);
        mGroupNameView = (EditText) groupStudySectionView.findViewById(R.id.edit_group_name);
        mCourseSubjectView = (Spinner) groupStudySectionView.findViewById(R.id.select_group_course_subject);
        mCourseNumberView = (Spinner) groupStudySectionView.findViewById(R.id.select_group_course_number);
        mCourseTitleView = (TextView) groupStudySectionView.findViewById(R.id.group_course_title);
        mWhenView = (EditText) groupStudySectionView.findViewById(R.id.edit_group_start_when);
        mStartTimeView = (EditText) groupStudySectionView.findViewById(R.id.edit_group_start_time);
        mEndTimeView = (EditText) groupStudySectionView.findViewById(R.id.edit_group_end_time);
        mWhereView = (EditText) groupStudySectionView.findViewById(R.id.edit_group_where);
        mNumberPeopleView = (EditText) groupStudySectionView.findViewById(R.id.edit_group_max_people);

        final Post post = new Post();
        final GroupStudy groupStudyPost = new GroupStudy();

        mWhenView.setOnFocusChangeListener(new View.OnFocusChangeListener(){
            @Override
            public void onFocusChange(View v, boolean arg){
                if(arg) {
                    showDatePickerDialog(v);
                }
            }
        });

        mStartTimeView.setOnFocusChangeListener(new View.OnFocusChangeListener(){
            @Override
            public void onFocusChange(View v, boolean arg){
                if(arg) {
                    showTimePickerDialog(v, 0);
                }
            }
        });

        mEndTimeView.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean arg) {
                if (arg) {
                    showTimePickerDialog(v, 1);
                }
            }
        });

        ArrayAdapter<CharSequence> courseSubjectAdapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.book_course_subject_list, R.layout.simple_spinner_item);
        courseSubjectAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        mCourseSubjectView.setAdapter(courseSubjectAdapter);

        ArrayAdapter<CharSequence> courseNumberAdapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.book_course_number_list, R.layout.simple_spinner_item);
        courseNumberAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        mCourseNumberView.setAdapter(courseNumberAdapter);

        Util.updateCourseSubjectsAdapter(getActivity(), mCourseSubjectView);

        mCourseSubjectView.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position != 0) {
                    String subject = parent.getItemAtPosition(position).toString();
                    Util.updateCourseNumbersAdapter(getActivity(), mCourseNumberView, subject);
                } else {
                    mCourseTitleView.setVisibility(View.GONE);
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        mCourseNumberView.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position != 0) {
                    String number = parent.getItemAtPosition(position).toString();
                    ParseQuery<ParseObject> query = Course.getQuery();
                    query.whereEqualTo(Course.KEY_SUBJECT, mCourseSubjectView.getSelectedItem().toString());
                    query.whereEqualTo(Course.KEY_NUMBER, number);
                    query.findInBackground(new FindCallback<ParseObject>() {
                        @Override
                        public void done(List<ParseObject> objects, ParseException e) {
                            if (objects != null && objects.size() > 0) {
                                Course selectedCourse = Course.getInstance(objects.get(0));
                                groupStudyPost.setCourse(selectedCourse);
                                mCourseTitleView.setText(selectedCourse.getTitle());
                                mCourseTitleView.setVisibility(View.VISIBLE);
                            }
                        }
                    });
                } else {
                    mCourseTitleView.setVisibility(View.GONE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        mPostBtnView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkInput()) {
                    mProgressBar.setVisibility(View.VISIBLE);
                    post.setContent(mContentView.getText().toString());
                    post.setUser(ParseUser.getCurrentUser());
                    post.setPostType(PostTypes.GROUP_STUDY.getType());
                    groupStudyPost.setPost(post);
                    SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.CANADA);
                    Calendar startTime = Calendar.getInstance();
                    Calendar endTime = Calendar.getInstance();

                    try {
                        startTime.setTime(format.parse(mWhenView.getText().toString() + " " + mStartTimeView.getText().toString()));
                        endTime.setTime(format.parse(mWhenView.getText().toString() + " " + mEndTimeView.getText().toString()));
                    } catch (java.text.ParseException e) {

                    }

                    groupStudyPost.setStartTime(startTime.getTime());
                    groupStudyPost.setEndTime(endTime.getTime());
                    groupStudyPost.setWhere(mWhereView.getText().toString());
                    groupStudyPost.setGroupName(mGroupNameView.getText().toString());
                    groupStudyPost.setMaxPeople(Integer.parseInt(mNumberPeopleView.getText().toString()));

                    post.saveInBackground(new SaveCallback() {
                        @Override
                        public void done(ParseException e) {
                            if (e == null) {
                                mProgressBar.setVisibility(View.GONE);
                                FragmentManager fragmentManager = getFragmentManager();
                                Toast.makeText(getActivity(), R.string.info_post_published, Toast.LENGTH_SHORT).show();
                                fragmentManager.beginTransaction().replace(R.id.container, HubFragment.newInstance(NavItem.ALL_POSTS.getId())).commit();
                            } else {
                                Toast.makeText(getActivity(), R.string.error_publishing_post, Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                    groupStudyPost.saveInBackground();
                }
            }
        });

        return actionPostView;
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
            EditText mDateView = (EditText) mHubActivity.findViewById(R.id.edit_group_start_when);
            mDateView.setText(new StringBuilder().append(day).append("/")
                    .append(month).append("/").append(year));
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
                EditText mStartTimeView = (EditText) mHubActivity.findViewById(R.id.edit_group_start_time);
                mStartTimeView.setText(new StringBuilder().append(hourOfDay < 10 ? "0" : "").append(hourOfDay).append(":")
                                                          .append(minute < 10 ? "0" : "").append(minute));
            }
            else if(start_or_end == 1){
                EditText mEndTimeView = (EditText) mHubActivity.findViewById(R.id.edit_group_end_time);
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


    private boolean checkInput() {
        mContentView.setError(null);
        mGroupNameView.setError(null);
        mNumberPeopleView.setError(null);
        mWhereView.setError(null);
        mStartTimeView.setError(null);
        mEndTimeView.setError(null);
        mWhenView.setError(null);

        if (TextUtils.isEmpty(mContentView.getText().toString())) {
            mContentView.setError(getString(R.string.error_post_empty_content));
            return false;
        }

        if (TextUtils.isEmpty(mGroupNameView.getText().toString())) {
            mGroupNameView.setError(getString(R.string.error_groupstudy_empty_groupname));
            return false;
        }

        if(mCourseSubjectView.getSelectedItem().toString().equals(getResources().getString(R.string.select_course_subject))){
            Toast.makeText(getActivity(),"Please select course subject", Toast.LENGTH_SHORT).show();
            return false;
        }

        if(mCourseNumberView.getSelectedItem().toString().equals(getResources().getString(R.string.select_course_number))) {
            Toast.makeText(getActivity(), "Please select course number", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (TextUtils.isEmpty(mWhereView.getText().toString())) {
            mWhereView.setError(getString(R.string.error_groupstudy_empty_where));
            return false;
        }

        if(TextUtils.isEmpty(mWhenView.getText().toString())){
            mWhenView.setError(getString(R.string.error_groupstudy_empty_when));
            return false;
        }

        if(TextUtils.isEmpty(mStartTimeView.getText().toString())){
            mStartTimeView.setError(getString(R.string.error_groupstudy_empty_starttime));
            return false;
        }

        if(TextUtils.isEmpty(mEndTimeView.getText().toString())){
            mEndTimeView.setError(getString(R.string.error_groupstudy_empty_endtime));
            return false;
        }

        if (TextUtils.isEmpty(mNumberPeopleView.getText().toString())) {
            mNumberPeopleView.setError(getString(R.string.error_groupstudy_empty_numberpeople));
            return false;
        }

        return true;
    }
}

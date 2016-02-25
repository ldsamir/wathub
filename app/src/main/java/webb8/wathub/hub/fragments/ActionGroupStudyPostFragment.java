package webb8.wathub.hub.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.Spinner;
import android.widget.TimePicker;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.security.acl.Group;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import webb8.wathub.R;
import webb8.wathub.models.BookExchange;
import webb8.wathub.models.Course;
import webb8.wathub.models.GroupStudy;
import webb8.wathub.models.Post;
import webb8.wathub.models.PostTypes;

/**
 * Created by mismayil on 25/02/16.
 */

/**
 * This fragment handles group study posts
 */
public class ActionGroupStudyPostFragment extends ActionPostFragment {

    // UI fields

    public ActionGroupStudyPostFragment() {}

    // UI fields
    protected EditText mGroupNameView;
    protected Spinner mCourseSubjectView;
    protected Spinner mCourseNumberView;
    protected EditText mCourseTitleView;
    protected DatePicker mDateView;
    protected TimePicker mStartTimeView;
    protected TimePicker mEndTimeView;
    protected EditText mLocationView;
    protected EditText mNumberPeopleView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View actionPostView = inflater.inflate(R.layout.fragment_action_post, container, false);
        FrameLayout actionPostContainer = (FrameLayout) actionPostView.findViewById(R.id.post_action_container);
        View groupStudySectionView = inflater.inflate(R.layout.fragment_action_groupstudy, container, false);

        actionPostContainer.addView(groupStudySectionView);

        mContentView = (EditText) actionPostView.findViewById(R.id.edit_post_content);
        mPostBtnView = (Button) actionPostView.findViewById(R.id.action_post_go);
        mGroupNameView = (EditText) groupStudySectionView.findViewById(R.id.edit_group_name);
        mCourseSubjectView = (Spinner) groupStudySectionView.findViewById(R.id.select_group_course_subject);
        mCourseNumberView = (Spinner) groupStudySectionView.findViewById(R.id.select_book_course_number);
        mCourseTitleView = (EditText) groupStudySectionView.findViewById(R.id.group_course_title);
        mStartTimeView = (TimePicker) groupStudySectionView.findViewById(R.id.edit_group_start_time);
        mEndTimeView = (TimePicker) groupStudySectionView.findViewById(R.id.edit_group_end_time);
        mLocationView = (EditText) groupStudySectionView.findViewById(R.id.edit_group_where);
        mNumberPeopleView = (EditText) groupStudySectionView.findViewById(R.id.edit_group_max_people);

        final Course course = new Course();
        final Post post = new Post();
        final GroupStudy GroupStudyPost = new GroupStudy();

        ArrayAdapter<CharSequence> courseSubjectAdapter = ArrayAdapter.createFromResource(mHubActivity.getApplicationContext(),
                R.array.book_course_subject_list, R.layout.spinner_dropdown_item);
        courseSubjectAdapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
        mCourseSubjectView.setAdapter(courseSubjectAdapter);

        ArrayAdapter<CharSequence> courseNumberAdapter = ArrayAdapter.createFromResource(mHubActivity.getApplicationContext(),
                R.array.book_course_number_list, R.layout.spinner_dropdown_item);
        courseNumberAdapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
        mCourseNumberView.setAdapter(courseNumberAdapter);

        //final boolean subject_selection = false;
        //final boolean number_selection = true;

        mCourseSubjectView.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position != 0) course.setSubject(parent.getItemAtPosition(position).toString());
                //subject_selection = true;
                //if (number_selection) course.getTitle();
                if (course.getNumber() > 0) mCourseTitleView.setText(course.getTitle());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                //subject_selection = false;
            }
        });

        mCourseNumberView.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position != 0) course.setNumber(Integer.parseInt(parent.getItemAtPosition(position).toString()));
                //number_selection = true;
                //if (subject_selection) course.getTitle();
                if (course.getSubject() != null) mCourseTitleView.setText(course.getTitle());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                //number_selection = false;
            }
        });

        //if (subject_selection && number_selection) mCourseTitleView.setText(course.getTitle());

        ParseQuery<ParseObject> courseQuery = Course.getQuery();
        courseQuery.orderByAscending(Course.KEY_SUBJECT);
        courseQuery.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                ArrayList<CharSequence> courseSubjects = new ArrayList<>();
                ArrayList<CharSequence> courseNumbers = new ArrayList<>();

                courseSubjects.add(getString(R.string.post_hint_select_course_subject));
                courseNumbers.add(getString(R.string.post_hint_select_course_number));

                for (ParseObject object : objects) {
                    Course c = Course.getInstance(object);
                    courseSubjects.add(c.getSubject());
                    courseNumbers.add(String.valueOf(c.getNumber()));
                }

                ArrayAdapter<CharSequence> subjectAdapter = new ArrayAdapter<CharSequence>(mHubActivity.getApplicationContext(),
                        R.layout.support_simple_spinner_dropdown_item, courseSubjects);
                mCourseSubjectView.setAdapter(subjectAdapter);
                ArrayAdapter<CharSequence> numberAdapter = new ArrayAdapter<CharSequence>(mHubActivity.getApplicationContext(),
                        R.layout.support_simple_spinner_dropdown_item, courseNumbers);
                mCourseNumberView.setAdapter(numberAdapter);
            }
        });

        mPostBtnView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                post.setContent(mContentView.getText().toString());
                post.setUser(ParseUser.getCurrentUser());
                post.setPostType(PostTypes.GROUP_STUDY.getType());
                GroupStudyPost.setPost(post);
                GroupStudyPost.setCourse(course);
                Calendar start = Calendar.getInstance();
                Calendar end = Calendar.getInstance();
                start.set(mDateView.getYear(), mDateView.getMonth(), mDateView.getDayOfMonth(),
                          mStartTimeView.getHour(), mStartTimeView.getMinute());
                end.set(mDateView.getYear(), mDateView.getMonth(), mDateView.getDayOfMonth(),
                        mEndTimeView.getHour(), mEndTimeView.getMinute());
                GroupStudyPost.setStartTime(start.getTime());
                GroupStudyPost.setEndTime(end.getTime());
                GroupStudyPost.setWhere(mLocationView.getText().toString());
                GroupStudyPost.setGroupName(mGroupNameView.getText().toString());
                GroupStudyPost.setMaxPeople(Integer.parseInt(mNumberPeopleView.getText().toString()));

                post.saveInBackground();
                GroupStudyPost.saveInBackground();
            }
        });

        return actionPostView;
    }

    private boolean checkInput() {
        return true;
    }
}

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

        updateCourseSubjectsAdapter(mCourseSubjectView);

        mCourseSubjectView.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position != 0) {
                    String subject = parent.getItemAtPosition(position).toString();
                    course.setSubject(subject);
                    updateCourseNumbersAdapter(mCourseNumberView, subject);
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
                    int number = Integer.parseInt(parent.getItemAtPosition(position).toString());
                    course.setNumber(number);
                    ParseQuery<ParseObject> query = Course.getQuery();
                    query.whereEqualTo(Course.KEY_SUBJECT, course.getSubject());
                    query.whereEqualTo(Course.KEY_NUMBER, course.getNumber());
                    query.findInBackground(new FindCallback<ParseObject>() {
                        @Override
                        public void done(List<ParseObject> objects, ParseException e) {
                            if (objects != null && objects.size() > 0) {
                                Course selectedCourse = Course.getInstance(objects.get(0));
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
                //number_selection = false;
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

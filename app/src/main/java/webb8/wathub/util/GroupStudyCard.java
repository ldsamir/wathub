package webb8.wathub.util;

import android.app.Activity;
import android.view.View;
import android.widget.TextView;

import com.parse.ParseException;

import webb8.wathub.R;
import webb8.wathub.models.BookConditions;
import webb8.wathub.models.Course;
import webb8.wathub.models.GroupStudy;

/**
 * Created by mismayil on 24/02/16.
 */
public class GroupStudyCard extends PostCard {
    private GroupStudy mGroupStudy;

    public GroupStudyCard(Activity activity, GroupStudy groupStudy) {
        mActivity = activity;
        mGroupStudy = groupStudy;
    }

    @Override
    public View getView() {
        View view = mActivity.getLayoutInflater().inflate(R.layout.card_groupstudy, null, false);
        TextView groupName = (TextView) view.findViewById(R.id.group_study_group_name);
        TextView groupCourse = (TextView) view.findViewById(R.id.group_study_course);
        TextView groupWhere = (TextView) view.findViewById(R.id.group_study_where);
        TextView groupStartTime = (TextView) view.findViewById(R.id.group_study_start_time);
        TextView groupEndTime = (TextView) view.findViewById(R.id.group_study_end_time);
        TextView groupNumJoined = (TextView) view.findViewById(R.id.group_study_num_joined);
        TextView groupMaxPeople = (TextView) view.findViewById(R.id.group_study_max_people);

        Course course = mGroupStudy.getCourse();

        try {
            course.fetch();
        } catch (ParseException e) {

        }

        groupName.setText(mGroupStudy.getGroupName());
        groupCourse.setText(course.getSubject() + " " + course.getNumber());
        groupWhere.setText(mGroupStudy.getWhere());
        String startTime = mGroupStudy.getStartTime().toString();
        startTime = startTime.substring(4, startTime.length()-12);
        groupStartTime.setText(startTime);
        String endTime = mGroupStudy.getEndTime().toString();
        endTime = endTime.substring(4, endTime.length()-12);
        groupEndTime.setText(endTime);
        groupNumJoined.setText(String.valueOf(mGroupStudy.getStudents() == null ? 0 : "" + mGroupStudy.getStudents().length()));
        groupMaxPeople.setText(String.valueOf(mGroupStudy.getMaxPeople()));

        return view;
    }
}

package webb8.wathub.util;

import android.app.Activity;
import android.graphics.Color;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.parse.ParseException;
import com.parse.ParseUser;

import org.json.JSONArray;

import webb8.wathub.R;
import webb8.wathub.models.Course;
import webb8.wathub.models.GroupStudy;
import webb8.wathub.models.Parsable;

/**
 * Created by mismayil on 24/02/16.
 */
public class GroupStudyCard extends PostCard {
    private GroupStudy mGroupStudy;
    TextView mGroupName, mGroupCourse, mGroupWhere, mGroupStartTime, mGroupEndTime, mGroupNumJoined, mGroupMaxPeople;
    private Button mBtnJoin;
    private Course mCourse;

    public GroupStudyCard(Activity activity, GroupStudy groupStudy) {
        mActivity = activity;
        mGroupStudy = groupStudy;
        mCourse = mGroupStudy.getCourse();
        refresh();
    }

    private boolean haveJoined (){
        String curObjectid = ParseUser.getCurrentUser().getObjectId();
        JSONArray StudentSet = mGroupStudy.getStudents();
        if (StudentSet != null) {
            for (int i = 0; i < StudentSet.length(); i++) {
                try {
                    if (StudentSet.getJSONObject(i).get(Parsable.KEY_OBJECT_ID).equals(curObjectid)) {
                        return true;
                    }
                }
                catch (org.json.JSONException e){
                    return false;
                }
            }
        }
        return false;
    }

    private void removeFromStudents (JSONArray Students){
        JSONArray newStudents = new JSONArray();

        String curObjectid = ParseUser.getCurrentUser().getObjectId();
        if (Students != null) {
            for (int i = 0; i < Students.length(); i++) {
                try {
                    if (!Students.getJSONObject(i).get(Parsable.KEY_OBJECT_ID).equals(curObjectid)) {
                        // add to the new list
                        newStudents.put(Students.getJSONObject(i));
                    }
                }
                catch (org.json.JSONException e){
                    break;
                }
            }
        }

        mGroupStudy.setStudents(newStudents);
        mGroupStudy.saveInBackground();
    }

    @Override
    public View getView() {
        View view = mActivity.getLayoutInflater().inflate(R.layout.card_groupstudy, null, false);
        mGroupName = (TextView) view.findViewById(R.id.group_study_group_name);
        mGroupCourse = (TextView) view.findViewById(R.id.group_study_course);
        mGroupWhere = (TextView) view.findViewById(R.id.group_study_where);
        mGroupStartTime = (TextView) view.findViewById(R.id.group_study_start_time);
        mGroupEndTime = (TextView) view.findViewById(R.id.group_study_end_time);
        mGroupNumJoined = (TextView) view.findViewById(R.id.group_study_num_joined);
        mGroupMaxPeople = (TextView) view.findViewById(R.id.group_study_max_people);
        mBtnJoin = (Button) view.findViewById(R.id.group_study_btn_join);
        mBtnJoin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // store all of the students joined in the jsonarray
                JSONArray StudentSet = mGroupStudy.getStudents();
                // if the student is in the list, then remove the student
                if (haveJoined()) {
                    // remove the student. TODO: add remove after wrapper to parse::remove is defined
                    removeFromStudents (StudentSet);

                    // update the copy of the Students set
                    StudentSet = mGroupStudy.getStudents();

                    // update the num of students joined in the textview
                    mGroupNumJoined.setText(String.valueOf(StudentSet == null ? 0 : "" + StudentSet.length()));

                    // update the text from "Joined" to "Join"
                    mBtnJoin.setText(String.valueOf("Join"));
                    mBtnJoin.setBackgroundColor(Color.WHITE);

                    // update the color to default
                    //mBtnJoin.setBackgroundResource(android.R.drawable.btn_default);
                }

                // if the student has not beed added to the list AND there is an available seat,then add
                else if (((StudentSet == null) && (mGroupStudy.getMaxPeople() != 0)) ||
                         ((StudentSet != null) && (StudentSet.length() < mGroupStudy.getMaxPeople()))) {
                    // add the user
                    mGroupStudy.addStudent(ParseUser.getCurrentUser());
                    mGroupStudy.saveInBackground();

                    // update the copy of the Students set
                    StudentSet = mGroupStudy.getStudents();

                    // update the num of students joined in the textview
                    mGroupNumJoined.setText(String.valueOf(StudentSet == null ? 0 : "" + StudentSet.length()));

                    // update the text from "Join" to "Joined"
                    mBtnJoin.setText(String.valueOf("Joined"));
                    mBtnJoin.setBackgroundColor(Color.GREEN);
                }
            }
        });

        mGroupName.setText(mGroupStudy.getGroupName());
        mGroupCourse.setText(mCourse.getSubject() + " " + mCourse.getNumber());
        mGroupWhere.setText(mGroupStudy.getWhere());
        String startTime = mGroupStudy.getStartTime().toString();
        startTime = startTime.substring(4, startTime.length()-12);
        mGroupStartTime.setText(startTime);
        String endTime = mGroupStudy.getEndTime().toString();
        endTime = endTime.substring(4, endTime.length()-12);
        mGroupEndTime.setText(endTime);
        mGroupNumJoined.setText(String.valueOf(mGroupStudy.getStudents() == null ? 0 : "" + mGroupStudy.getStudents().length()));
        mGroupMaxPeople.setText(String.valueOf(mGroupStudy.getMaxPeople()));
        if (haveJoined()){
            mBtnJoin.setText(String.valueOf("Joined"));
            mBtnJoin.setBackgroundColor(Color.GREEN);
        }
        else {
            mBtnJoin.setText(String.valueOf("Join"));
            mBtnJoin.setBackgroundColor(Color.WHITE);
        }

        return view;
    }

    public void refresh() {
        try {
            mCourse.fetch();
        } catch (ParseException e) {

        }
    }
}

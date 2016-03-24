package webb8.wathub.models;

import com.parse.ParseClassName;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import org.json.JSONArray;

import java.util.Date;

/**
 * Created by mismayil on 04/02/16.
 */

@ParseClassName("GroupStudy")
public class GroupStudy extends ParseObject implements Parsable {

    // class columns
    public static final String KEY_CLASSNAME = "GroupStudy";
    public static final String KEY_POST = "post";
    public static final String KEY_WHERE = "where";
    public static final String KEY_MAX_PEOPLE = "maxPeople";
    public static final String KEY_START_TIME = "startTime";
    public static final String KEY_END_TIME = "endTime";
    public static final String KEY_COURSE = "course";
    public static final String KEY_STUDENTS = "students";
    public static final String KEY_GROUP_NAME = "groupName";

    public Post getPost() {
        ParseObject object = getParseObject(KEY_POST);
        if (object != null) return ParseObject.createWithoutData(Post.class, object.getObjectId());
        return null;
    }

    public void setPost(Post post) {
        put(KEY_POST, post);
    }

    public String getWhere() {
        return getString(KEY_WHERE);
    }

    public void setWhere(String where) {
        put(KEY_WHERE, where);
    }

    public int getMaxPeople() {
        return getInt(KEY_MAX_PEOPLE);
    }

    public void setMaxPeople(int maxPeople) {
        put(KEY_MAX_PEOPLE, maxPeople);
    }

    public void setStartTime(Date when) { put(KEY_START_TIME, when);}

    public Date getStartTime() { return getDate(KEY_START_TIME);}

    public void setEndTime(Date when) { put(KEY_END_TIME, when);}

    public Date getEndTime() { return getDate(KEY_END_TIME);}

    public Course getCourse() {
        ParseObject object = getParseObject(KEY_COURSE);
        if (object != null) return ParseObject.createWithoutData(Course.class, object.getObjectId());
        return null;
    }

    public void setCourse(Course course) {
        put(KEY_COURSE, course);
    }

    public JSONArray getStudents() {
        return getJSONArray(KEY_STUDENTS);
    }

    public void setStudents(JSONArray students) {
        put(KEY_STUDENTS, students);
    }

    public void addStudent(ParseUser student) {
        addUnique(KEY_STUDENTS, student);
    }

    public String getGroupName() { return getString(KEY_GROUP_NAME);}

    public void setGroupName(String name) { put(KEY_GROUP_NAME, name); }

    public static ParseQuery<ParseObject> getQuery() {
        return ParseQuery.getQuery(KEY_CLASSNAME);
    }

    // convert ParseObject to GroupStudy
    public static GroupStudy getInstance(ParseObject object) {
        if (object != null) return ParseObject.createWithoutData(GroupStudy.class, object.getObjectId());
        return null;
    }
}

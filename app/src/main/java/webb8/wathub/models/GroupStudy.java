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
    public static final String KEY_WHEN = "when";
    public static final String KEY_WHERE = "where";
    public static final String KEY_MAX_PEOPLE = "maxPeople";
    public static final String KEY_DURATION = "duration";
    public static final String KEY_COURSE = "course";
    public static final String KEY_STUDENTS = "students";

    public Post getPost() {
        ParseObject object = getParseObject(KEY_POST);
        if (object != null) return ParseObject.createWithoutData(Post.class, object.getObjectId());
        return null;
    }

    public void setPost(Post post) {
        put(KEY_POST, post);
    }

    public Date getWhen() {
        return getDate(KEY_WHEN);
    }

    public void setWhen(Date when) {
        put(KEY_WHEN, when);
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

    public int getDuration() {
        return getInt(KEY_DURATION);
    }

    public void setDuration(int duration) {
        put(KEY_DURATION, duration);
    }

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

    public void addStudent(ParseUser student) {
        addUnique(KEY_STUDENTS, student);
    }

    public static ParseQuery<ParseObject> getQuery() {
        return ParseQuery.getQuery(KEY_CLASSNAME);
    }

    public static GroupStudy getInstance(ParseObject object) {
        if (object != null) return ParseObject.createWithoutData(GroupStudy.class, object.getObjectId());
        return null;
    }
}

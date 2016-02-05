package webb8.wathub.models;

import com.parse.ParseClassName;
import com.parse.ParseObject;
import com.parse.ParseUser;

import org.json.JSONArray;

import java.util.Date;

/**
 * Created by mismayil on 04/02/16.
 */

@ParseClassName("GroupStudy")
public class GroupStudy extends ParseObject {

    public ParseObject getPost() {
        return getParseObject("post");
    }

    public void setPost(ParseObject post) {
        put("post", post);
    }

    public Date getWhen() {
        return getDate("when");
    }

    public void setWhen(Date when) {
        put("when", when);
    }

    public String getWhere() {
        return getString("where");
    }

    public void setWhere(String where) {
        put("where", where);
    }

    public int getMaxPeople() {
        return getInt("maxPeople");
    }

    public void setMaxPeople(int maxPeople) {
        put("maxPeople", maxPeople);
    }

    public int getDuration() {
        return getInt("duration");
    }

    public void setDuration(int duration) {
        put("duration", duration);
    }

    public ParseObject getCourse() {
        return getParseObject("course");
    }

    public void setCourse(ParseObject course) {
        put("course", course);
    }

    public JSONArray getStudents() {
        return getJSONArray("students");
    }

    public void addStudent(ParseUser student) {
        addUnique("students", student);
    }
}

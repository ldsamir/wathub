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
public class GroupStudy extends ParseObject {
    private ParseObject object;

    public GroupStudy(ParseObject object) {
        this.object = object;
    }

    public GroupStudy() {
        this.object = this;
    }

    public ParseObject getPost() {
        return object.getParseObject("post");
    }

    public void setPost(ParseObject post) {
        object.put("post", post);
    }

    public Date getWhen() {
        return object.getDate("when");
    }

    public void setWhen(Date when) {
        object.put("when", when);
    }

    public String getWhere() {
        return object.getString("where");
    }

    public void setWhere(String where) {
        object.put("where", where);
    }

    public int getMaxPeople() {
        return object.getInt("maxPeople");
    }

    public void setMaxPeople(int maxPeople) {
        object.put("maxPeople", maxPeople);
    }

    public int getDuration() {
        return object.getInt("duration");
    }

    public void setDuration(int duration) {
        object.put("duration", duration);
    }

    public ParseObject getCourse() {
        return object.getParseObject("course");
    }

    public void setCourse(ParseObject course) {
        object.put("course", course);
    }

    public JSONArray getStudents() {
        return object.getJSONArray("students");
    }

    public void addStudent(ParseUser student) {
        object.addUnique("students", student);
    }

    public static ParseQuery<ParseObject> getQuery() {
        return ParseQuery.getQuery("GroupStudy");
    }
}

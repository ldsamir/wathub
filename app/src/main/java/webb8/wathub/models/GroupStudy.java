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

    public Post getPost() {
        return ParseObject.createWithoutData(Post.class, getParseObject("post").getObjectId());
    }

    public void setPost(Post post) {
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

    public Course getCourse() {
        return ParseObject.createWithoutData(Course.class, getParseObject("course").getObjectId());
    }

    public void setCourse(Course course) {
        put("course", course);
    }

    public JSONArray getStudents() {
        return getJSONArray("students");
    }

    public void addStudent(ParseUser student) {
        addUnique("students", student);
    }

    public static ParseQuery<ParseObject> getQuery() {
        return ParseQuery.getQuery("GroupStudy");
    }

    public static GroupStudy getInstance(ParseObject object) {
        return ParseObject.createWithoutData(GroupStudy.class, object.getObjectId());
    }
}

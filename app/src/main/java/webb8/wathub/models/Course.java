package webb8.wathub.models;

import com.parse.ParseClassName;
import com.parse.ParseObject;
import com.parse.ParseQuery;

/**
 * Created by mismayil on 04/02/16.
 */

@ParseClassName("Course")
public class Course extends ParseObject {
    private ParseObject object;

    public Course(ParseObject object) {
        this.object = object;
    }

    public Course() {
        this.object = this;
    }

    public String getSubject() {
        return object.getString("subject");
    }

    public void setSubject(String subject) {
        object.put("subject", subject);
    }

    public int getNumber() {
        return object.getInt("number");
    }

    public void setNumber(int number) {
        object.put("number", number);
    }

    public String getTitle() {
        return object.getString("title");
    }

    public void setTitle(String title) {
        object.put("title", title);
    }

    public static ParseQuery<ParseObject> getQuery() {
        return ParseQuery.getQuery("Course");
    }
}

package webb8.wathub.models;

import com.parse.ParseClassName;
import com.parse.ParseObject;
import com.parse.ParseQuery;

/**
 * Created by mismayil on 04/02/16.
 */

@ParseClassName("Course")
public class Course extends ParseObject implements Parsable {

    // class columns
    public static final String KEY_CLASSNAME = "Course";
    public static final String KEY_SUBJECT = "subject";
    public static final String KEY_NUMBER = "number";
    public static final String KEY_TITLE = "title";

    public String getSubject() {
        return getString(KEY_SUBJECT);
    }

    public void setSubject(String subject) {
        put(KEY_SUBJECT, subject);
    }

    public int getNumber() {
        return getInt(KEY_NUMBER);
    }

    public void setNumber(int number) {
        put(KEY_NUMBER, number);
    }

    public String getTitle() {
        return getString(KEY_TITLE);
    }

    public void setTitle(String title) {
        put(KEY_TITLE, title);
    }

    public static ParseQuery<ParseObject> getQuery() {
        return ParseQuery.getQuery(KEY_CLASSNAME);
    }

    // convert ParseObject to Course
    public static Course getInstance(ParseObject object) {
        if (object != null) return ParseObject.createWithoutData(Course.class, object.getObjectId());
        return null;
    }
}

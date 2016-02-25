package webb8.wathub.models;

import com.parse.ParseClassName;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Iterator;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by mismayil on 04/02/16;
 * Updated by smusali on 25/02/16.
 */

@ParseClassName("Course")
public class Course extends ParseObject implements Parsable {

    // class columns
    public static final String KEY_CLASSNAME = "Course";
    public static final String KEY_SUBJECT = "subject";
    public static final String KEY_NUMBER = "number";
    public static final String KEY_TITLE = "title";
    public static final String COURSES_FILE = "/home/mismayil/UW/CS446/wathub/app/src/main/java/webb8/wathub/data/Courses.json";

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

    // load Courses into DB
    public static void loadCourses() {
        String stringCourse = "";
        try {
            BufferedReader br = new BufferedReader(new FileReader(COURSES_FILE));
            StringBuilder sb = new StringBuilder();
            String line = br.readLine();
            while (line != null) {
                sb.append(line);
                line = br.readLine();
            }
            stringCourse = sb.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }

        JSONObject courses = null;
        try {
            courses = new JSONObject(stringCourse);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Iterator<String> subjects = courses.keys();
        while (subjects.hasNext()) {
            String subject = subjects.next();
            JSONObject part = null;
            try {
                part = courses.getJSONObject(subject);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            Iterator<String> numbers = part.keys();
            while (numbers.hasNext()) {
                String number = numbers.next();
                String title = null;
                try {
                    title = part.getString(number);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                Course new_course = new Course();
                new_course.setSubject(subject);
                new_course.setNumber(Integer.parseInt(number));
                new_course.setTitle(title);
                new_course.saveInBackground();
            }
        }
    }
}

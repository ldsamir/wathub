package webb8.wathub.models;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.pm.ApplicationInfo;
import android.content.res.AssetManager;

import com.parse.ParseClassName;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Iterator;
import org.json.JSONException;
import org.json.JSONObject;

import webb8.wathub.init.App;

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
    public static final String COURSES_FILE = "data/Courses.json";

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
    public static void loadCourses(Context c) {

        String stringCourse = "";
        try {
            InputStream f = c.getAssets().open(COURSES_FILE);
            stringCourse = f.toString();
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
package webb8.wathub.util;

import android.app.Activity;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

import webb8.wathub.R;
import webb8.wathub.models.Course;

/**
 * Created by mismayil on 3/24/16.
 */
public class Util {
    public static void updateCourseSubjectsAdapter(final Activity activity, final Spinner spinner) {
        ParseQuery<ParseObject> courseQuery = Course.getQuery();
        courseQuery.orderByAscending(Course.KEY_SUBJECT);
        courseQuery.setLimit(5200);
        courseQuery.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                ArrayList<CharSequence> courseSubjects = new ArrayList<>();

                courseSubjects.add(activity.getString(R.string.post_hint_select_course_subject));

                for (ParseObject object : objects) {
                    Course c = Course.getInstance(object);
                    String subject = c.getSubject();
                    if (!courseSubjects.contains(subject)) courseSubjects.add(c.getSubject());
                }

                ArrayAdapter<CharSequence> subjectAdapter = new ArrayAdapter<>(activity,
                        R.layout.simple_spinner_item, courseSubjects);
                subjectAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
                spinner.setAdapter(subjectAdapter);
            }
        });
    }

    public static void updateCourseNumbersAdapter(final Activity activity, final Spinner spinner, String courseSubject) {
        ParseQuery<ParseObject> courseQuery = Course.getQuery();
        courseQuery.orderByAscending(Course.KEY_NUMBER);
        courseQuery.whereEqualTo(Course.KEY_SUBJECT, courseSubject);

        courseQuery.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                ArrayList<CharSequence> courseNumbers = new ArrayList<>();

                courseNumbers.add(activity.getString(R.string.post_hint_select_course_number));

                for (ParseObject object : objects) {
                    Course c = Course.getInstance(object);
                    if (!courseNumbers.contains(c.getNumber())) courseNumbers.add(c.getNumber());
                }

                ArrayAdapter<CharSequence> subjectAdapter = new ArrayAdapter<>(activity,
                        R.layout.simple_spinner_item, courseNumbers);
                subjectAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
                spinner.setAdapter(subjectAdapter);
            }
        });
    }
}

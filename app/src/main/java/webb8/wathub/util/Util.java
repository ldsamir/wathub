package webb8.wathub.util;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.github.brnunes.swipeablerecyclerview.SwipeableRecyclerViewTouchListener;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import webb8.wathub.R;
import webb8.wathub.models.Course;

/**
 * Created by mismayil on 3/24/16.
 */
public class Util {
    public static void updateCourseSubjectsAdapter(final Activity activity, final Spinner spinner) {
        final String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        final ArrayList<String> courseSubjects = new ArrayList<>();
        courseSubjects.add(activity.getString(R.string.post_hint_select_course_subject));

        for (int i = 0; i < alphabet.length(); i++) {
            String letter = alphabet.substring(i, i + 1);
            ParseQuery<ParseObject> courseQuery = Course.getQuery();
            courseQuery.orderByAscending(Course.KEY_SUBJECT);
            courseQuery.setLimit(1000);
            courseQuery.whereStartsWith(Course.KEY_SUBJECT, letter);
            courseQuery.findInBackground(new FindCallback<ParseObject>() {
                @Override
                public void done(List<ParseObject> objects, ParseException e) {
                    for (ParseObject object : objects) {
                        Course c = Course.getInstance(object);
                        String subject = c.getSubject();
                        if (!courseSubjects.contains(subject)) courseSubjects.add(c.getSubject());
                    }

                    Collections.sort(courseSubjects.subList(1, courseSubjects.size()));
                    ArrayAdapter<String> subjectAdapter = new ArrayAdapter<>(activity,
                            R.layout.simple_spinner_item, courseSubjects);
                    subjectAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
                    spinner.setAdapter(subjectAdapter);
                }
            });
        }
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

    public static SwipeableRecyclerViewTouchListener getSwipeTouchListener(RecyclerView recyclerView, final PostAdapter adapter) {
        SwipeableRecyclerViewTouchListener swipeTouchListener =
                new SwipeableRecyclerViewTouchListener(recyclerView,
                        new SwipeableRecyclerViewTouchListener.SwipeListener() {
                            @Override
                            public boolean canSwipeLeft(int position) {
                                return true;
                            }

                            @Override
                            public boolean canSwipeRight(int position) {
                                return true;
                            }

                            @Override
                            public void onDismissedBySwipeLeft(RecyclerView recyclerView, int[] reverseSortedPositions) {
                                for (int position : reverseSortedPositions) {
                                    adapter.removePostCard(position, PostState.DONE);
                                }
                                adapter.notifyDataSetChanged();
                            }

                            @Override
                            public void onDismissedBySwipeRight(RecyclerView recyclerView, int[] reverseSortedPositions) {
                                for (int position : reverseSortedPositions) {
                                    adapter.removePostCard(position, PostState.FAVORITED);
                                }
                                adapter.notifyDataSetChanged();
                            }
                        });

        return swipeTouchListener;
    }
}

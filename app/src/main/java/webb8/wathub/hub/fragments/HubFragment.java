package webb8.wathub.hub.fragments;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

import webb8.wathub.R;
import webb8.wathub.hub.Action;
import webb8.wathub.hub.NavItem;
import webb8.wathub.hub.HubActivity;
import webb8.wathub.hub.fragments.actions.ActionPostFragment;
import webb8.wathub.hub.fragments.actions.ActionSearchFragment;
import webb8.wathub.hub.fragments.navigation.FavoriteFragment;
import webb8.wathub.hub.fragments.navigation.MessageFragment;
import webb8.wathub.hub.fragments.navigation.PostFragment;
import webb8.wathub.hub.fragments.navigation.ProfileFragment;
import webb8.wathub.models.Course;

/**
 * Created by mismayil on 23/02/16.
 */
public class HubFragment extends Fragment {

    protected static final String ARG_SECTION_NUMBER = "SECTION_NUMBER";
    protected static Activity mHubActivity;

    public HubFragment() {}

    public static HubFragment newInstance(int action) {
        HubFragment fragment = null;

        if (action == NavItem.PROFILE.getId()) {
            fragment = new ProfileFragment();
        } else

        if (action == NavItem.ALL_POSTS.getId() ||
            action == NavItem.BOOK_EXCHANGE_POSTS.getId() ||
            action == NavItem.GROUP_STUDY_POSTS.getId() ||
            action == NavItem.CARPOOL_POSTS.getId()) {
            fragment = PostFragment.newInstance(action);
        } else

        if (action == NavItem.MESSAGES.getId()) {
            fragment = new MessageFragment();
        } else

        if (action == NavItem.FAVORITES.getId()) {
            fragment = new FavoriteFragment();
        } else

        if (action == Action.ACTION_POST_GENERAL.getId() ||
            action == Action.ACTION_POST_BOOK_EXCHANGE.getId() ||
            action == Action.ACTION_POST_GROUP_STUDY.getId() ||
            action == Action.ACTION_CARPOOL.getId()) {
            fragment = ActionPostFragment.newInstance(action);
        } else

        if (action == Action.ACTION_SEARCH.getId()) {
            fragment = new ActionSearchFragment();
        }

        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, action);
        if (fragment != null) fragment.setArguments(args);

        return fragment;
    }

    // set current activity
    public static void setHubActivity(Activity activity) {
        mHubActivity = activity;
    }

    @Override
    public void onAttach(Context activity) {
        super.onAttach(activity);
        ((HubActivity) activity).onSectionAttached(
                getArguments().getInt(ARG_SECTION_NUMBER));
    }

    protected void updateCourseSubjectsAdapter(final Spinner spinner) {
        ParseQuery<ParseObject> courseQuery = Course.getQuery();
        courseQuery.orderByAscending(Course.KEY_SUBJECT);
        courseQuery.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                ArrayList<CharSequence> courseSubjects = new ArrayList<>();

                courseSubjects.add(getString(R.string.post_hint_select_course_subject));

                for (ParseObject object : objects) {
                    Course c = Course.getInstance(object);
                    String subject = c.getSubject();
                    if (!courseSubjects.contains(subject)) courseSubjects.add(c.getSubject());
                }

                ArrayAdapter<CharSequence> subjectAdapter = new ArrayAdapter<>(getActivity(),
                        R.layout.simple_spinner_item, courseSubjects);
                subjectAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
                spinner.setAdapter(subjectAdapter);
            }
        });
    }

    protected void updateCourseNumbersAdapter(final Spinner spinner, String courseSubject) {
        ParseQuery<ParseObject> courseQuery = Course.getQuery();
        courseQuery.orderByAscending(Course.KEY_NUMBER);
        courseQuery.whereEqualTo(Course.KEY_SUBJECT, courseSubject);

        courseQuery.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                ArrayList<CharSequence> courseNumbers = new ArrayList<>();

                courseNumbers.add(getString(R.string.post_hint_select_course_number));

                for (ParseObject object : objects) {
                    Course c = Course.getInstance(object);
                    if (!courseNumbers.contains(c.getNumber())) courseNumbers.add(c.getNumber());
                }

                ArrayAdapter<CharSequence> subjectAdapter = new ArrayAdapter<>(getActivity(),
                        R.layout.simple_spinner_item, courseNumbers);
                subjectAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
                spinner.setAdapter(subjectAdapter);
            }
        });
    }
}

package webb8.wathub.hub.fragments;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

import webb8.wathub.R;
import webb8.wathub.hub.Action;
import webb8.wathub.models.Course;
import webb8.wathub.models.Post;

/**
 * This fragment handles posting actions
 */
public class ActionPostFragment extends HubFragment {

    // UI fields
    protected EditText mContentView;
    protected Button mPostBtnView;

    public ActionPostFragment() {
        // Required empty public constructor
    }

    public static ActionPostFragment newInstance(int action) {
        ActionPostFragment fragment = null;

        if (action == Action.ACTION_POST_GENERAL.getId()) {
            fragment = new ActionPostFragment();
        } else

        if (action == Action.ACTION_POST_BOOK_EXCHANGE.getId()) {
            fragment = new ActionBookExchangePostFragment();
        } else

        if (action == Action.ACTION_POST_GROUP_STUDY.getId()) {
            fragment = new ActionGroupStudyPostFragment();
        }

        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View actionPostView = inflater.inflate(R.layout.fragment_action_post, container, false);
        mContentView = (EditText) actionPostView.findViewById(R.id.edit_post_content);
        mPostBtnView = (Button) actionPostView.findViewById(R.id.action_post_go);

        final Post post = new Post();

        mPostBtnView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkInput()) {
                    post.setUser(ParseUser.getCurrentUser());
                    post.setContent(mContentView.getText().toString());
                    post.setPostType(null);

                    post.saveInBackground();
                }
            }
        });

        return actionPostView;
    }

    private boolean checkInput() {
        mContentView.setError(null);

        if (TextUtils.isEmpty(mContentView.getText().toString())) {
            mContentView.setError(getString(R.string.error_post_empty_content));
            return false;
        }

        return true;
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
                    courseNumbers.add(c.getNumber());
                }

                ArrayAdapter<CharSequence> subjectAdapter = new ArrayAdapter<>(getActivity(),
                        R.layout.simple_spinner_item, courseNumbers);
                subjectAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
                spinner.setAdapter(subjectAdapter);
            }
        });
    }
}

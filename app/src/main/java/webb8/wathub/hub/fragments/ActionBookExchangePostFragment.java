package webb8.wathub.hub.fragments;

import android.app.FragmentManager;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.util.List;

import webb8.wathub.R;
import webb8.wathub.hub.NavItem;
import webb8.wathub.models.BookConditions;
import webb8.wathub.models.BookExchange;
import webb8.wathub.models.Course;
import webb8.wathub.models.Post;
import webb8.wathub.models.PostTypes;

/**
 * Created by mismayil on 25/02/16.
 */

/**
 * This fragment handles book exchange posts
 */
public class ActionBookExchangePostFragment extends ActionPostFragment {

    public ActionBookExchangePostFragment() {}

    // UI fields
    protected EditText mBookTitleView;
    protected Spinner mCourseSubjectView;
    protected Spinner mCourseNumberView;
    protected EditText mBookPriceView;
    protected Spinner mBookConditionView;
    protected TextView mCourseTitleView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View actionPostView = inflater.inflate(R.layout.fragment_action_post, container, false);
        FrameLayout actionPostContainer = (FrameLayout) actionPostView.findViewById(R.id.post_action_container);
        View bookExchangeSectionView = inflater.inflate(R.layout.fragment_action_bookexchange, container, false);

        actionPostContainer.addView(bookExchangeSectionView);

        mContentView = (EditText) actionPostView.findViewById(R.id.edit_post_content);
        mPostBtnView = (Button) actionPostView.findViewById(R.id.action_post_go);
        mBookTitleView = (EditText) bookExchangeSectionView.findViewById(R.id.edit_book_title);
        mCourseSubjectView = (Spinner) bookExchangeSectionView.findViewById(R.id.select_book_course_subject);
        mCourseNumberView = (Spinner) bookExchangeSectionView.findViewById(R.id.select_book_course_number);
        mBookPriceView = (EditText) bookExchangeSectionView.findViewById(R.id.edit_book_price);
        mBookConditionView = (Spinner) bookExchangeSectionView.findViewById(R.id.select_book_condition);
        mCourseTitleView = (TextView) bookExchangeSectionView.findViewById(R.id.text_course_title);

        final Course course = new Course();
        final Post post = new Post();
        final BookExchange bookExchangePost = new BookExchange();

        ArrayAdapter<CharSequence> courseSubjectAdapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.book_course_subject_list, R.layout.simple_spinner_item);
        courseSubjectAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        mCourseSubjectView.setAdapter(courseSubjectAdapter);

        ArrayAdapter<CharSequence> courseNumberAdapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.book_course_number_list, R.layout.simple_spinner_item);
        courseNumberAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        mCourseNumberView.setAdapter(courseNumberAdapter);

        ArrayAdapter<CharSequence> conditionAdapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.book_conditions, R.layout.simple_spinner_item);
        conditionAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        mBookConditionView.setAdapter(conditionAdapter);

        updateCourseSubjectsAdapter(mCourseSubjectView);

        mCourseSubjectView.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position != 0) {
                    String subject = parent.getItemAtPosition(position).toString();
                    updateCourseNumbersAdapter(mCourseNumberView, subject);
                    course.setSubject(subject);
                } else {
                    mCourseTitleView.setVisibility(View.GONE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        mCourseNumberView.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position != 0) {
                    String number = parent.getItemAtPosition(position).toString();
                    course.setNumber(number);
                    ParseQuery<ParseObject> query = Course.getQuery();
                    query.whereEqualTo(Course.KEY_SUBJECT, course.getSubject());
                    query.whereEqualTo(Course.KEY_NUMBER, course.getNumber());
                    query.findInBackground(new FindCallback<ParseObject>() {
                        @Override
                        public void done(List<ParseObject> objects, ParseException e) {
                            if (objects != null && objects.size() > 0) {
                                Course selectedCourse = Course.getInstance(objects.get(0));
                                mCourseTitleView.setText(selectedCourse.getTitle());
                                mCourseTitleView.setVisibility(View.VISIBLE);
                            }
                        }
                    });
                } else {
                    mCourseTitleView.setVisibility(View.GONE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        mBookConditionView.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position != 0) {
                    String condition = parent.getItemAtPosition(position).toString();
                    if (condition.equalsIgnoreCase(getString(BookConditions.BAD.getNameId()))) {
                        bookExchangePost.setCondition(BookConditions.BAD.getId());
                    } else if (condition.equalsIgnoreCase(getString(BookConditions.MODERATE.getNameId()))) {
                        bookExchangePost.setCondition(BookConditions.MODERATE.getId());
                    } else if (condition.equalsIgnoreCase(getString(BookConditions.GOOD.getNameId()))) {
                        bookExchangePost.setCondition(BookConditions.GOOD.getId());
                    } else if (condition.equalsIgnoreCase(getString(BookConditions.EXCELLENT.getNameId()))) {
                        bookExchangePost.setCondition(BookConditions.EXCELLENT.getId());
                    }

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        mPostBtnView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkInput()) {
                    post.setContent(mContentView.getText().toString());
                    post.setUser(ParseUser.getCurrentUser());
                    post.setPostType(PostTypes.BOOK_EXCHANGE.getType());
                    bookExchangePost.setPost(post);
                    bookExchangePost.setTitle(mBookTitleView.getText().toString());
                    bookExchangePost.setCourse(course);
                    bookExchangePost.setPrice(Double.parseDouble(mBookPriceView.getText().toString()));

                    post.saveInBackground(new SaveCallback() {
                        @Override
                        public void done(ParseException e) {
                            if (e == null) {
                                FragmentManager fragmentManager = getFragmentManager();
                                Toast.makeText(getActivity(), R.string.info_post_published, Toast.LENGTH_SHORT).show();
                                fragmentManager.beginTransaction().replace(R.id.container, HubFragment.newInstance(NavItem.ALL_POSTS.getId())).commit();
                            } else {
                                Toast.makeText(getActivity(), R.string.error_publishing_post, Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

                    bookExchangePost.saveInBackground();
                }

            }
        });

        return actionPostView;
    }

    private boolean checkInput() {
        mContentView.setError(null);
        mBookTitleView.setError(null);
        mBookPriceView.setError(null);


        if (TextUtils.isEmpty(mContentView.getText().toString())) {
            mContentView.setError(getString(R.string.error_post_empty_content));
            return false;
        }

        if (TextUtils.isEmpty(mBookTitleView.getText().toString())) {
            mBookTitleView.setError(getString(R.string.error_post_empty_book_title));
            return false;
        }

        if(mCourseSubjectView.getSelectedItem().toString().equals(getResources().getString(R.string.select_course_subject))){
            Toast.makeText(getActivity(),"Please select course subject", Toast.LENGTH_SHORT).show();
            return false;
        }

        if(mCourseNumberView.getSelectedItem().toString().equals(getResources().getString(R.string.select_course_number))){
            Toast.makeText(getActivity(),"Please select course number", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (TextUtils.isEmpty(mBookPriceView.getText().toString())) {
            mBookPriceView.setError(getString(R.string.error_post_empty_book_price));
            return false;
        }




        return true;
    }
}

package webb8.wathub.hub.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.Spinner;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

import webb8.wathub.R;
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
    protected Spinner mSelectCourseNumber;
    protected EditText mBookPriceView;
    protected Spinner mBookConditionView;

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
        mSelectCourseNumber = (Spinner) bookExchangeSectionView.findViewById(R.id.select_book_course_number);
        mBookPriceView = (EditText) bookExchangeSectionView.findViewById(R.id.edit_book_price);
        mBookConditionView = (Spinner) bookExchangeSectionView.findViewById(R.id.select_book_condition);

        final Course course = new Course();
        final Post post = new Post();
        final BookExchange bookExchangePost = new BookExchange();

        ArrayAdapter<CharSequence> courseSubjectAdapter = ArrayAdapter.createFromResource(mHubActivity.getApplicationContext(),
                R.array.book_course_subject_list, R.layout.spinner_dropdown_item);
        courseSubjectAdapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
        mCourseSubjectView.setAdapter(courseSubjectAdapter);

        ArrayAdapter<CharSequence> courseNumberAdapter = ArrayAdapter.createFromResource(mHubActivity.getApplicationContext(),
                R.array.book_course_number_list, R.layout.spinner_dropdown_item);
        courseNumberAdapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
        mSelectCourseNumber.setAdapter(courseNumberAdapter);

        ArrayAdapter<CharSequence> conditionAdapter = ArrayAdapter.createFromResource(mHubActivity.getApplicationContext(),
                R.array.book_conditions, R.layout.spinner_dropdown_item);
        conditionAdapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
        mBookConditionView.setAdapter(conditionAdapter);

        mCourseSubjectView.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position != 0) course.setSubject(parent.getItemAtPosition(position).toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        mSelectCourseNumber.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position != 0)
                    course.setNumber(Integer.parseInt(parent.getItemAtPosition(position).toString()));
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

        ParseQuery<ParseObject> courseQuery = Course.getQuery();
        courseQuery.orderByAscending(Course.KEY_SUBJECT);
        courseQuery.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                ArrayList<CharSequence> courseSubjects = new ArrayList<>();
                ArrayList<CharSequence> courseNumbers = new ArrayList<>();

                courseSubjects.add(getString(R.string.post_hint_select_course_subject));
                courseNumbers.add(getString(R.string.post_hint_select_course_number));

                for (ParseObject object : objects) {
                    Course c = Course.getInstance(object);
                    courseSubjects.add(c.getSubject());
                    courseNumbers.add(String.valueOf(c.getNumber()));
                }

                ArrayAdapter<CharSequence> subjectAdapter = new ArrayAdapter<CharSequence>(mHubActivity.getApplicationContext(),
                        R.layout.support_simple_spinner_dropdown_item, courseSubjects);
                mCourseSubjectView.setAdapter(subjectAdapter);
                ArrayAdapter<CharSequence> numberAdapter = new ArrayAdapter<CharSequence>(mHubActivity.getApplicationContext(),
                        R.layout.support_simple_spinner_dropdown_item, courseNumbers);
                mSelectCourseNumber.setAdapter(numberAdapter);

            }
        });

        mPostBtnView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                post.setContent(mContentView.getText().toString());
                post.setUser(ParseUser.getCurrentUser());
                post.setPostType(PostTypes.BOOK_EXCHANGE.getType());
                bookExchangePost.setPost(post);
                bookExchangePost.setTitle(mBookTitleView.getText().toString());
                bookExchangePost.setCourse(course);
                bookExchangePost.setPrice(Double.parseDouble(mBookTitleView.getText().toString()));

                post.saveInBackground();
                bookExchangePost.saveInBackground();
            }
        });

        return actionPostView;
    }

    private boolean checkInput() {
        return true;
    }
}

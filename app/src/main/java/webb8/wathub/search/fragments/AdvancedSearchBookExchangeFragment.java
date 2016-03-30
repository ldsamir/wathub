package webb8.wathub.search.fragments;

import android.app.Activity;
import android.app.FragmentManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import webb8.wathub.R;
import webb8.wathub.hub.fragments.PostFeedFragment;
import webb8.wathub.models.BookExchange;
import webb8.wathub.models.Course;
import webb8.wathub.models.Post;
import webb8.wathub.util.NavItem;
import webb8.wathub.util.PostCard;
import webb8.wathub.util.Util;

/**
 * Created by mismayil on 3/24/16.
 */
public class AdvancedSearchBookExchangeFragment extends AdvancedSearchFragment {

    // UI fields
    private EditText mBookTitleView;
    private Spinner mBookCourseSubjectView;
    private Spinner mBookCourseNumberView;
    private EditText mBookMinPriceView;
    private EditText mBookMaxPriceView;
    private Spinner mBookConditionView;
    private RelativeLayout mProgressBar;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View actionSearchView = inflater.inflate(R.layout.fragment_advanced_search, container, false);
        final View actionSearchBookExchangeView = inflater.inflate(R.layout.fragment_advanced_search_bookexchange, container, false);
        mActionSearchContainer = (FrameLayout) actionSearchView.findViewById(R.id.advanced_search_container);

        mProgressBar = (RelativeLayout) actionSearchView.findViewById(R.id.progress_bar);
        mActionSearchContainer.addView(actionSearchBookExchangeView);

        mContentView = (EditText) actionSearchView.findViewById(R.id.edit_search_content);
        mSearchTypeView = (Spinner) actionSearchView.findViewById(R.id.select_search_type);
        mSearchBtnView = (Button) actionSearchView.findViewById(R.id.btn_search);

        mBookTitleView = (EditText) actionSearchBookExchangeView.findViewById(R.id.edit_search_book_title);
        mBookCourseSubjectView = (Spinner) actionSearchBookExchangeView.findViewById(R.id.select_search_book_course_subject);
        mBookCourseNumberView = (Spinner) actionSearchBookExchangeView.findViewById(R.id.select_search_book_course_number);
        mBookMinPriceView = (EditText) actionSearchBookExchangeView.findViewById(R.id.edit_search_book_min_price);
        mBookMaxPriceView = (EditText) actionSearchBookExchangeView.findViewById(R.id.edit_search_book_max_price);
        mBookConditionView = (Spinner) actionSearchBookExchangeView.findViewById(R.id.select_search_book_condition);

        ArrayAdapter<CharSequence> searchTypeAdapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.search_type_list, R.layout.simple_spinner_item);
        searchTypeAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        mSearchTypeView.setAdapter(searchTypeAdapter);

        ArrayAdapter<CharSequence> courseSubjectAdapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.search_course_subject_list, R.layout.simple_spinner_item);
        courseSubjectAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);

        ArrayAdapter<CharSequence> courseNumberAdapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.search_course_number_list, R.layout.simple_spinner_item);
        courseNumberAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);

        Util.updateCourseSubjectsAdapter(getActivity(), mBookCourseSubjectView);

        mSearchTypeView.setSelection(2);

        mSearchTypeView.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                FragmentManager fragmentManager = getFragmentManager();

                if (parent.getItemAtPosition(position).toString().equalsIgnoreCase(getString(NavItem.ALL_POSTS.getNameId()))) {
                    fragmentManager.beginTransaction()
                            .replace(R.id.search_fragment_container, new AdvancedSearchFragment())
                            .commit();
                } else if (parent.getItemAtPosition(position).toString().equalsIgnoreCase(getString(NavItem.GROUP_STUDY_POSTS.getNameId()))) {
                    fragmentManager.beginTransaction()
                            .replace(R.id.search_fragment_container, new AdvancedSearchGroupStudyFragment())
                            .commit();
                } else if (parent.getItemAtPosition(position).toString().equalsIgnoreCase(getString(NavItem.CARPOOL_POSTS.getNameId()))) {
                    fragmentManager.beginTransaction()
                            .replace(R.id.search_fragment_container, new AdvancedSearchCarpoolFragment())
                            .commit();
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        mBookCourseSubjectView.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position != 0) {
                    Util.updateCourseNumbersAdapter(getActivity(), mBookCourseNumberView, parent.getItemAtPosition(position).toString());
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        ArrayAdapter<CharSequence> conditionAdapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.book_conditions, R.layout.simple_spinner_item);
        conditionAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        mBookConditionView.setAdapter(conditionAdapter);

        mSearchBtnView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Should start while clicking the search button:
                mProgressBar.setVisibility(View.VISIBLE);
                final String selectStr = "select", emptyStr = "";
                final Map<String, Integer> conditionMap = new HashMap<String, Integer>();
                conditionMap.put("BAD", 0);
                conditionMap.put("MODERATE", 1);
                conditionMap.put("GOOD", 2);
                conditionMap.put("EXCELLENT", 3);

                // Input data and verification of the inputs:
                final String title = mBookTitleView.getText().toString();
                final Boolean checkTitle = !(title.equals(emptyStr));
                final String courseSubject = mBookCourseSubjectView.getSelectedItem().toString();
                final Boolean checkCourseSubject = !(courseSubject.toLowerCase().contains(selectStr));
                String courseNumberBuf = emptyStr;
                Boolean checkCourseNumberBuf = false;
                if (checkCourseSubject) {
                    courseNumberBuf += mBookCourseNumberView.getSelectedItem().toString();
                    checkCourseNumberBuf = checkCourseNumberBuf || !(courseNumberBuf.toLowerCase().contains(selectStr));
                }
                final String courseNumber = courseNumberBuf;
                final Boolean checkCourseNumber = checkCourseNumberBuf;
                final String minPrice = mBookMinPriceView.getText().toString();
                // For the case below we are going to ignore MinPrice in the search:
                final Boolean checkMinPrice = !(minPrice.equals(emptyStr));
                final String maxPrice = mBookMaxPriceView.getText().toString();
                // For the case below we are going to ignore MaxPrice in the search:
                final Boolean checkMaxPrice = !(maxPrice.equals(emptyStr));
                final String conditionStr = mBookConditionView.getSelectedItem().toString();
                final Boolean checkCondition = !(conditionStr.toLowerCase().contains(selectStr));

                /* First, we have to search through the courses
                 * to obtain the course IDs for being able to use them
                 * in the main search:
                 */
                ParseQuery<ParseObject> courseQuery = Course.getQuery();
                final Collection<Course> courses = new ArrayList<Course>();
                if (checkCourseSubject) {
                    courseQuery.whereEqualTo(Course.KEY_SUBJECT, courseSubject);
                    System.out.println(courseSubject);
                    // Since we can select the number after selecting the subject:
                    if (checkCourseNumber) {
                        System.out.println(courseNumber);
                        courseQuery.whereEqualTo(Course.KEY_NUMBER, courseNumber);
                    }
                    courseQuery.findInBackground(new FindCallback<ParseObject>() {
                        public void done(List<ParseObject> objects, ParseException e) {
                            if (e == null) {
                                System.out.println(objects.size());
                                for (ParseObject object : objects) {
                                    courses.add(Course.getInstance(object));
                                }
                                System.out.println(courses.size());
                                // Searching through BookExchange posts
                                // by taking each input into consideration:
                                ParseQuery<ParseObject> bookExchangePostQuery = BookExchange.getQuery();
                                if (checkTitle)
                                    bookExchangePostQuery.whereContains(BookExchange.KEY_TITLE, title);
                                bookExchangePostQuery.whereContainedIn(BookExchange.KEY_COURSE, courses);
                                if (checkMinPrice)
                                    bookExchangePostQuery.whereGreaterThanOrEqualTo(BookExchange.KEY_PRICE, Integer.parseInt(minPrice));
                                if (checkMaxPrice)
                                    bookExchangePostQuery.whereLessThanOrEqualTo(BookExchange.KEY_PRICE, Integer.parseInt(maxPrice));
                                if (checkCondition)
                                    bookExchangePostQuery.whereEqualTo(BookExchange.KEY_CONDITION, conditionMap.get(conditionStr));
                                bookExchangePostQuery.findInBackground(new FindCallback<ParseObject>() {
                                    @Override
                                    public void done(List<ParseObject> objects, ParseException e) {
                                        if (e == null) {
                                            List<PostCard> postCards = new ArrayList<>();
                                            System.out.println(objects.size());
                                            for (ParseObject object : objects) {
                                                Post post = Post.getInstance((ParseObject) BookExchange.getInstance(object).getPost());
                                                postCards.add(new PostCard(getActivity(), post));
                                            }
                                            PostFeedFragment postFeedFragment = PostFeedFragment.newInstance(postCards);
                                            mProgressBar.setVisibility(View.GONE);
                                            FragmentManager fragmentManager = getFragmentManager();
                                            fragmentManager.beginTransaction()
                                                    .replace(R.id.advanced_search_container, postFeedFragment)
                                                    .commit();
                                        }
                                    }
                                });
                            }
                        }
                    });
                } else {
                    // Searching through BookExchange posts
                    // by taking each input into consideration:
                    ParseQuery<ParseObject> bookExchangePostQuery = BookExchange.getQuery();
                    if (checkTitle)
                        bookExchangePostQuery.whereContains(BookExchange.KEY_TITLE, title);
                    if (checkMinPrice)
                        bookExchangePostQuery.whereGreaterThanOrEqualTo(BookExchange.KEY_PRICE, Integer.parseInt(minPrice));
                    if (checkMaxPrice)
                        bookExchangePostQuery.whereLessThanOrEqualTo(BookExchange.KEY_PRICE, Integer.parseInt(maxPrice));
                    if (checkCondition)
                        bookExchangePostQuery.whereEqualTo(BookExchange.KEY_CONDITION, conditionMap.get(conditionStr));
                    bookExchangePostQuery.findInBackground(new FindCallback<ParseObject>() {
                        @Override
                        public void done(List<ParseObject> objects, ParseException e) {
                            if (e == null) {
                                List<PostCard> postCards = new ArrayList<>();
                                System.out.println(objects.size());
                                for (ParseObject object : objects) {
                                    Post post = Post.getInstance((ParseObject) BookExchange.getInstance(object).getPost());
                                    postCards.add(new PostCard(getActivity(), post));
                                }
                                PostFeedFragment postFeedFragment = PostFeedFragment.newInstance(postCards);
                                mProgressBar.setVisibility(View.GONE);
                                FragmentManager fragmentManager = getFragmentManager();
                                fragmentManager.beginTransaction()
                                        .replace(R.id.advanced_search_container, postFeedFragment)
                                        .commit();
                            }
                        }
                    });
                }
            }
        });

        return actionSearchView;
    }

}

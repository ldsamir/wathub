package webb8.wathub.util;

import android.app.Activity;
import android.view.View;
import android.widget.TextView;

import com.parse.ParseException;

import webb8.wathub.R;
import webb8.wathub.models.BookConditions;
import webb8.wathub.models.BookExchange;
import webb8.wathub.models.Course;

/**
 * Created by mismayil on 23/02/16.
 */
public class BookExchangeCard extends PostCard {

    private BookExchange mBookExchange;
    private Course mCourse;

    public BookExchangeCard(Activity activity, BookExchange bookExchange) {
        mActivity = activity;
        mBookExchange = bookExchange;
        mCourse = mBookExchange.getCourse();
        refresh();
    }

    @Override
    public View getView() {
        View view = mActivity.getLayoutInflater().inflate(R.layout.card_bookexchange, null, false);
        TextView bookCourse = (TextView) view.findViewById(R.id.book_exchange_course);
        TextView bookTitle = (TextView) view.findViewById(R.id.book_exchange_bookTitle);
        TextView bookCondition = (TextView) view.findViewById(R.id.book_exchange_bookCondition);
        TextView bookPrice = (TextView) view.findViewById(R.id.book_exchange_bookPrice);
        bookCourse.setText(mCourse.getSubject() + mCourse.getNumber());
        bookTitle.setText(mBookExchange.getTitle());
        int condition = mBookExchange.getCondition();
        if (condition == BookConditions.BAD.getId()) bookCondition.setText(mActivity.getString(BookConditions.BAD.getNameId()));
        if (condition == BookConditions.MODERATE.getId()) bookCondition.setText(mActivity.getString(BookConditions.MODERATE.getNameId()));
        if (condition == BookConditions.GOOD.getId()) bookCondition.setText(mActivity.getString(BookConditions.GOOD.getNameId()));
        if (condition == BookConditions.EXCELLENT.getId()) bookCondition.setText(mActivity.getString(BookConditions.EXCELLENT.getNameId()));
        bookPrice.setText(String.valueOf(mBookExchange.getPrice()));

        return view;
    }

    public void refresh() {
        try {
            mCourse.fetch();
        } catch (ParseException e) {

        }
    }
}

package webb8.wathub.models;

import com.parse.ParseClassName;
import com.parse.ParseObject;
import com.parse.ParseQuery;

/**
 * Created by mismayil on 04/02/16.
 */

@ParseClassName("BookExchange")
public class BookExchange extends ParseObject implements Parsable {

    // class columns
    public static final String KEY_CLASSNAME = "BookExchange";
    public static final String KEY_TITLE = "title";
    public static final String KEY_COURSE = "course";
    public static final String KEY_POST = "post";
    public static final String KEY_PRICE = "price";
    public static final String KEY_CONDITION = "condition";

    public String getTitle() {
        return getString(KEY_TITLE);
    }

    public void setTitle(String title) {
        put(KEY_TITLE, title);
    }

    public Course getCourse() {
        ParseObject object = getParseObject(KEY_COURSE);
        if (object != null) return ParseObject.createWithoutData(Course.class, object.getObjectId());
        return null;
    }

    public void setCourse(Course course) {
        put(KEY_COURSE, course);
    }

    public Post getPost() {
        ParseObject object = getParseObject(KEY_POST);
        if (object != null) return ParseObject.createWithoutData(Post.class, object.getObjectId());
        return null;
    }

    public void setPost(Post post) {
        put(KEY_POST, post);
    }

    public double getPrice() {
        return getDouble(KEY_PRICE);
    }

    public void setPrice(double price) {
        put(KEY_PRICE, price);
    }

    public int getCondition() {
        return getInt(KEY_CONDITION);
    }

    public void setCondition(int condition) {
        put(KEY_CONDITION, condition);
    }

    public static ParseQuery<ParseObject> getQuery() {
        return ParseQuery.getQuery(KEY_CLASSNAME);
    }

    public static BookExchange getInstance(ParseObject object) {
        if (object != null) return ParseObject.createWithoutData(BookExchange.class, object.getObjectId());
        return null;
    }
}

package webb8.wathub.models;

import com.parse.ParseClassName;
import com.parse.ParseObject;
import com.parse.ParseQuery;

/**
 * Created by mismayil on 04/02/16.
 */

@ParseClassName("BookExchange")
public class BookExchange extends ParseObject {

    public String getTitle() {
        return getString("title");
    }

    public void setTitle(String title) {
        put("title", title);
    }

    public Course getCourse() {
        return ParseObject.createWithoutData(Course.class, getParseObject("course").getObjectId());
    }

    public void setCourse(Course course) {
        put("course", course);
    }

    public Post getPost() {
        return ParseObject.createWithoutData(Post.class, getParseObject("post").getObjectId());
    }

    public void setPost(Post post) {
        put("post", post);
    }

    public double getPrice() {
        return getDouble("price");
    }

    public void setPrice(double price) {
        put("price", price);
    }

    public int getCondition() {
        return getInt("condition");
    }

    public void setCondition(int condition) {
        put("condition", condition);
    }

    public static ParseQuery<ParseObject> getQuery() {
        return ParseQuery.getQuery("BookExchange");
    }

    public static BookExchange getInstance(ParseObject object) {
        return ParseObject.createWithoutData(BookExchange.class, object.getObjectId());
    }
}

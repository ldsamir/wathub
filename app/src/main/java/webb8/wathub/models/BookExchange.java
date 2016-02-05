package webb8.wathub.models;

import com.parse.ParseClassName;
import com.parse.ParseObject;

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

    public ParseObject getCourse() {
        return getParseObject("course");
    }

    public void setCourse(ParseObject course) {
        put("course", course);
    }

    public ParseObject getPost() {
        return getParseObject("post");
    }

    public void setPost(ParseObject post) {
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
}

package webb8.wathub.models;

import com.parse.ParseClassName;
import com.parse.ParseObject;
import com.parse.ParseQuery;

/**
 * Created by mismayil on 04/02/16.
 */

@ParseClassName("BookExchange")
public class BookExchange extends ParseObject {
    private ParseObject object;

    public BookExchange(ParseObject object) {
        this.object = object;
    }

    public BookExchange() {
        this.object = this;
    }

    public String getTitle() {
        return object.getString("title");
    }

    public void setTitle(String title) {
        object.put("title", title);
    }

    public ParseObject getCourse() {
        return object.getParseObject("course");
    }

    public void setCourse(ParseObject course) {
        object.put("course", course);
    }

    public ParseObject getPost() {
        return object.getParseObject("post");
    }

    public void setPost(ParseObject post) {
        object.put("post", post);
    }

    public double getPrice() {
        return object.getDouble("price");
    }

    public void setPrice(double price) {
        object.put("price", price);
    }

    public int getCondition() {
        return object.getInt("condition");
    }

    public void setCondition(int condition) {
        object.put("condition", condition);
    }

    public static ParseQuery<ParseObject> getQuery() {
        return ParseQuery.getQuery("BookExchange");
    }
}

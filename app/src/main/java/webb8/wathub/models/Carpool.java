package webb8.wathub.models;

import com.parse.ParseClassName;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import org.json.JSONArray;

import java.util.Date;

/**
 * Created by mismayil on 04/02/16.
 */

@ParseClassName("Carpool")
public class Carpool extends ParseObject {
    public String getFrom() {
        return getString("from");
    }

    public void setFrom(String from) {
        put("from", from);
    }

    public String getTo() {
        return getString("to");
    }

    public void setTo(String to) {
        put("to", to);
    }

    public Date getWhen() {
        return getDate("when");
    }

    public void setWhen(Date when) {
        put("when", when);
    }

    public int getMaxPassengers() {
        return getInt("maxPassengers");
    }

    public void setMaxPassengers(int maxPassengers) {
        put("maxPassengers", maxPassengers);
    }

    public Post getPost() {
        ParseObject object = getParseObject("post");
        if (object != null) return ParseObject.createWithoutData(Post.class, object.getObjectId());
        return null;
    }

    public void setPost(Post post) {
        put("post", post);
    }

    public JSONArray getPassengers() {
        return getJSONArray("passengers");
    }

    public void addPassenger(ParseUser passenger) {
        addUnique("passengers", passenger);
    }

    public double getPrice() {
        return getDouble("price");
    }

    public void setPrice(double price) {
        put("price", price);
    }

    public static ParseQuery<ParseObject> getQuery() {
        return ParseQuery.getQuery("Carpool");
    }

    public static Carpool getInstance(ParseObject object) {
        if (object != null) return ParseObject.createWithoutData(Carpool.class, object.getObjectId());
        return null;
    }

}

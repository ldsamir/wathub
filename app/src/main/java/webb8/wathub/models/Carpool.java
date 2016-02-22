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
public class Carpool extends ParseObject implements Parsable {

    // class columns
    public static final String KEY_CLASSNAME = "Carpool";
    public static final String KEY_FROM = "from";
    public static final String KEY_TO = "to";
    public static final String KEY_WHEN = "when";
    public static final String KEY_MAX_PASSENGERS = "maxPassengers";
    public static final String KEY_POST = "post";
    public static final String KEY_PASSENGERS = "passengers";
    public static final String KEY_PRICE = "price";

    public String getFrom() {
        return getString(KEY_FROM);
    }

    public void setFrom(String from) {
        put(KEY_FROM, from);
    }

    public String getTo() {
        return getString(KEY_TO);
    }

    public void setTo(String to) {
        put(KEY_TO, to);
    }

    public Date getWhen() {
        return getDate(KEY_WHEN);
    }

    public void setWhen(Date when) {
        put(KEY_WHEN, when);
    }

    public int getMaxPassengers() {
        return getInt(KEY_MAX_PASSENGERS);
    }

    public void setMaxPassengers(int maxPassengers) {
        put(KEY_MAX_PASSENGERS, maxPassengers);
    }

    public Post getPost() {
        ParseObject object = getParseObject(KEY_POST);
        if (object != null) return ParseObject.createWithoutData(Post.class, object.getObjectId());
        return null;
    }

    public void setPost(Post post) {
        put(KEY_POST, post);
    }

    public JSONArray getPassengers() {
        return getJSONArray(KEY_PASSENGERS);
    }

    public void addPassenger(ParseUser passenger) {
        addUnique(KEY_PASSENGERS, passenger);
    }

    public double getPrice() {
        return getDouble(KEY_PRICE);
    }

    public void setPrice(double price) {
        put(KEY_PRICE, price);
    }

    public static ParseQuery<ParseObject> getQuery() {
        return ParseQuery.getQuery(KEY_CLASSNAME);
    }

    // convert ParseObject to Carpool
    public static Carpool getInstance(ParseObject object) {
        if (object != null) return ParseObject.createWithoutData(Carpool.class, object.getObjectId());
        return null;
    }

}

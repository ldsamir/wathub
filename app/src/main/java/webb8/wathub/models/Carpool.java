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
    private ParseObject object;

    public Carpool(ParseObject object) {
        this.object = object;
    }

    public Carpool() {
        this.object = this;
    }

    public String getFrom() {
        return object.getString("from");
    }

    public void setFrom(String from) {
        object.put("from", from);
    }

    public String getTo() {
        return object.getString("to");
    }

    public void setTo(String to) {
        object.put("to", to);
    }

    public Date getWhen() {
        return object.getDate("when");
    }

    public void setWhen(Date when) {
        object.put("when", when);
    }

    public int getMaxPassengers() {
        return object.getInt("maxPassengers");
    }

    public void setMaxPassengers(int maxPassengers) {
        object.put("maxPassengers", maxPassengers);
    }

    public ParseObject getPost() {
        return object.getParseObject("post");
    }

    public void setPost(ParseObject post) {
        object.put("post", post);
    }

    public JSONArray getPassengers() {
        return object.getJSONArray("passengers");
    }

    public void addPassenger(ParseUser passenger) {
        object.addUnique("passengers", passenger);
    }

    public double getPrice() {
        return object.getDouble("price");
    }

    public void setPrice(double price) {
        object.put("price", price);
    }

    public static ParseQuery<ParseObject> getQuery() {
        return ParseQuery.getQuery("Carpool");
    }

}

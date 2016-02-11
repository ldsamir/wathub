package webb8.wathub.models;

import com.parse.ParseClassName;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

/**
 * Created by mismayil on 04/02/16.
 */

@ParseClassName("Message")
public class Message extends ParseObject {
    private ParseObject object;

    public Message(ParseObject object) {
        this.object = object;
    }

    public Message() {
        this.object = this;
    }

    public ParseUser getFrom() {
        return object.getParseUser("from");
    }

    public void setFrom(ParseUser from) {
        object.put("from", from);
    }

    public ParseUser getTo() {
        return object.getParseUser("to");
    }

    public void setTo(ParseUser to) {
        object.put("to", to);
    }

    public String getMessage() {
        return object.getString("message");
    }

    public void setMessage(String message) {
        object.put("message", message);
    }

    public static ParseQuery<ParseObject> getQuery() {
        return ParseQuery.getQuery("Message");
    }
}


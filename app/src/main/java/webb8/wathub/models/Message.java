package webb8.wathub.models;

import com.parse.ParseClassName;
import com.parse.ParseObject;
import com.parse.ParseUser;

/**
 * Created by mismayil on 04/02/16.
 */

@ParseClassName("Message")
public class Message extends ParseObject {

    public ParseUser getFrom() {
        return getParseUser("from");
    }

    public void setFrom(ParseUser from) {
        put("from", from);
    }

    public ParseUser getTo() {
        return getParseUser("to");
    }

    public void setTo(ParseUser to) {
        put("to", to);
    }

    public String getMessage() {
        return getString("message");
    }

    public void setMessage(String message) {
        put("message", message);
    }
}


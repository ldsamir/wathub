package webb8.wathub.models;

import com.parse.ParseClassName;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

/**
 * Created by mismayil on 04/02/16.
 */

@ParseClassName("Message")
public class Message extends ParseObject implements Parsable {

    // class columns
    public static final String KEY_CLASSNAME = "Message";
    public static final String KEY_FROM = "from";
    public static final String KEY_TO = "to";
    public static final String KEY_MESSAGE = "message";

    public ParseUser getFrom() {
        return getParseUser(KEY_FROM);
    }

    public void setFrom(ParseUser from) {
        put(KEY_FROM, from);
    }

    public ParseUser getTo() {
        return getParseUser(KEY_TO);
    }

    public void setTo(ParseUser to) {
        put(KEY_TO, to);
    }

    public String getMessage() {
        return getString(KEY_MESSAGE);
    }

    public void setMessage(String message) {
        put(KEY_MESSAGE, message);
    }

    public static ParseQuery<ParseObject> getQuery() {
        return ParseQuery.getQuery(KEY_CLASSNAME);
    }

    // convert ParseObject to Message
    public static Message getInstance(ParseObject object) {
        if (object != null) return ParseObject.createWithoutData(Message.class, object.getObjectId());
        return null;
    }
}


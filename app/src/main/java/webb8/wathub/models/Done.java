package webb8.wathub.models;

import com.parse.ParseClassName;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

/**
 * Created by mismayil on 10/02/16.
 */
@ParseClassName("Done")
public class Done extends ParseObject implements Parsable {

    // class columns
    public static final String KEY_CLASSNAME = "Done";
    public static final String KEY_USER = "user";
    public static final String KEY_POST = "post";

    public ParseUser getUser() {
        return getParseUser(KEY_USER);
    }

    public void setUser(ParseUser user) {
        put(KEY_USER, user);
    }

    public Post getPost() {
        ParseObject object = getParseObject(KEY_POST);
        if (object != null) return ParseObject.createWithoutData(Post.class, object.getObjectId());
        return null;
    }

    public void setPost(Post post) {
        put(KEY_POST, post);
    }

    public static ParseQuery<ParseObject> getQuery() {
        return ParseQuery.getQuery(KEY_CLASSNAME);
    }

    // convert ParseObject to Done
    public static Done getInstance(ParseObject object) {
        if (object != null) return ParseObject.createWithoutData(Done.class, object.getObjectId());
        return null;
    }
}

package webb8.wathub.models;

import com.parse.ParseClassName;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

/**
 * Created by mismayil on 10/02/16.
 */
@ParseClassName("Done")
public class Done extends ParseObject {
    private ParseObject object;

    public Done(ParseObject object) {
        this.object = object;
    }

    public Done() {
        this.object = this;
    }
    public ParseUser getUser() {
        return object.getParseUser("user");
    }

    public void setUser(ParseUser user) {
        object.put("user", user);
    }

    public ParseObject getPost() {
        return object.getParseObject("post");
    }

    public void setPost(ParseObject post) {
        object.put("post", post);
    }

    public static ParseQuery<ParseObject> getQuery() {
        return ParseQuery.getQuery("Done");
    }
}

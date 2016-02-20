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

    public ParseUser getUser() {
        return getParseUser("user");
    }

    public void setUser(ParseUser user) {
        put("user", user);
    }

    public Post getPost() {
        return ParseObject.createWithoutData(Post.class, getParseObject("post").getObjectId());
    }

    public void setPost(Post post) {
        put("post", post);
    }

    public static ParseQuery<ParseObject> getQuery() {
        return ParseQuery.getQuery("Done");
    }

    public static Done getInstance(ParseObject object) {
        return ParseObject.createWithoutData(Done.class, object.getObjectId());
    }
}

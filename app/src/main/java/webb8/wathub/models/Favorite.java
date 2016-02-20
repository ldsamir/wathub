package webb8.wathub.models;

import com.parse.ParseClassName;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

/**
 * Created by mismayil on 04/02/16.
 */

@ParseClassName("Favorite")
public class Favorite extends ParseObject {

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
        return ParseQuery.getQuery("Favorite");
    }

    public static Favorite getInstance(ParseObject object) {
        return ParseObject.createWithoutData(Favorite.class, object.getObjectId());
    }
}

package webb8.wathub.models;

import com.parse.ParseClassName;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

/**
 * Created by mismayil on 04/02/16.
 */

@ParseClassName("Post")
public class Post extends ParseObject {

    public ParseUser getUser() {
        return getParseUser("user");
    }

    public void setUser(ParseUser user) {
        put("user", user);
    }

    public PostType getPostType() {
        ParseObject object =  getParseObject("postType");
        if (object != null) return ParseObject.createWithoutData(PostType.class, object.getObjectId());
        return null;
    }

    public void setPostType(PostType postType) {
        put("postType", postType);
    }

    public int getState() {
        return getInt("user");
    }

    public void setState(int state) {
        put("state", state);
    }

    public String getContent(){
        return getString("content");
    }

    public static ParseQuery<ParseObject> getQuery() {
        return ParseQuery.getQuery("Post");
    }

    public static Post getInstance(ParseObject object) {
        if (object != null) return ParseObject.createWithoutData(Post.class, object.getObjectId());
        return null;
    }

}

package webb8.wathub.models;

import com.parse.ParseClassName;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

/**
 * Created by mismayil on 04/02/16.
 */

@ParseClassName("Post")
public class Post extends ParseObject implements Parsable {

    // class columns
    public static final String KEY_CLASSNAME = "Post";
    public static final String KEY_USER = "user";
    public static final String KEY_POST_TYPE = "postType";
    public static final String KEY_STATE = "state";
    public static final String KEY_CONTENT = "content";

    public ParseUser getUser() {
        return getParseUser(KEY_USER);
    }

    public void setUser(ParseUser user) {
        put(KEY_USER, user);
    }

    public PostType getPostType() {
        ParseObject object =  getParseObject(KEY_POST_TYPE);
        if (object != null) return ParseObject.createWithoutData(PostType.class, object.getObjectId());
        return null;
    }

    public void setPostType(PostType postType) {
        put(KEY_POST_TYPE, postType);
    }

    public int getState() {
        return getInt(KEY_STATE);
    }

    public void setState(int state) {
        put(KEY_STATE, state);
    }

    public String getContent(){
        return getString(KEY_CONTENT);
    }

    public static ParseQuery<ParseObject> getQuery() {
        return ParseQuery.getQuery(KEY_CLASSNAME);
    }

    // convert ParseObject to Post
    public static Post getInstance(ParseObject object) {
        if (object != null) return ParseObject.createWithoutData(Post.class, object.getObjectId());
        return null;
    }

}

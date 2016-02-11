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
    private ParseObject object;

    public Post(ParseObject object) {
        this.object = object;
    }

    public Post() {
        this.object = this;
    }

    public ParseUser getUser() {
        return object.getParseUser("user");
    }

    public void setUser(ParseUser user) {
        object.put("user", user);
    }

    public ParseObject getPostType() {
        return object.getParseObject("postType");
    }

    public void setPostType(ParseObject postType) {
        object.put("postType", postType);
    }

    public int getState() {
        return object.getInt("user");
    }

    public void setState(int state) {
        object.put("state", state);
    }

    public String getContent(){
        return object.getString("content");
    }

    public void setContent(String content) {
        object.put("content", content);
    }

    public static ParseQuery<ParseObject> getQuery() {
        return ParseQuery.getQuery("Post");
    }

}

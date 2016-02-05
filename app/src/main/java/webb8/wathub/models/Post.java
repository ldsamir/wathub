package webb8.wathub.models;

import com.parse.ParseClassName;
import com.parse.ParseObject;
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

    public ParseObject getPostType() {
        return getParseObject("postType");
    }

    public void setPostType(ParseObject postType) {
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

    public void setContent(String content) {
        put("content", content);
    }

}

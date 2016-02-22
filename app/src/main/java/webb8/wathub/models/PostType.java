package webb8.wathub.models;

import com.parse.ParseClassName;
import com.parse.ParseObject;
import com.parse.ParseQuery;

/**
 * Created by mismayil on 04/02/16.
 */

@ParseClassName("PostType")
public class PostType extends ParseObject implements Parsable {

    // class columns
    public static final String KEY_CLASSNAME = "PostType";
    public static final String KEY_TYPENAME = "typeName";

    public String getTypeName() {
        return getString(KEY_TYPENAME);
    }

    public void setTypeName(String typeName) {
        put(KEY_TYPENAME, typeName);
    }

    public static ParseQuery<ParseObject> getQuery() {
        return ParseQuery.getQuery(KEY_CLASSNAME);
    }

    // convert ParseObject to PostType
    public static PostType getInstance(ParseObject object) {
        if (object != null) return ParseObject.createWithoutData(PostType.class, object.getObjectId());
        return null;
    }
}

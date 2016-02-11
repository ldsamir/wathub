package webb8.wathub.models;

import com.parse.ParseClassName;
import com.parse.ParseObject;
import com.parse.ParseQuery;

/**
 * Created by mismayil on 04/02/16.
 */

@ParseClassName("PostType")
public class PostType extends ParseObject {
    private ParseObject object;

    public PostType(ParseObject object) {
        this.object = object;
    }

    public PostType() {
        this.object = this;
    }

    public String getTypeName() {
        return object.getString("typeName");
    }

    public void setTypeName(String typeName) {
        object.put("typeName", typeName);
    }

    public static ParseQuery<ParseObject> getQuery() {
        return ParseQuery.getQuery("PostType");
    }
}

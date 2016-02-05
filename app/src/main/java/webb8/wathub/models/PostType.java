package webb8.wathub.models;

import com.parse.ParseClassName;
import com.parse.ParseObject;

/**
 * Created by mismayil on 04/02/16.
 */

@ParseClassName("PostType")
public class PostType extends ParseObject {

    public String getTypeName() {
        return getString("typeName");
    }

    public void setTypeName(String typeName) {
        put("typeName", typeName);
    }
}

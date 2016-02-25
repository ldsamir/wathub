package webb8.wathub.models;

import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.List;

/**
 * Created by mismayil on 25/02/16.
 */
public enum PostTypes {
    BOOK_EXCHANGE(BookExchange.KEY_CLASSNAME),
    GROUP_STUDY(GroupStudy.KEY_CLASSNAME),
    CARPOOL(Carpool.KEY_CLASSNAME);

    private PostType type;

    PostTypes(String typeName) {
        ParseQuery<ParseObject> typeQuery = PostType.getQuery();
        typeQuery.whereEqualTo(PostType.KEY_TYPENAME, typeName);

        try {
            List<ParseObject> objects = typeQuery.find();
            if (objects != null && objects.size() > 0) {
                type = PostType.getInstance(objects.get(0));
            } else {
                type = null;
            }
        } catch (ParseException e) {

        }
    }

    public PostType getType() { return type; }
}

package webb8.wathub.models;

import com.parse.ParseClassName;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import org.json.JSONArray;
import java.util.Date;

/**
 * Created by mismayil on 04/02/16.
 */
@ParseClassName("Profile")
public class Profile extends ParseObject {

    public String getFirstName() {
        return getString("firstName");
    }

    public void setFirstName(String firstName) {
        put("firstName", firstName);
    }

    public String getLastName() {
        return getString("lastName");
    }

    public void setLastName(String lastName) {
        put("lastName", lastName);
    }

    public Date getBirthday() {
        return getDate("birthday");
    }

    public void setBirthday(Date birthday) {
        put("birthday", birthday);
    }

    public String getPhone() {
        return getString("phone");
    }

    public void setPhone(String phone) {
        put("phone", phone);
    }

    public String getMajor() {
        return getString("major");
    }

    public void setMajor(String major) {
        put("major", major);
    }

    public ParseFile getAvatar() {
        return getParseFile("avatar");
    }

    public void setAvatar(ParseFile avatar) {
        put("avatar", avatar);
    }

    public JSONArray getContactLinks() {
        return getJSONArray("contactLinks");
    }

    public void addContactLink(String contactLink) {
        addUnique("contactLinks", contactLink);
    }

    public ParseUser getOwner() {
        return (ParseUser) get("owner");
    }

    public void setOwner(ParseUser owner) {
        put("owner", owner);
    }

    public static ParseQuery<ParseObject> getQuery() {
        return ParseQuery.getQuery("Profile");
    }

    public static Profile getInstance(ParseObject object) {
        if (object != null) return ParseObject.createWithoutData(Profile.class, object.getObjectId());
        return null;
    }
}

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
public class Profile extends ParseObject implements Parsable {

    // class columns
    public static final String KEY_CLASSNAME = "Profile";
    public static final String KEY_FIRST_NAME = "firstName";
    public static final String KEY_LAST_NAME = "lastName";
    public static final String KEY_BIRTHDAY = "birthday";
    public static final String KEY_PHONE = "phone";
    public static final String KEY_MAJOR = "major";
    public static final String KEY_AVATAR = "avatar";
    public static final String KEY_CONTACT_LINKS = "contactLinks";
    public static final String KEY_OWNER = "owner";

    public String getFirstName() {
        return getString(KEY_FIRST_NAME);
    }

    public void setFirstName(String firstName) {
        put(KEY_FIRST_NAME, firstName);
    }

    public String getLastName() {
        return getString(KEY_LAST_NAME);
    }

    public void setLastName(String lastName) {
        put(KEY_LAST_NAME, lastName);
    }

    public Date getBirthday() {
        return getDate(KEY_BIRTHDAY);
    }

    public void setBirthday(Date birthday) {
        put(KEY_BIRTHDAY, birthday);
    }

    public String getPhone() {
        return getString(KEY_PHONE);
    }

    public void setPhone(String phone) {
        put(KEY_PHONE, phone);
    }

    public String getMajor() {
        return getString(KEY_MAJOR);
    }

    public void setMajor(String major) {
        put(KEY_MAJOR, major);
    }

    public ParseFile getAvatar() {
        return getParseFile(KEY_AVATAR);
    }

    public void setAvatar(ParseFile avatar) {
        put(KEY_AVATAR, avatar);
    }

    public JSONArray getContactLinks() {
        return getJSONArray(KEY_CONTACT_LINKS);
    }

    public void setContactLinks(JSONArray contactLinks) {
        put(KEY_CONTACT_LINKS, contactLinks);
    }

    public void addContactLink(String contactLink) {
        addUnique(KEY_CONTACT_LINKS, contactLink);
    }

    public ParseUser getOwner() {
        return (ParseUser) get(KEY_OWNER);
    }

    public void setOwner(ParseUser owner) {
        put(KEY_OWNER, owner);
    }

    public static ParseQuery<ParseObject> getQuery() {
        return ParseQuery.getQuery(KEY_CLASSNAME);
    }

    // convert ParseObject to Profile
    public static Profile getInstance(ParseObject object) {
        if (object != null) return ParseObject.createWithoutData(Profile.class, object.getObjectId());
        return null;
    }
}

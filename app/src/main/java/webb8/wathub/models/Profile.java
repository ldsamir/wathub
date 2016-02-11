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
    private ParseObject object;

    public Profile(ParseObject object) {
        this.object = object;
    }

    public Profile() {
        this.object = this;
    }

    public String getFirstName() {
        return object.getString("firstName");
    }

    public void setFirstName(String firstName) {
        object.put("firstName", firstName);
    }

    public String getLastName() {
        return object.getString("lastName");
    }

    public void setLastName(String lastName) {
        object.put("lastName", lastName);
    }

    public Date getBirthday() {
        return object.getDate("birthday");
    }

    public void setBirthday(Date birthday) {
        object.put("birthday", birthday);
    }

    public String getPhone() {
        return object.getString("phone");
    }

    public void setPhone(String phone) {
        object.put("phone", phone);
    }

    public String getMajor() {
        return object.getString("major");
    }

    public void setMajor(String major) {
        object.put("major", major);
    }

    public ParseFile getAvatar() {
        return object.getParseFile("avatar");
    }

    public void setAvatar(ParseFile avatar) {
        object.put("avatar", avatar);
    }

    public JSONArray getContactLinks() {
        return object.getJSONArray("contactLinks");
    }

    public void addContactLink(String contactLink) {
        object.addUnique("contactLinks", contactLink);
    }

    public ParseUser getOwner() {
        return (ParseUser) object.get("owner");
    }

    public void setOwner(ParseUser owner) {
        object.put("owner", owner);
    }

    public static ParseQuery<ParseObject> getQuery() {
        return ParseQuery.getQuery("Profile");
    }
}

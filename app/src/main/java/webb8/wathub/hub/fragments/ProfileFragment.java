package webb8.wathub.hub.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

import webb8.wathub.R;
import webb8.wathub.models.Profile;

/**
 * Created by mismayil on 23/02/16.
 */
public class ProfileFragment extends HubFragment {

    public ProfileFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup viewContainer, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_profile, viewContainer, false);
        ParseQuery<ParseObject> query = Profile.getQuery();
        ParseUser user = ParseUser.getCurrentUser();
        query.whereEqualTo("owner", user);
        try {
            List<ParseObject> objects = query.find();
            Profile cur_user = Profile.getInstance(objects.get(0));
            EditText firstNameView = (EditText) rootView.findViewById(R.id.firstName);
            EditText lastNameView = (EditText) rootView.findViewById(R.id.lastName);
            EditText birthDayView = (EditText) rootView.findViewById(R.id.birthDay);
            EditText phoneView = (EditText) rootView.findViewById(R.id.phoneNumber);
            firstNameView.setText(cur_user.getFirstName());
            lastNameView.setText(cur_user.getLastName());
            SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy HH:mm a", Locale.CANADA);
            if(cur_user.getBirthday() != null){
                birthDayView.setText(format.format(cur_user.getBirthday()));
            }
            phoneView.setText(cur_user.getPhone());
        } catch (ParseException e) {

        }

        return rootView;
    }
}

package webb8.wathub.hub.fragments.navigation;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import webb8.wathub.R;
import webb8.wathub.hub.fragments.HubFragment;
import webb8.wathub.models.Profile;

/**
 * Created by mismayil on 23/02/16.
 */
public class ProfileFragment extends HubFragment {

    private EditText firstNameView;
    private EditText lastNameView;
    private EditText birthDayView;
    private EditText phoneView;

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
            firstNameView = (EditText) rootView.findViewById(R.id.firstName);
            lastNameView = (EditText) rootView.findViewById(R.id.lastName);
            birthDayView = (EditText) rootView.findViewById(R.id.birthDay);
            phoneView = (EditText) rootView.findViewById(R.id.phoneNumber);
            Button saveButton = (Button) rootView.findViewById(R.id.saveButton);
            saveButton.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    save();
                }
            });
            firstNameView.setText(cur_user.getFirstName());
            lastNameView.setText(cur_user.getLastName());
            SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy", Locale.CANADA);
            if(cur_user.getBirthday() != null){
                birthDayView.setText(format.format(cur_user.getBirthday()));
            }
            phoneView.setText(cur_user.getPhone());
        } catch (ParseException e) {

        }

        return rootView;
    }
    private void save(){
        final String firstName = firstNameView.getText().toString();
        final String lastName = lastNameView.getText().toString();
        final String phone = phoneView.getText().toString();
        final String birthDay_string = birthDayView.getText().toString();
        Date birthDay;

        ParseUser user = ParseUser.getCurrentUser();
        ParseQuery<ParseObject> query = Profile.getQuery();
        query.whereEqualTo("owner", user);
        try{
            DateFormat df = new SimpleDateFormat("dd.MM.yyyy HH:mm a", Locale.CANADA);

            List<ParseObject> objects = query.find();
            Profile userProfile = Profile.getInstance(objects.get(0));
            userProfile.setFirstName(firstName);
            userProfile.setLastName(lastName);
            userProfile.setPhone(phone);
            try {
                birthDay = df.parse(birthDay_string);
                userProfile.setBirthday(birthDay);
            }catch(Exception e) {

            }
            userProfile.saveInBackground(new SaveCallback() {
                @Override
                public void done(ParseException e) {
                    if(e == null){
                        Toast.makeText(getActivity(), R.string.profile_save, Toast.LENGTH_SHORT).show();
                    }
                }
            });
        } catch(ParseException e){
        }

    }

}

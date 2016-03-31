package webb8.wathub.hub.fragments.navigation;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcel;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.ProgressCallback;
import com.parse.SaveCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
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

    private static final int READ_REQUEST_CODE = 42;

    private Profile mProfile;
    private EditText mFirstNameView;
    private EditText mLastNameView;
    private EditText mBirthdayView;
    private EditText mPhoneView;
    private EditText mMajorView;
    private LinearLayout mContactLinksView;
    private Button mLinkAddBtnView;
    private Button mSaveBtnView;
    private ImageView mAvatarView;
    private Uri mAvatar;

    public ProfileFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup viewContainer, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_profile, viewContainer, false);

        mFirstNameView = (EditText) rootView.findViewById(R.id.profile_firstName);
        mLastNameView = (EditText) rootView.findViewById(R.id.profile_lastName);
        mBirthdayView = (EditText) rootView.findViewById(R.id.profile_birthday);
        mPhoneView = (EditText) rootView.findViewById(R.id.profile_phone);
        mMajorView = (EditText) rootView.findViewById(R.id.profile_major);
        mContactLinksView = (LinearLayout) rootView.findViewById(R.id.profile_contact_links);
        mAvatarView = (ImageView) rootView.findViewById(R.id.profile_photo);
        mLinkAddBtnView = (Button) rootView.findViewById(R.id.profile_btn_add_contact_link);
        mSaveBtnView = (Button) rootView.findViewById(R.id.profile_btn_save);

        ParseQuery<ParseObject> query = Profile.getQuery();
        ParseUser user = ParseUser.getCurrentUser();
        query.whereEqualTo(Profile.KEY_OWNER, user);

        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                if (e == null) {
                    mProfile = Profile.getInstance(objects.get(0));
                    mFirstNameView.setText(mProfile.getFirstName());
                    mLastNameView.setText(mProfile.getLastName());
                    SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy", Locale.CANADA);

                    if (mProfile.getBirthday() != null) {
                        mBirthdayView.setText(format.format(mProfile.getBirthday()));
                    }

                    mPhoneView.setText(mProfile.getPhone());
                    mMajorView.setText(mProfile.getMajor());

                    JSONArray contactLinks = mProfile.getContactLinks();

                    try {
                        for (int i = 0; i < contactLinks.length(); i++) {
                            String contactLink = contactLinks.get(i).toString();
                            View contactLinkView = getContactLinkView();
                            EditText editText = (EditText) contactLinkView.findViewWithTag(mHubActivity.getString(R.string.profile_contact_link_tag));
                            editText.setText(contactLink);
                            mContactLinksView.addView(contactLinkView);
                        }
                    } catch (JSONException je) {

                    }

                    try {
                        ParseFile avatarFile = mProfile.getAvatar();
                        if (avatarFile != null) {
                            Uri avatar = Uri.fromFile(avatarFile.getFile());
                            mAvatarView.setImageURI(avatar);
                        }
                    } catch (ParseException pe) {

                    }
                } else {
                    Toast.makeText(mHubActivity.getApplicationContext(), R.string.error_loading_profile, Toast.LENGTH_SHORT).show();
                }
            }
        });

        mBirthdayView.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    DatePickerFragment fragment = new DatePickerFragment();
                    fragment.show(getFragmentManager(), "birthday");
                }
            }
        });

        mLinkAddBtnView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mContactLinksView.addView(getContactLinkView());
            }
        });

        mAvatarView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadAvatar();
            }
        });

        mSaveBtnView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                save();
            }
        });

        return rootView;
    }

    private void save(){
        String firstName = mFirstNameView.getText().toString();
        String lastName = mLastNameView.getText().toString();
        String phone = mPhoneView.getText().toString();
        String birthdayStr = mBirthdayView.getText().toString();
        String major = mMajorView.getText().toString();

        SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy", Locale.CANADA);
        Date birthday = mProfile.getBirthday();

        try {
            birthday = format.parse(birthdayStr);
        } catch (java.text.ParseException p) {

        }

        mProfile.setFirstName(firstName);
        mProfile.setLastName(lastName);
        mProfile.setPhone(phone);
        mProfile.setBirthday(birthday);
        mProfile.setMajor(major);

        JSONArray contactLinks = new JSONArray();

        for (int i = 0; i < mContactLinksView.getChildCount(); i++) {
            View v = mContactLinksView.getChildAt(i);
            EditText editText = (EditText) v.findViewWithTag(mHubActivity.getString(R.string.profile_contact_link_tag));
            String contactLink = editText.getText().toString();
            if (!contactLink.isEmpty()) contactLinks.put(contactLink);
        }

        mProfile.setContactLinks(contactLinks);

        if (mAvatar != null) {
//            Bitmap bitmap = null;
////            final ParseFile avatar = new ParseFile();
//
//            try {
//                InputStream is = mHubActivity.getContentResolver().openInputStream(mAvatar);
//                bitmap = BitmapFactory.decodeStream(is);
//                File avatarFile = new File(mHubActivity.getCacheDir(), "tmp");
//                avatarFile.createNewFile();
//                ByteArrayOutputStream bos = new ByteArrayOutputStream();
//                bitmap.compress(Bitmap.CompressFormat.PNG, 0, bos);
//                byte[] data = bos.toByteArray();
//                FileOutputStream fos = new FileOutputStream(avatarFile);
//                fos.write(data);
////                avatar = new ParseFile(bos.toByteArray());
//            } catch (FileNotFoundException fne) {
//
//            } catch (IOException ioe) {
//
//            }
//
//            Toast.makeText(mHubActivity.getApplicationContext(), R.string.info_profile_saving, Toast.LENGTH_LONG).show();
////            avatar.saveInBackground(new SaveCallback() {
////                @Override
////                public void done(ParseException e) {
////                    if (e == null) {
////                        mProfile.setAvatar(avatar);
////                        mProfile.saveInBackground(new SaveCallback() {
////                            @Override
////                            public void done(ParseException e) {
////                                if (e == null) {
////                                    Toast.makeText(mHubActivity.getApplicationContext(), R.string.info_profile_updated, Toast.LENGTH_SHORT).show();
////                                } else {
////                                    Toast.makeText(mHubActivity.getApplicationContext(), R.string.error_updating_profile, Toast.LENGTH_SHORT).show();
////                                    System.out.println(e.getMessage());
////                                }
////                            }
////                        });
////                    } else {
////                        System.out.println(e.getMessage());
////                    }
////                }
////            });
        } else {
//            mProfile.saveInBackground(new SaveCallback() {
//                @Override
//                public void done(ParseException e) {
//                    if (e == null) {
//                        Toast.makeText(mHubActivity.getApplicationContext(), R.string.info_profile_updated, Toast.LENGTH_SHORT).show();
//                    } else {
//                        Toast.makeText(mHubActivity.getApplicationContext(), R.string.error_updating_profile, Toast.LENGTH_SHORT).show();
//                        System.out.println(e.getMessage());
//                    }
//                }
//            });
        }

        mProfile.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if (e == null) {
                    Toast.makeText(mHubActivity.getApplicationContext(), R.string.info_profile_updated, Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(mHubActivity.getApplicationContext(), R.string.error_updating_profile, Toast.LENGTH_SHORT).show();
                    System.out.println(e.getMessage());
                }
            }
        });
    }

    public static class DatePickerFragment extends DialogFragment
            implements DatePickerDialog.OnDateSetListener {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current date as the default date in the picker
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);

            // Create a new instance of DatePickerDialog and return it
            return new DatePickerDialog(getActivity(), this, year, month, day);
        }

        public void onDateSet(DatePicker view, int year, int month, int day) {
            EditText birthDayView = (EditText) mHubActivity.findViewById(R.id.profile_birthday);
            SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy", Locale.CANADA);
            Calendar cal = Calendar.getInstance();
            cal.set(Calendar.DAY_OF_MONTH, day);
            cal.set(Calendar.MONTH, month);
            cal.set(Calendar.YEAR, year);
            birthDayView.setText(format.format(cal.getTime()));
        }
    }

    private void uploadAvatar() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        startActivityForResult(intent, READ_REQUEST_CODE);
    }

    private View getContactLinkView() {
        final View contactLinkView = mHubActivity.getLayoutInflater().inflate(R.layout.contact_link, null, false);
        ImageView deleteLinkView = (ImageView) contactLinkView.findViewWithTag(mHubActivity.getString(R.string.profile_delete_contact_link_tag));
        deleteLinkView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mContactLinksView.removeView(contactLinkView);
            }
        });

        return contactLinkView;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == READ_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            Uri uri = null;
            if (data != null) {
                uri = data.getData();
                mAvatarView.setImageURI(uri);
                mAvatar = uri;
            }
        }
    }
}

package webb8.wathub.hub.fragments.actions;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import webb8.wathub.R;
import webb8.wathub.util.NavItem;
import webb8.wathub.hub.fragments.HubFragment;
import webb8.wathub.models.Carpool;
import webb8.wathub.models.Post;
import webb8.wathub.models.PostTypes;

/**
 * Created by jorujlu on 06/03/16.
 */
public class ActionPostCarpoolFragment extends ActionPostFragment {
    public ActionPostCarpoolFragment() {}

    // UI fields
    protected EditText mFromView;
    protected EditText mToView;
    protected EditText mWhenView;
    protected EditText mPriceView;
    protected EditText mNumPassengerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View actionPostView = inflater.inflate(R.layout.fragment_action_post, container, false);
        FrameLayout actionPostContainer = (FrameLayout) actionPostView.findViewById(R.id.post_action_container);
        View carpoolSectionView = inflater.inflate(R.layout.fragment_action_carpool, container, false);

        actionPostContainer.addView(carpoolSectionView);

        mContentView = (EditText) actionPostView.findViewById(R.id.edit_post_content);
        mPostBtnView = (Button) actionPostView.findViewById(R.id.action_post_go);
        mFromView = (EditText) carpoolSectionView.findViewById(R.id.edit_carpool_from);
        mToView = (EditText) carpoolSectionView.findViewById(R.id.edit_carpool_to);
        mWhenView = (EditText) carpoolSectionView.findViewById(R.id.edit_carpool_when);
        mPriceView = (EditText) carpoolSectionView.findViewById(R.id.edit_carpool_price);
        mNumPassengerView = (EditText) carpoolSectionView.findViewById(R.id.edit_carpool_max_passengers);

        final Post post = new Post();
        final Carpool carpoolPost = new Carpool();


        mWhenView.setOnFocusChangeListener(new View.OnFocusChangeListener(){
            @Override
            public void onFocusChange(View v, boolean arg){
                if(arg) {
                    showDatePickerDialog(v);
                }
            }
        });

        mPostBtnView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkInput()) {
                    post.setContent(mContentView.getText().toString());
                    post.setUser(ParseUser.getCurrentUser());
                    post.setPostType(PostTypes.CARPOOL.getType());
                    SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.CANADA);

                    Calendar departTime = Calendar.getInstance();

                    try {
                        departTime.setTime(format.parse(mWhenView.getText().toString()));
                    } catch (java.text.ParseException e) {

                    }

                    carpoolPost.setPost(post);
                    carpoolPost.setFrom(mFromView.getText().toString());
                    carpoolPost.setTo(mFromView.getText().toString());
                    carpoolPost.setWhen(departTime.getTime());
                    carpoolPost.setPrice(Double.parseDouble(mPriceView.getText().toString()));
                    carpoolPost.setMaxPassengers(Integer.parseInt(mNumPassengerView.getText().toString()));

                    post.saveInBackground(new SaveCallback() {
                        @Override
                        public void done(ParseException e) {
                            if (e == null) {
                                FragmentManager fragmentManager = getFragmentManager();
                                Toast.makeText(getActivity(), R.string.info_post_published, Toast.LENGTH_SHORT).show();
                                fragmentManager.beginTransaction().replace(R.id.container, HubFragment.newInstance(NavItem.ALL_POSTS.getId())).commit();
                            } else {
                                Toast.makeText(getActivity(), R.string.error_publishing_post, Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                    carpoolPost.saveInBackground();

                }

            }
        });

        return actionPostView;
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
            EditText mWhenView = (EditText) mHubActivity.findViewById(R.id.edit_carpool_when);
            mWhenView.setText(new StringBuilder().append(day).append("/")
                    .append(month).append("/").append(year));
        }
    }

    public void showDatePickerDialog(View v) {
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getActivity().getFragmentManager(), "datePicker");
    }

    private boolean checkInput() {
        mContentView.setError(null);
        mFromView.setError(null);
        mToView.setError(null);
        mWhenView.setError(null);
        mPriceView.setError(null);
        mNumPassengerView.setError(null);

        if (TextUtils.isEmpty(mContentView.getText().toString())) {
            mContentView.setError(getString(R.string.error_post_empty_content));
            return false;
        }

        if (TextUtils.isEmpty(mFromView.getText().toString())) {
            mFromView.setError(getString(R.string.error_post_empty_carpool_from));
            return false;
        }

        if (TextUtils.isEmpty(mToView.getText().toString())) {
            mToView.setError(getString(R.string.error_post_empty_carpool_to));
            return false;
        }

        if (TextUtils.isEmpty(mWhenView.getText().toString())) {
            mWhenView.setError(getString(R.string.error_post_empty_carpool_when));
            return false;
        }

        if (TextUtils.isEmpty(mPriceView.getText().toString())) {
            mPriceView.setError(getString(R.string.error_post_empty_carpool_price));
            return false;
        }

        if (TextUtils.isEmpty(mNumPassengerView.getText().toString())) {
            mNumPassengerView.setError(getString(R.string.error_post_empty_carpool_num_passengers));
            return false;
        }

        return true;
    }
}

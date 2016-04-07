package webb8.wathub.util;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParsePush;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import org.json.JSONArray;
import org.json.JSONException;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import webb8.wathub.R;
import webb8.wathub.hub.ProfileActivity;
import webb8.wathub.models.Carpool;
import webb8.wathub.models.Comment;
import webb8.wathub.models.Parsable;
import webb8.wathub.models.Vote;


/**
 * Created by mismayil on 23/02/16.
 */
public class CarpoolCard extends PostCard {

    private Carpool mCarpool;
    private TextView mCarpoolNumJoined;
    private TextView mCarpoolMaxPeople;
    private int mJoinCount;
    private boolean mCurUserJoined;

    public CarpoolCard(Activity activity, Carpool carpool) {
        mActivity = activity;
        mCarpool = carpool;
        mJoinCount = 0;
        mCurUserJoined = false;
        refresh();
    }

    public View getView(View postCardView) {
        View view = mActivity.getLayoutInflater().inflate(R.layout.card_carpool, null, false);
        TextView carpoolFrom = (TextView) view.findViewById(R.id.card_carpool_from);
        TextView carpoolTo = (TextView) view.findViewById(R.id.card_carpool_to);
        TextView carpoolWhen = (TextView) view.findViewById(R.id.card_carpool_when);
        TextView carpoolPrice = (TextView) view.findViewById(R.id.card_carpool_price);
        mPostActionJoinBarView = (LinearLayout) postCardView.findViewById(R.id.post_action_bar_join);
        mPostActionJoinNumBarView = (LinearLayout) postCardView.findViewById(R.id.post_action_join_num_bar);
        mPostActionJoinView = (ImageView) postCardView.findViewById(R.id.post_action_join);
        mCarpoolNumJoined = (TextView) postCardView.findViewById(R.id.post_action_join_num);
        mCarpoolMaxPeople = (TextView) postCardView.findViewById(R.id.post_action_join_max_num);

        mPostActionJoinBarView.setVisibility(View.VISIBLE);

        updateJoinCount(0);

        carpoolFrom.setText(mCarpool.getFrom());
        carpoolTo.setText(mCarpool.getTo());
        Calendar cal = Calendar.getInstance();
        cal.setTime(mCarpool.getWhen());
        SimpleDateFormat format = new SimpleDateFormat("MMMM dd, yyyy", Locale.CANADA);
        carpoolWhen.setText(format.format(cal.getTime()));
        carpoolPrice.setText(String.valueOf((int) mCarpool.getPrice()));
        mCarpoolNumJoined.setText(String.valueOf(mJoinCount));
        mCarpoolMaxPeople.setText(String.valueOf(mCarpool.getMaxPassengers()));

        mPostActionJoinView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mCurUserJoined) {
                    updateJoinCount(-1);
                    JSONArray joinedUsers = mCarpool.getPassengers();

                    for (int i = 0; i < joinedUsers.length(); i++) {
                        String userObjectId = null;

                        try {
                            userObjectId = joinedUsers.getJSONObject(i).get(Parsable.KEY_OBJECT_ID).toString();
                        } catch (JSONException p) {

                        }

                        if (userObjectId != null && userObjectId.equalsIgnoreCase(ParseUser.getCurrentUser().getObjectId())) {
                            joinedUsers.remove(i);
                            break;
                        }
                    }

                    mCarpool.setPassengers(joinedUsers);
                    mCarpool.saveInBackground();
                    Toast.makeText(mActivity.getApplicationContext(), R.string.action_unjoined_carpool, Toast.LENGTH_SHORT).show();

                } else {
                    if (mJoinCount + 1 <= mCarpool.getMaxPassengers()) {
                        updateJoinCount(1);
                        mCarpool.addPassenger(ParseUser.getCurrentUser());
                        mCarpool.saveInBackground();
                        Toast.makeText(mActivity, R.string.action_joined_carpool, Toast.LENGTH_SHORT).show();
                        ParsePush parsePush = new ParsePush();
                        parsePush.setChannel("CARPOOL");
                        parsePush.setMessage(ParseUser.getCurrentUser().getUsername() + " joined your ride");
                        parsePush.sendInBackground();
                    } else {
                        Toast.makeText(mActivity, R.string.info_car_is_full, Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        mPostActionJoinNumBarView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                JSONArray passengers = mCarpool.getPassengers();

                if (passengers != null) {
                    final String[] userNames = new String[passengers.length()];

                    for (int i = 0; i < passengers.length(); i++) {
                        try {
                            String userObjectId = passengers.getJSONObject(i).get(Parsable.KEY_OBJECT_ID).toString();
                            ParseQuery<ParseUser> userQuery = ParseUser.getQuery();
                            userQuery.whereEqualTo(Parsable.KEY_OBJECT_ID, userObjectId);
                            List<ParseUser> users = userQuery.find();
                            ParseUser user = users.get(0);
                            userNames[i] = user.getUsername();
                        } catch (JSONException e) {

                        } catch (ParseException p) {

                        }
                    }

                    AlertDialog.Builder builder = new AlertDialog.Builder(mActivity);
                    builder.setTitle(R.string.title_users_joined);
                    builder.setItems(userNames, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent(mActivity, ProfileActivity.class);
                            intent.putExtra(ProfileActivity.EXTRA_USER_NAME, userNames[which]);
                            mActivity.startActivity(intent);
                        }
                    });
                    AlertDialog dialog = builder.create();
                    dialog.setCanceledOnTouchOutside(true);
                    dialog.show();
                }
            }
        });

        return view;
    }

    public void refresh() {

        try {
            mCarpool.fetch();
        } catch (ParseException e) {

        }

        mCurUserJoined = false;

        JSONArray passengers = mCarpool.getPassengers();

        if (passengers != null) {
            for (int i = 0; i < passengers.length(); i++) {
                String userObjectId = null;

                try {
                    userObjectId = passengers.getJSONObject(i).get(Parsable.KEY_OBJECT_ID).toString();
                } catch (JSONException e) {

                }

                if (userObjectId != null && userObjectId.equalsIgnoreCase(ParseUser.getCurrentUser().getObjectId())) {
                    mCurUserJoined = true;
                    break;
                }

            }
            mJoinCount = passengers.length();
        } else {
            mJoinCount = 0;
            mCurUserJoined = false;
        }



    }

    public void updateJoinCount(int count) {
        if (count > 0) mCurUserJoined = true;
        if (count < 0) mCurUserJoined = false;
        if (mCurUserJoined) mPostActionJoinView.setImageResource(R.drawable.ic_person_black_24dp);
        else mPostActionJoinView.setImageResource(R.drawable.ic_person_add_black_24dp);
        mJoinCount += count;
        mCarpoolNumJoined.setText(String.valueOf(mJoinCount));
        mCarpoolMaxPeople.setText(String.valueOf(mCarpool.getMaxPassengers()));
    }
}


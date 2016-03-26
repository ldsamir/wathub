package webb8.wathub.util;

import android.app.Activity;
import android.view.View;
import android.widget.TextView;

import webb8.wathub.R;
import webb8.wathub.models.Carpool;



/**
 * Created by mismayil on 23/02/16.
 */
public class CarpoolCard extends PostCard {

    private Carpool mCarpool;

    public CarpoolCard(Activity activity, Carpool carpool) {
        mActivity = activity;
        mCarpool = carpool;
    }

    @Override
    public View getView() {
        View view = mActivity.getLayoutInflater().inflate(R.layout.card_carpool, null, false);
        TextView carpoolFrom = (TextView) view.findViewById(R.id.card_carpool_from);
        TextView carpoolTo = (TextView) view.findViewById(R.id.card_carpool_to);
        TextView carpoolWhen = (TextView) view.findViewById(R.id.card_carpool_when);
        TextView carpoolPrice = (TextView) view.findViewById(R.id.card_carpool_price);
        carpoolFrom.setText(mCarpool.getFrom());
        carpoolTo.setText(mCarpool.getTo());
        String when = mCarpool.getWhen().toString();
        when = when.substring(4, when.length()-12);
        carpoolWhen.setText(when);
        carpoolPrice.setText(String.valueOf(mCarpool.getPrice()));

        return view;
    }

}


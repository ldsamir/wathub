package webb8.wathub.util;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import java.util.List;

import webb8.wathub.R;

/**
 * Created by mismayil on 4/1/16.
 */
public class DrawerAdapter extends BaseAdapter {
    Activity mActivity;
    NavItem[] mNavItems;
    private int selectedPosition;

    public DrawerAdapter(Activity activity, NavItem[] navItemList) {
        mActivity = activity;
        mNavItems = navItemList;
    }

    @Override
    public int getCount() {
        return mNavItems.length;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public Object getItem(int position) {
        return mNavItems[position];
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view;

        if (convertView != null) {
            view = convertView;
        } else {
            view = mActivity.getLayoutInflater().inflate(R.layout.drawer_item, parent, false);
        }

        LinearLayout linearLayout = (LinearLayout) view.findViewById(R.id.drawer_item_layout);
        TextView textView = (TextView) view.findViewById(R.id.drawer_item_text);
        ImageView imageView = (ImageView) view.findViewById(R.id.drawer_item_ic);
        textView.setText(mActivity.getString(mNavItems[position].getNameId()));

        if (mNavItems[position] == NavItem.PROFILE) imageView.setImageResource(R.drawable.ic_account_circle_black_24dp);
        if (mNavItems[position] == NavItem.MY_POSTS) imageView.setImageResource(R.drawable.ic_home_black_24dp);
        if (mNavItems[position] == NavItem.ALL_POSTS) imageView.setImageResource(R.drawable.ic_view_headline_black_24dp);
        if (mNavItems[position] == NavItem.BOOK_EXCHANGE_POSTS) imageView.setImageResource(R.drawable.ic_book_black_24dp);
        if (mNavItems[position] == NavItem.CARPOOL_POSTS) imageView.setImageResource(R.drawable.ic_directions_car_black_24dp);
        if (mNavItems[position] == NavItem.GROUP_STUDY_POSTS) imageView.setImageResource(R.drawable.ic_group_black_24dp);
        if (mNavItems[position] == NavItem.FAVORITES) imageView.setImageResource(R.drawable.ic_bookmark_black_24dp);
        if (mNavItems[position] == NavItem.DONE) imageView.setImageResource(R.drawable.ic_done_black_24dp);
        if (mNavItems[position] == NavItem.LOG_OUT) imageView.setImageResource(R.drawable.ic_power_settings_new_black_24dp);

        if (position == selectedPosition) {
            linearLayout.setBackgroundColor(mActivity.getResources().getColor(R.color.teal));
        } else {
            linearLayout.setBackgroundColor(mActivity.getResources().getColor(R.color.yellow_uw));
        }

        return view;
    }

    public void setSelectedPosition(int position) {
        selectedPosition = position;
        notifyDataSetChanged();
    }

}

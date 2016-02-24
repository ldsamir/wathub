package webb8.wathub.hub.fragments;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;

import webb8.wathub.hub.Action;
import webb8.wathub.hub.HubActivity;

/**
 * Created by mismayil on 23/02/16.
 */
public class HubFragment extends Fragment {

    private static final String ARG_SECTION_NUMBER = "SECTION_NUMBER";
    protected static Activity hubActivity;

    public HubFragment() {}

    public static HubFragment newInstance(int action) {
        HubFragment fragment = null;

        if (action == Action.PROFILE.getId()) {
            fragment = new ProfileFragment();
        }

        if (action == Action.ALL_POSTS.getId()) {
            fragment = new PostFragment();
        }

        if (action == Action.BOOK_EXCHANGE_POSTS.getId()) {
            fragment = new BookExchangeFragment();
        }

        if (action == Action.CARPOOL_POSTS.getId()) {
            fragment = new CarpoolFragment();
        }

        if (action == Action.GROUP_STUDY_POSTS.getId()) {
            fragment = new GroupStudyFragment();
        }

        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, action);
        if (fragment != null) fragment.setArguments(args);

        return fragment;
    }

    // set current activity
    public static void setHubActivity(Activity activity) {
        hubActivity = activity;
    }

    @Override
        public void onAttach(Context activity) {
            super.onAttach(activity);
            ((HubActivity) activity).onSectionAttached(
                    getArguments().getInt(ARG_SECTION_NUMBER));
        }
}

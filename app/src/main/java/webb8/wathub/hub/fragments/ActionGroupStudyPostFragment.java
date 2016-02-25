package webb8.wathub.hub.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import webb8.wathub.R;

/**
 * Created by mismayil on 25/02/16.
 */

/**
 * This fragment handles group study posts
 */
public class ActionGroupStudyPostFragment extends ActionPostFragment {

    // UI fields

    public ActionGroupStudyPostFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View actionPostView = inflater.inflate(R.layout.fragment_action_post, container, false);
        FrameLayout actionPostContainer = (FrameLayout) actionPostView.findViewById(R.id.post_action_container);
        View groupStudySectionView = inflater.inflate(R.layout.fragment_action_groupstudy, container, false);

        actionPostContainer.addView(groupStudySectionView);

        return actionPostView;
    }
}

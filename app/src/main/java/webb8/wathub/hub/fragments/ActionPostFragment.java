package webb8.wathub.hub.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import webb8.wathub.R;
import webb8.wathub.hub.Action;

/**
 * This fragment handles posting actions
 */
public class ActionPostFragment extends HubFragment {

    public ActionPostFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        int action = getArguments().getInt(ARG_ACTION_NUMBER);
        View actionPostView = inflater.inflate(R.layout.fragment_action_post, container, false);
        FrameLayout actionPostContainer = (FrameLayout) actionPostView.findViewById(R.id.post_action_container);
        View bookExchangeSectionView = inflater.inflate(R.layout.fragment_action_bookexchange, container, false);
        View groupStudySectionView = inflater.inflate(R.layout.fragment_action_groupstudy, container, false);

        if (action == Action.ACTION_POST_BOOK_EXCHANGE.getId()) {
            actionPostContainer.addView(bookExchangeSectionView);
        } else if (action == Action.ACTION_POST_GROUP_STUDY.getId()) {
            actionPostContainer.addView(groupStudySectionView);
        }

        return actionPostView;
    }
}

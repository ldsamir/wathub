package webb8.wathub.hub.fragments.actions;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;

import webb8.wathub.R;

/**
 * Created by mismayil on 3/23/16.
 */
public class ActionSearchCarpoolFragment extends ActionSearchFragment {

    // UI fields
    private EditText mCarpoolFromView;
    private EditText mCarpoolToView;
    private EditText mCarpoolWhenView;
    private EditText mCarpoolMinPassengerView;
    private EditText mCarpoolMaxPassengerView;
    private EditText mCarpoolMinPriceView;
    private EditText mCarpoolMaxPriceView;

    public ActionSearchCarpoolFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View actionSearchView = super.onCreateView(inflater, container, savedInstanceState);
        final View actionSearchCarpoolView = inflater.inflate(R.layout.fragment_action_search_carpool, container, false);
        mActionSearchContainer = (FrameLayout) actionSearchView.findViewById(R.id.action_search_container);

        mActionSearchContainer.addView(actionSearchCarpoolView);

        mCarpoolFromView = (EditText) actionSearchCarpoolView.findViewById(R.id.edit_search_carpool_from);
        mCarpoolToView = (EditText) actionSearchCarpoolView.findViewById(R.id.edit_search_carpool_to);
        mCarpoolWhenView = (EditText) actionSearchCarpoolView.findViewById(R.id.edit_search_carpool_when);
        mCarpoolMinPassengerView = (EditText) actionSearchCarpoolView.findViewById(R.id.edit_search_carpool_min_passenger);
        mCarpoolMaxPassengerView = (EditText) actionSearchCarpoolView.findViewById(R.id.edit_search_carpool_max_passenger);
        mCarpoolMinPriceView = (EditText) actionSearchCarpoolView.findViewById(R.id.edit_search_min_price);
        mCarpoolMaxPriceView = (EditText) actionSearchCarpoolView.findViewById(R.id.edit_search_max_price);

        return actionSearchView;
    }
}

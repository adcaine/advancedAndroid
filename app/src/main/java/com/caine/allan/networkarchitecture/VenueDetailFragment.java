package com.caine.allan.networkarchitecture;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by allancaine on 2015-10-19.
 */
public class VenueDetailFragment extends Fragment {

    private static final String ARG_VENUE_ID = "VenueDetailFragment.VenueId";

    private DataManager mDataManager;
    private TokenStore mTokenStore;
    private String mVenueId;
    private Venue mVenue;

    private TextView mVenueNameTextView;
    private TextView mVenueAddressTextView;
    private Button mCheckInButton;

    public static VenueDetailFragment newInstance(String venueId){
        VenueDetailFragment fragment = new VenueDetailFragment();

        Bundle args = new Bundle();
        args.putString(ARG_VENUE_ID, venueId);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_venue_detail, container, false);
        mVenueNameTextView = (TextView)view.findViewById(R.id.fragment_venue_VenueNameTextView);
        mVenueAddressTextView = (TextView)view.findViewById(R.id.fragment_venue_VenueAddressTextView);
        mCheckInButton = (Button)view.findViewById(R.id.fragment_venue_VenueCheckInButton);
        mTokenStore = new TokenStore(getActivity());
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        mVenueId = getArguments().getString(ARG_VENUE_ID);
        mDataManager = DataManager.get(getActivity());
        mVenue = mDataManager.getVenue(mVenueId);
    }

    @Override
    public void onResume() {
        super.onResume();
        mVenueNameTextView.setText(mVenue.getName());
        mVenueAddressTextView.setText(mVenue.getFormattedAddress());
        if(mTokenStore.getAccessToken() != null){
            mCheckInButton.setVisibility(View.VISIBLE);
        }
    }
}

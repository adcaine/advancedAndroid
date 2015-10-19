package com.caine.allan.networkarchitecture;

import android.os.Bundle;
import android.support.v4.app.Fragment;

/**
 * Created by allancaine on 2015-10-19.
 */
public class VenueDetailFragment extends Fragment {

    private static final String ARG_VENUE_ID = "VenueDetailFragment.VenueId";

    public static VenueDetailFragment newInstance(String venueId){
        VenueDetailFragment fragment = new VenueDetailFragment();

        Bundle args = new Bundle();
        args.putString(ARG_VENUE_ID, venueId);
        fragment.setArguments(args);
        return fragment;
    }


}

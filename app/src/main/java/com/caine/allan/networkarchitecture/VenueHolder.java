package com.caine.allan.networkarchitecture;

import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by allancaine on 2015-10-19.
 */
public class VenueHolder extends RecyclerView.ViewHolder{

    private VenueView mVenueView;
    private Venue mVenue;

    public VenueHolder(View itemView) {
        super(itemView);
        mVenueView = (VenueView)itemView;
    }

    public void bindVenue(Venue venue){
        mVenue = venue;
        mVenueView.setTitle(mVenue.getName());
        mVenueView.setAddress(mVenue.getFormattedAddress());
    }
}

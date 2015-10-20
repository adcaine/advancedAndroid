package com.caine.allan.networkarchitecture.views;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.caine.allan.networkarchitecture.VenueDetailActivity;
import com.caine.allan.networkarchitecture.models.Venue;
import com.caine.allan.networkarchitecture.views.VenueView;

/**
 * Created by allancaine on 2015-10-19.
 */
public class VenueHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

    private VenueView mVenueView;
    private Venue mVenue;

    public VenueHolder(View itemView) {
        super(itemView);
        itemView.setOnClickListener(this);
        mVenueView = (VenueView)itemView;
    }

    public void bindVenue(Venue venue){
        mVenue = venue;
        mVenueView.setTitle(mVenue.getName());
        mVenueView.setAddress(mVenue.getFormattedAddress());
    }

    @Override
    public void onClick(View v) {
        Context context = v.getContext();
        Intent intent = VenueDetailActivity.newIntent(context, mVenue.getId());
        context.startActivity(intent);
    }
}

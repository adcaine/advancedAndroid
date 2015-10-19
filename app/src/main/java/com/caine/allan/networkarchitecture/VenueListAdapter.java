package com.caine.allan.networkarchitecture;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by allancaine on 2015-10-19.
 */
public class VenueListAdapter extends RecyclerView.Adapter<VenueHolder> {

    private Context mContext;
    private List<Venue> mVenueList;

    public VenueListAdapter(Context context, List<Venue> venues){
        mContext = context;
        mVenueList = venues;
    }

    public void setVenueList(List<Venue> venueList){
        mVenueList = venueList;
        notifyDataSetChanged();
    }

    @Override
    public VenueHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        VenueView venueView = new VenueView(mContext);
        return new VenueHolder(venueView);
    }

    @Override
    public void onBindViewHolder(VenueHolder holder, int position) {
        holder.bindVenue(mVenueList.get(position));

    }

    @Override
    public int getItemCount() {
        return mVenueList.size();
    }
}

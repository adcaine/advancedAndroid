package com.caine.allan.networkarchitecture;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * A placeholder fragment containing a simple view.
 */
public class VenueListActivityFragment extends Fragment implements VenueSearchListener {

    protected DataManager mDataManager;
    private List<Venue> mVenueList;
    private RecyclerView mRecyclerView;

    public VenueListActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_venue_list, container, false);
        mRecyclerView = (RecyclerView)view.findViewById(R.id.venueListRecyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(layoutManager);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        mDataManager = DataManager.get(getActivity());
        mDataManager.addVenueSearchListener(this);
        mDataManager.fetchVenueSearch();
    }

    @Override
    public void onStop() {
        super.onStop();
        mDataManager.removeVenueSearchListener(this);
    }

    @Override
    public void onVenueSearchFinished() {
        mVenueList = mDataManager.getVenueList();
    }
}

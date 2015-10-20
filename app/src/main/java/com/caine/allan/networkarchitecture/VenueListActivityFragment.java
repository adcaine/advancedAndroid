package com.caine.allan.networkarchitecture;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.caine.allan.networkarchitecture.listeners.VenueSearchListener;
import com.caine.allan.networkarchitecture.models.Venue;
import com.caine.allan.networkarchitecture.views.VenueListAdapter;
import com.caine.allan.networkarchitecture.web.DataManager;

import java.util.Collections;
import java.util.List;

/**
 * A placeholder fragment containing a simple view.
 */
public class VenueListActivityFragment extends Fragment implements VenueSearchListener {

    private static final int AUTHENTICATION_ACTIVITY_REQUEST = 0;

    protected DataManager mDataManager;
    private List<Venue> mVenueList;
    private RecyclerView mRecyclerView;
    private VenueListAdapter mVenueListAdapter;
    private TokenStore mTokenStore;

    public VenueListActivityFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setHasOptionsMenu(true);
        mTokenStore = new TokenStore(getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {



        View view = inflater.inflate(R.layout.fragment_venue_list, container, false);
        mRecyclerView = (RecyclerView)view.findViewById(R.id.venueListRecyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(layoutManager);
        mVenueListAdapter = new VenueListAdapter(getActivity(), Collections.EMPTY_LIST);
        mRecyclerView.setAdapter(mVenueListAdapter);
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
        mVenueListAdapter.setVenueList(mVenueList);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        if(mTokenStore.getAccessToken() == null){
            inflater.inflate(R.menu.menu_sign_in, menu);
        } else {
            inflater.inflate(R.menu.menu_sign_out, menu);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_sign_out:
                mTokenStore.setAccessToken(null);
                getActivity().invalidateOptionsMenu();
                return true;
            case R.id.sign_in:
                Intent i = new Intent(getActivity(), AuthenticationActivity.class);
                startActivityForResult(i, AUTHENTICATION_ACTIVITY_REQUEST);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == AUTHENTICATION_ACTIVITY_REQUEST){
            getActivity().invalidateOptionsMenu();
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}

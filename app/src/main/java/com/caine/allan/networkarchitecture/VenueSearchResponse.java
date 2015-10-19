package com.caine.allan.networkarchitecture;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by allancaine on 2015-10-19.
 */
public class VenueSearchResponse {
    @SerializedName("venues") private List<Venue> mVenueList;

    public List<Venue> getVenueList(){
        return mVenueList;
    }
}

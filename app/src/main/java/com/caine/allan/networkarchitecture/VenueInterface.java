package com.caine.allan.networkarchitecture;

import retrofit.http.GET;
import retrofit.http.Query;
import retrofit.Callback;

/**
 * Created by allancaine on 2015-10-19.
 */


public interface VenueInterface {
    @GET("/venues/search")
    void venueSearch(@Query("ll") String latLngString, Callback<VenueSearchResponse> callback);

}

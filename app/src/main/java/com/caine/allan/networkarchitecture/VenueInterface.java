package com.caine.allan.networkarchitecture;

import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Query;
import retrofit.Callback;

/**
 * Created by allancaine on 2015-10-19.
 */


public interface VenueInterface {
    @GET("/venues/search")
    void venueSearch(@Query("ll") String latLngString, Callback<VenueSearchResponse> callback);

    @FormUrlEncoded
    @POST("/checkins/add")
    public void venueCheckIn(@Field("venueId") String venueId, Callback<Object> callback);

}

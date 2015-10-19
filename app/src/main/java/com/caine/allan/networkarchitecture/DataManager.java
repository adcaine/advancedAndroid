package com.caine.allan.networkarchitecture;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;

import retrofit.Callback;
import retrofit.RequestInterceptor;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;
import retrofit.converter.GsonConverter;

/**
 * Created by allancaine on 2015-10-19.
 */
public class DataManager {
    private static final String TAG = "DataManager";
    private static final String FOURSQUARE_ENDPOINT = "https://api.foursquare.com/v2";

    private static final String CLIENT_ID = "S1RJ2URPTNSFRIH0R3MPYBRSWKYQQ0SRAVDRJGBUTTYQWKYW";
    private static final String CLIENT_SECRET = "LBSJ52A04D1IGN1KEBRANZSW4PRRYF51HFTALP0G3YXAHI0O";
    private static final String FOURSQUARE_VERSION = "20150406";
    private static final String FOURSQUARE_MODE = "foursquare";

    private static final String TEST_LAT_LONG = "33.759,-84.332";
    private List<Venue> mVenueList;

    private static DataManager sDataManager;
    private RestAdapter mBasicRestAdapter;

    private List<VenueSearchListener> mSearchListeners;

    public static DataManager get(Context context){
        if(sDataManager == null){
            Gson gson = new GsonBuilder()
                    .registerTypeAdapter(VenueSearchResponse.class, new VenueListDeserializer())
                    .create();
            RestAdapter basicRestAdapter = new RestAdapter.Builder()
                    .setEndpoint(FOURSQUARE_ENDPOINT)
                    .setConverter(new GsonConverter(gson))
                    .setLogLevel(RestAdapter.LogLevel.FULL)
                    .setRequestInterceptor(sRequestInterceptor)
                    .build();
            sDataManager = new DataManager(basicRestAdapter);
        }
        return sDataManager;
    }

    protected DataManager(RestAdapter basicRestAdapter){
        mBasicRestAdapter = basicRestAdapter;
        mSearchListeners = new ArrayList<>();
    }

    public static RequestInterceptor sRequestInterceptor = new RequestInterceptor() {
        @Override
        public void intercept(RequestFacade request) {
            request.addQueryParam("client_id", CLIENT_ID);
            request.addQueryParam("client_secret", CLIENT_SECRET);
            request.addQueryParam("v", FOURSQUARE_VERSION);
            request.addQueryParam("m", FOURSQUARE_MODE);
        }
    };

    public void fetchVenueSearch() {
        VenueInterface venueInterface = mBasicRestAdapter
                .create(VenueInterface.class);

        venueInterface.venueSearch(TEST_LAT_LONG, new Callback<VenueSearchResponse>() {
            @Override
            public void success(VenueSearchResponse venueSearchResponse, Response response) {
                mVenueList = venueSearchResponse.getVenueList();
                notifySearchListeners();
            }

            @Override
            public void failure(RetrofitError error) {
                Log.e(TAG, "Failed to fetch venue search", error);
            }
        });
    }

    public List<Venue> getVenueList() {
        return mVenueList;
    }

    public void addVenueSearchListener(VenueSearchListener venueSearchListener){
        mSearchListeners.add(venueSearchListener);
    }

    public void removeVenueSearchListener(VenueSearchListener venueSearchListener){
        mSearchListeners.remove(venueSearchListener);
    }

    private void notifySearchListeners() {
        for(VenueSearchListener listener : mSearchListeners){
            listener.onVenueSearchFinished();
        }
    }


}

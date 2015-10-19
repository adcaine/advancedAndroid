package com.caine.allan.networkarchitecture;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit.RestAdapter;
import retrofit.converter.GsonConverter;

/**
 * Created by allancaine on 2015-10-19.
 */
public class DataManager {
    private static final String TAG = "DataManager";
    private static final String FOURSQUARE_ENDPOINT = "https://api/foursquare.com/v2";

    private static DataManager sDataManager;
    private RestAdapter mBasicResAdapter;

    public static DataManager get(Context context){
        if(sDataManager == null){
            Gson gson = new GsonBuilder()
                    .registerTypeAdapter(VenueSearchResponse.class, new VenueListDeserializer())
                    .create();
            RestAdapter basicRestAdapter = new RestAdapter.Builder()
                    .setEndpoint(FOURSQUARE_ENDPOINT)
                    .setConverter(new GsonConverter(gson))
                    .build();
            sDataManager = new DataManager(basicRestAdapter);
        }
        return sDataManager;
    }

    protected DataManager(RestAdapter basicResAdapter){
        mBasicResAdapter = basicResAdapter;
    }


}

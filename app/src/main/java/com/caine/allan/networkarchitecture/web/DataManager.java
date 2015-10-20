package com.caine.allan.networkarchitecture.web;

import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.widget.Toast;

import com.caine.allan.networkarchitecture.R;
import com.caine.allan.networkarchitecture.TokenStore;
import com.caine.allan.networkarchitecture.exceptions.UnauthorizedException;
import com.caine.allan.networkarchitecture.listeners.VenueCheckInListener;
import com.caine.allan.networkarchitecture.listeners.VenueSearchListener;
import com.caine.allan.networkarchitecture.models.Venue;
import com.caine.allan.networkarchitecture.models.VenueSearchResponse;
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
    private static final String OAUTH_ENDPOINT = "https://foursquare.com/oauth2/authenticate";
    public static final String OAUTH_REDIRECT_URI = "http://www.bignerdranch.com";

    private static final String CLIENT_ID = "S1RJ2URPTNSFRIH0R3MPYBRSWKYQQ0SRAVDRJGBUTTYQWKYW";
    private static final String CLIENT_SECRET = "T1MACDS2LRYXBVNEP2ZKHTZWEB5S1DMS41MZ2MDRGJPMLYEE";
    private static final String FOURSQUARE_VERSION = "20150406";
    private static final String FOURSQUARE_MODE = "foursquare";
    private static final String SWARM_MODE = "swarm";

    private static final String TEST_LAT_LONG = "33.759,-84.332";
    private List<Venue> mVenueList;

    private static DataManager sDataManager;
    private Context mContext;
    private static TokenStore sTokenStore;
    private RestAdapter mBasicRestAdapter;
    private RestAdapter mAuthenticatedRestAdapter;

    private List<VenueSearchListener> mSearchListeners;
    private List<VenueCheckInListener> mCheckInListeners;

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
            RestAdapter authenticatedRestAdapter = new RestAdapter.Builder()
                    .setEndpoint(FOURSQUARE_ENDPOINT)
                    .setConverter(new GsonConverter(gson))
                    .setLogLevel(RestAdapter.LogLevel.FULL)
                    .setRequestInterceptor(sAuthenticatedRequestInterceptor)
                    .setErrorHandler(new RetrofitErrorHandler())
                    .build();
            sDataManager = new DataManager(context.getApplicationContext(), basicRestAdapter, authenticatedRestAdapter);
        }
        return sDataManager;
    }

    protected DataManager(Context context, RestAdapter basicRestAdapter, RestAdapter authenticatedRestAdapter){
        mContext = context;
        sTokenStore = new TokenStore(mContext);
        mBasicRestAdapter = basicRestAdapter;
        mAuthenticatedRestAdapter = authenticatedRestAdapter;
        mSearchListeners = new ArrayList<>();
        mCheckInListeners = new ArrayList<>();
    }

    public String getAuthenticationUrl(){
        return Uri.parse(OAUTH_ENDPOINT).buildUpon()
                .appendQueryParameter("client_id", CLIENT_ID)
                .appendQueryParameter("response_type", "token")
                .appendQueryParameter("redirect_uri", OAUTH_REDIRECT_URI)
                .build()
                .toString();
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

    private static RequestInterceptor sAuthenticatedRequestInterceptor =
            new RequestInterceptor() {
                @Override
                public void intercept(RequestFacade request) {
                    request.addQueryParam("oauth_token", sTokenStore.getAccessToken());
                    request.addQueryParam("v", FOURSQUARE_VERSION);
                    request.addQueryParam("m", SWARM_MODE);
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
                notifiyCheckInListenersTokenExpired();
            }
        });
    }

    public void checkInToVenue(String venueId){
        VenueInterface venueInterface =
                mAuthenticatedRestAdapter.create(VenueInterface.class);
        venueInterface.venueCheckIn(venueId, new Callback<Object>() {
            @Override
            public void success(Object o, Response response) {
                notifyCheckInListeners();
            }

            @Override
            public void failure(RetrofitError error) {
                Log.e(TAG, "Failed to check in to venue,", error);
                if (error.getCause() instanceof UnauthorizedException) {
                    sTokenStore.setAccessToken(null);
                    notifiyCheckInListenersTokenExpired();
                }
            }
        });
    }

    public Venue getVenue(String venueId){
        for(Venue v : mVenueList){
            if(v.getId().equals(venueId)){
                return  v;
            }
        }
        return null;
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

    public void addVenueCheckInListener(VenueCheckInListener listener){
        mCheckInListeners.add(listener);
    }

    public void removeCheckInListener(VenueCheckInListener listener){
        mCheckInListeners.remove(listener);
    }

    private void notifyCheckInListeners(){
        for(VenueCheckInListener listener : mCheckInListeners){
            listener.onVenueCheckInFinished();
        }
    }

    private void notifiyCheckInListenersTokenExpired(){
        for(VenueCheckInListener listener : mCheckInListeners){
            listener.onTokenExpired();
        }
    }
}

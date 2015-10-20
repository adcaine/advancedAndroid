package com.caine.allan.networkarchitecture.web;

import com.caine.allan.networkarchitecture.BuildConfig;
import com.caine.allan.networkarchitecture.listeners.VenueSearchListener;
import com.caine.allan.networkarchitecture.models.Venue;
import com.caine.allan.networkarchitecture.models.VenueSearchResponse;
import com.caine.allan.networkarchitecture.web.DataManager;
import com.caine.allan.networkarchitecture.web.VenueInterface;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.MockitoAnnotations;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

import java.util.ArrayList;
import java.util.List;

import retrofit.Callback;
import retrofit.RestAdapter;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


/**
 * Created by allancaine on 2015-10-20.
 */
@RunWith(RobolectricTestRunner.class)
@Config(sdk = 21, constants = BuildConfig.class)
public class DataManagerTest {
    @Captor
    private ArgumentCaptor<Callback<VenueSearchResponse>> searchCaptor;
    private DataManager mDataManager;
    private RestAdapter mBasicRestAdapter;
    private RestAdapter mAuthenticatedRestAdapter;
    private VenueInterface mVenueInterface;
    private VenueSearchListener mVenueSearchListener;

    @Before
    public void setUp(){
        MockitoAnnotations.initMocks(this);
        mBasicRestAdapter = mock(RestAdapter.class);
        mAuthenticatedRestAdapter = mock(RestAdapter.class);

        mDataManager = new DataManager(RuntimeEnvironment.application, mBasicRestAdapter, mAuthenticatedRestAdapter);

        mVenueInterface = mock(VenueInterface.class);
        when(mBasicRestAdapter.create(VenueInterface.class))
                .thenReturn(mVenueInterface);
        mVenueSearchListener = mock(VenueSearchListener.class);
        mDataManager.addVenueSearchListener(mVenueSearchListener);
    }

    @Test
    public void searchListenerTriggeredOnSuccessfulSearch(){
        mDataManager.fetchVenueSearch();

        verify(mVenueInterface).venueSearch(anyString(), searchCaptor.capture());

        VenueSearchResponse response = mock(VenueSearchResponse.class);
        searchCaptor.getValue().success(response, null);

        verify(mVenueSearchListener).onVenueSearchFinished();

    }

    @Test
    public void venueSearchListSavedOnSuccessfulSearch() {
        mDataManager.fetchVenueSearch();

        verify(mVenueInterface).venueSearch(anyString(), searchCaptor.capture());

        String firstVenueName = "Cool first venue";
        Venue firstVenue = mock(Venue.class);
        when(firstVenue.getName()).thenReturn(firstVenueName);

        String secondVenueName = "Awesome second venue";
        Venue secondVenue = mock(Venue.class);
        when(secondVenue.getName()).thenReturn(secondVenueName);

        List<Venue> venueList = new ArrayList<>();
        venueList.add(firstVenue);
        venueList.add(secondVenue);

        VenueSearchResponse response = mock(VenueSearchResponse.class);
        when(response.getVenueList()).thenReturn(venueList);

        searchCaptor.getValue().success(response, null);

        List<Venue> dataManagerVenueList = mDataManager.getVenueList();

        assertThat(dataManagerVenueList, is(equalTo(venueList)));

    }

}

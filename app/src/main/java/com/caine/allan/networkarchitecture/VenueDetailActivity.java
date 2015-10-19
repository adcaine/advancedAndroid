package com.caine.allan.networkarchitecture;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by allancaine on 2015-10-19.
 */
public class VenueDetailActivity extends AppCompatActivity {

    private static final String ARG_VENUE_ID = "VenueDetailActivity.VenueId";

    public static Intent newIntent(Context context, String venueId){
        Intent i = new Intent(context, VenueDetailActivity.class);
        i.putExtra(ARG_VENUE_ID, venueId);
        return i;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String venueId = getIntent().getStringExtra(ARG_VENUE_ID);
        setContentView(R.layout.activity_fragment);
        getSupportFragmentManager().beginTransaction()
                .add(R.id.fragment_container, VenueDetailFragment.newInstance(venueId))
                .commit();
    }
}

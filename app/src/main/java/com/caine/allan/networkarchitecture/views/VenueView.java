package com.caine.allan.networkarchitecture.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.caine.allan.networkarchitecture.R;

/**
 * Created by allancaine on 2015-10-19.
 */
public class VenueView extends LinearLayout{

    private TextView mTitleTextView;
    private TextView mAddressTextView;

    public VenueView(Context context){
        this(context, null);
    }

    public VenueView(Context context, AttributeSet attrs){
        super(context, attrs);
        setOrientation(VERTICAL);
        LinearLayout.LayoutParams params = new LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.setMargins(0, 0, 0, 16);
        setLayoutParams(params);

        LayoutInflater layoutInflater = LayoutInflater.from(context);
        VenueView view = (VenueView)layoutInflater.inflate(R.layout.view_venue, this, true);
        mTitleTextView = (TextView)view.findViewById(R.id.view_venue_list_VenueTitleTextView);
        mAddressTextView = (TextView)view.findViewById(R.id.view_venue_list_VenueLocationTextView);
    }

    public void setTitle(String titleTextView) {
        mTitleTextView.setText(titleTextView);
    }

    public void setAddress(String addressTextView) {
        mAddressTextView.setText(addressTextView);
    }
}

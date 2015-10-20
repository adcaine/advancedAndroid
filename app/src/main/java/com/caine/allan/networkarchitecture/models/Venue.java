package com.caine.allan.networkarchitecture.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by allancaine on 2015-10-19.
 */
public class Venue {

    @SerializedName("id") private String mId;
    @SerializedName("name") private String mName;
    @SerializedName("verified") private boolean mVerified;
    @SerializedName("location") private Location mLocation;
    @SerializedName("categories") private List<Category> mCategory;

    public String getId() {
        return mId;
    }

    public String getName(){
        return mName;
    }

    public String getFormattedAddress() {
        return mLocation.getFormattedAddress();
    }


}

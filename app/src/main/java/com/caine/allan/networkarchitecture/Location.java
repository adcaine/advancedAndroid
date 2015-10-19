package com.caine.allan.networkarchitecture;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by allancaine on 2015-10-19.
 */
public class Location {
    @SerializedName("lat") private double mLatitude;
    @SerializedName("lng") private double mLongitude;
    @SerializedName("formattedAddress") private List<String> mFormattedAddress;

}

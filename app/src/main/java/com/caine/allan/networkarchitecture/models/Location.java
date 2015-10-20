package com.caine.allan.networkarchitecture.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by allancaine on 2015-10-19.
 */
public class Location {
    @SerializedName("lat") private double mLatitude;
    @SerializedName("lng") private double mLongitude;
    @SerializedName("formattedAddress") private List<String> mFormattedAddress;

    public String getFormattedAddress(){
        String formattedAddress = "";
        for(String addressPart : mFormattedAddress){
            formattedAddress += addressPart;
            if(mFormattedAddress.indexOf(addressPart) != (mFormattedAddress.size() - 1)){
                formattedAddress += " ";
            }
        }
        return formattedAddress;
    }

}

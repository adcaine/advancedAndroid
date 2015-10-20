package com.caine.allan.networkarchitecture.models;

import com.caine.allan.networkarchitecture.models.Icon;
import com.google.gson.annotations.SerializedName;

/**
 * Created by allancaine on 2015-10-19.
 */
public class Category {
    @SerializedName("id") private String mId;
    @SerializedName("name") private String mName;
    @SerializedName("icon") private Icon mIcon;
    @SerializedName("primary") private boolean mPrimary;
}

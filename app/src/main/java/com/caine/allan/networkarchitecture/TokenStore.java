package com.caine.allan.networkarchitecture;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by allancaine on 2015-10-19.
 */
public class TokenStore {
    private static final String TOKEN_KEY = "TokenStore.TokenKey";

    private Context mContext;
    private SharedPreferences mSharedPreferences;

    public TokenStore(Context context){
        mContext = context.getApplicationContext();
        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(mContext);
    }

    public String getAccessToken(){
        return mSharedPreferences.getString(TOKEN_KEY, null);
    }

    public void setAccessToken(String accessToken){
        mSharedPreferences.edit()
                .putString(TOKEN_KEY, accessToken)
                .apply();
    }
}

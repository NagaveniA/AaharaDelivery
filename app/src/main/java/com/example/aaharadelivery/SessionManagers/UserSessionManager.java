package com.example.aaharadelivery.SessionManagers;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.HashMap;

public class UserSessionManager {

    SharedPreferences sharedPreferences;
    // Editor reference for Shared preferences
    SharedPreferences.Editor editor,editor1;

    //context
    Context context;

    //shared pref mode
    int PRIVATE_MODE = 0;
    private static final String PREFER_NAME = "AaharaDelivery";

    public static final String KEY_accessToken = "access_token";
    public static final String KEY_GUEST = "isguest";

    public UserSessionManager(Context context) {
        this.context = context;
        sharedPreferences = context.getSharedPreferences(PREFER_NAME, PRIVATE_MODE);
        editor = sharedPreferences.edit();
    }
    public void createguest(boolean isguest){
        editor.putBoolean(KEY_GUEST, isguest);
        editor.commit();
    }

    public void createUserLoginSession(String accessToken){
          editor.putString(KEY_accessToken,accessToken);


        // commit changes
        editor.commit();
    }

    public HashMap<String, String> getUserDetails(){

        //Use hashmap to store user credentials
        HashMap<String, String> user = new HashMap<String, String>();


        //accessToken
        user.put(KEY_accessToken, sharedPreferences.getString(KEY_accessToken, null));

        return user;

    }

}

package com.aahara.aaharadelivery.SessionManagers;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.aahara.aaharadelivery.LoginActivity;

import java.util.HashMap;

public class UserSessionManager {

    SharedPreferences sharedPreferences;
    // Editor reference for Shared preferences
    SharedPreferences.Editor editor, editor1;

    //context
    Context context;

    //shared pref mode
    int PRIVATE_MODE = 0;
    private static final String PREFER_NAME = "AaharaDelivery";

    public static final String KEY_accessToken = "access_token";
    public static final String KEY_GUEST = "isguest";
    private static final String IS_USER_LOGIN = "IsUserLoggedIn";

    public UserSessionManager(Context context) {
        this.context = context;
        sharedPreferences = context.getSharedPreferences(PREFER_NAME, PRIVATE_MODE);
        editor = sharedPreferences.edit();
    }

    public void createguest(boolean isguest) {
        editor.putBoolean(KEY_GUEST, isguest);
        editor.commit();
    }

    public void createUserLoginSession(String accessToken) {
        editor.putString(KEY_accessToken, accessToken);
        editor.putBoolean(IS_USER_LOGIN, true);

        // commit changes
        editor.commit();
    }

    public HashMap<String, String> getUserDetails() {

        //Use hashmap to store user credentials
        HashMap<String, String> user = new HashMap<String, String>();


        //accessToken
        user.put(KEY_accessToken, sharedPreferences.getString(KEY_accessToken, null));

        return user;

    }

    public boolean checkLogin() {
        // Check login status
        if (!this.isUserLoggedIn()) {

            // user is not logged in redirect him to LoginActivity Activity
            Intent i = new Intent(context, LoginActivity.class);

            // Closing all the Activities from stack
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

            // Add new Flag to start new Activity
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            // Staring LoginActivity Activity
            context.startActivity(i);

            return true;
        }
        return false;
    }

    public boolean isUserLoggedIn() {
        return sharedPreferences.getBoolean(IS_USER_LOGIN, false);
    }

    public void logoutUser() {
        editor.putBoolean(IS_USER_LOGIN, false);
        editor.clear();
        editor.commit();

    }


}

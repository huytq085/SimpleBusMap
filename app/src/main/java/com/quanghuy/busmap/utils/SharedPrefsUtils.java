package com.quanghuy.busmap.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.quanghuy.busmap.Constants;
import com.quanghuy.busmap.entity.User;

/**
 * Created by Huy on 4/19/2018.
 */

public class SharedPrefsUtils {
    private static final String CURRENT_USER = "currentUser";
    public static User getCurrentUser(Context context) {
        SharedPreferences mPrefs = context.getSharedPreferences(Constants.PACKAGE_NAME, Context.MODE_PRIVATE);
        String currentUserString = mPrefs.getString(CURRENT_USER, "");
        User currentUser = JsonUtils.decode(currentUserString, User.class);
        return currentUser;
    }

    public static void setCurrentUser(Context context, User user) {
        SharedPreferences mPrefs = context.getSharedPreferences(Constants.PACKAGE_NAME, Context.MODE_PRIVATE);
        mPrefs.edit().putString(CURRENT_USER, JsonUtils.encode(user)).commit();
    }

    public static void removeCurrentUser(Context context) {
        SharedPreferences mPrefs = context.getSharedPreferences(Constants.PACKAGE_NAME, Context.MODE_PRIVATE);
        mPrefs.edit().remove(CURRENT_USER).commit();
    }
}

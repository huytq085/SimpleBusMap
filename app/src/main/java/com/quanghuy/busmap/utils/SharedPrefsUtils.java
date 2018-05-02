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

    public static SharedPreferences getPrefs(Context context) {
        return context.getSharedPreferences(Constants.PACKAGE_NAME, Context.MODE_PRIVATE);
    }

    public static User getCurrentUser(Context context) {
        String currentUserString = getPrefs(context).getString(CURRENT_USER, "");
        User currentUser = JsonUtils.decode(currentUserString, User.class);
        return currentUser;
    }

    public static void setCurrentUser(Context context, User user) {
        getPrefs(context).edit().putString(CURRENT_USER, JsonUtils.encode(user)).commit();
    }

    public static void removeCurrentUser(Context context) {
        getPrefs(context).edit().remove(CURRENT_USER).commit();
    }
}

package com.quanghuy.busmap.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.quanghuy.busmap.entity.User;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Huy on 4/4/2018.
 */

public class UserManager {
    private final String TAG = "UserManager";
    
    SQLiteOpenHelper dbhandler;
    SQLiteDatabase database;

    private static final String[] allColumns = {
            UserDBHandler.COLUMN_ID,
            UserDBHandler.COLUMN_USERNAME,
            UserDBHandler.COLUMN_PASSWORD,
            UserDBHandler.COLUMN_FIRST_NAME,
            UserDBHandler.COLUMN_LAST_NAME,
            UserDBHandler.COLUMN_GENDER,
    };

    public UserManager(Context context) {
        dbhandler = new UserDBHandler(context);
    }
    public void open(){
        Log.d(TAG, "open: Database opened");
        database = dbhandler.getWritableDatabase();
    }
    public void close(){
        Log.d(TAG, "close: Database closed");
        dbhandler.close();
    }

    public User addUser(User user) {
        ContentValues values = new ContentValues();
        values.put(UserDBHandler.COLUMN_FIRST_NAME, user.getFullName());
//        values.put(UserDBHandler.COLUMN_LAST_NAME, user.getLastName());
        values.put(UserDBHandler.COLUMN_GENDER, user.getGender());
        values.put(UserDBHandler.COLUMN_PASSWORD, user.getPassword());
        values.put(UserDBHandler.COLUMN_USERNAME, user.getUsername());
        long insertId = database.insert(UserDBHandler.TABLE_USER, null, values);
//        user.setUserId(insertId);
        return user;
    }

//    Getting single user
    public User getUser(String userName) {
        Cursor cursor = database.query(UserDBHandler.TABLE_USER, allColumns, UserDBHandler.COLUMN_USERNAME + "=?", new String[]{userName}, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
//        if (cursor.getCount() > 0){
//            User user = new User(cursor.getLong(0), cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getString(5));
//            return user;
//        }
        return null;
    }

    public List<User> getAllUsers(){
        Cursor cursor = database.query(UserDBHandler.TABLE_USER, allColumns, null, null, null, null, null);
//        if (cursor != null) {
//            cursor.moveToFirst();
//        }
        List<User> users = new ArrayList<>();
        if (cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                User user = new User();
//                user.setUserId(cursor.getLong(cursor.getColumnIndex(UserDBHandler.COLUMN_ID)));
                user.setUsername(cursor.getString(cursor.getColumnIndex(UserDBHandler.COLUMN_USERNAME)));
                user.setPassword(cursor.getString(cursor.getColumnIndex(UserDBHandler.COLUMN_PASSWORD)));
                user.setFullName(cursor.getString(cursor.getColumnIndex(UserDBHandler.COLUMN_FIRST_NAME)));
//                user.setLastName(cursor.getString(cursor.getColumnIndex(UserDBHandler.COLUMN_LAST_NAME)));
                user.setGender(cursor.getString(cursor.getColumnIndex(UserDBHandler.COLUMN_GENDER)));
//                User user = new User(cursor.getLong(0), cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getString(5));
                users.add(user);
            }
        }
        return users;
    }

    public int updateUser(User user) {
        ContentValues values = new ContentValues();
        values.put(UserDBHandler.COLUMN_FIRST_NAME, user.getFullName());
//        values.put(UserDBHandler.COLUMN_LAST_NAME, user.getLastName());
        values.put(UserDBHandler.COLUMN_GENDER, user.getGender());
        values.put(UserDBHandler.COLUMN_PASSWORD, user.getPassword());
        values.put(UserDBHandler.COLUMN_USERNAME, user.getUsername());
//        Updating row
        return database.update(UserDBHandler.TABLE_USER, values, UserDBHandler.COLUMN_ID + " =? ",new String[]{String.valueOf(user.getUserId())});
    }
}

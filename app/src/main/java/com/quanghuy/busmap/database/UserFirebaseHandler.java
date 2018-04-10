package com.quanghuy.busmap.database;

import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.quanghuy.busmap.entity.User;

import com.google.firebase.database.DatabaseReference;
import com.quanghuy.busmap.utils.JsonUtils;

/**
 * Created by Huy on 4/10/2018.
 */

public class UserFirebaseHandler {

    private boolean ok = true;

    private final String TAG = "UserFirebaseHandler";

    DatabaseReference refUser;

    public UserFirebaseHandler(){
        refUser = FirebaseDatabase.getInstance().getReference("User");
    }

    public void addUser(final User user, final OnCheckDataListener onCheckDataListener){
        Query query =refUser.orderByChild("userName").equalTo(user.getUserName());
        Log.i(TAG, "addUser: " + user.getUserName());
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    Log.i(TAG, "onDataChange: EXIST===================");
                    onCheckDataListener.onSuccess(false);
                } else {
                    String id = refUser.push().getKey();
                    user.setUserId(id);
                    refUser.child(id).setValue(user);
                    onCheckDataListener.onSuccess(true);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
    public void updateUser(User user){
        refUser.child(user.getUserId()).setValue(user);
    }
//    TODO: Need to access to userName = userName
    public void getUser(String userName, final OnGetDataListener onGetDataListener){
        Query query =refUser.orderByChild("userName").equalTo(userName);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    Log.d(TAG, "onDataChange: EXIST ===================================" + dataSnapshot.getKey());
                    User user = dataSnapshot.child(dataSnapshot.getKey()).getValue(User.class);
                    Log.d(TAG, "onDataChange: " + JsonUtils.encode(user));
                    onGetDataListener.onSuccess(user);
                } else {
                    Log.d(TAG, "onDataChange: NOT EXIST ===================================");
                    onGetDataListener.onFailed(null);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

}

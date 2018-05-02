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

import java.util.Map;

/**
 * Created by Huy on 4/10/2018.
 */

public class UserFirebaseHandler {

    private boolean ok = true;

    private final String TAG = "UserFirebaseHandler";

    DatabaseReference refUser;

    public UserFirebaseHandler(){
        refUser = FirebaseDatabase.getInstance().getReference("users");
    }

    public void updateUser(Map<String, Object> user) {
        refUser.updateChildren(user);
    }

    public void addUser(final User user, final OnCheckDataListener onCheckDataListener){
        Query query =refUser.orderByChild("userName").equalTo(user.getUsername());
        Log.i(TAG, "addUser: " + user.getUsername());
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
    public void getUser(String userName, final OnGetDataListener onGetDataListener){
        Log.d(TAG, "getUser: " + userName);
        Query query =refUser.orderByChild("userName").equalTo(userName);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (DataSnapshot data : dataSnapshot.getChildren()){
                        User user = data.getValue(User.class);
                        Log.i(TAG, "onDataChange: " + JsonUtils.encode(user));
                        onGetDataListener.onSuccess(user);
                    }

                } else {
                    Log.d(TAG, "onDataChange: ================= NOT EXIST ===================================");
                    onGetDataListener.onFailed(null);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

}

package com.quanghuy.busmap.database;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;

/**
 * Created by Huy on 4/11/2018.
 */

public interface OnGetDataListener {
    public void onStart();
    public void onSuccess(Object object);
    public void onFailed(Object object);
}

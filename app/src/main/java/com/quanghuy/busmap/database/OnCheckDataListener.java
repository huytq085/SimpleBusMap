package com.quanghuy.busmap.database;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;

/**
 * Created by Huy on 4/11/2018.
 */

public interface OnCheckDataListener {
    public void onStart();
    public void onSuccess(Boolean ok);
    public void onFailed(Boolean ok);
}

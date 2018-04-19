package com.quanghuy.busmap.ui.activity;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.quanghuy.busmap.R;
import com.quanghuy.busmap.SignUpActivity;
import com.quanghuy.busmap.entity.User;
import com.quanghuy.busmap.ui.adapters.SimpleDividerItemDecoration;
import com.quanghuy.busmap.ui.adapters.UserAdapter;

import java.util.ArrayList;
import java.util.List;

public class UserActivity extends AppCompatActivity {

    final String TAG = "UserActivity";
    FirebaseDatabase mDatabase;
    DatabaseReference mUserRef;

    UserAdapter mAdapter;

    RecyclerView recyclerView;
    List<User> result;

    public void setControl() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        setControl();

        mDatabase = FirebaseDatabase.getInstance();
        mUserRef = mDatabase.getReference("users");
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fabAddUser);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UserActivity.this, SignUpActivity.class);
                startActivity(intent);
            }
        });

        result = new ArrayList<>();
        recyclerView = findViewById(R.id.user_recycler_view);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(llm);
        recyclerView.addItemDecoration(new SimpleDividerItemDecoration(this));
        Log.d(TAG, "onCreate: 1=======================");
        mAdapter = new UserAdapter(result);
        Log.d(TAG, "onCreate: 2=======================");
        recyclerView.setAdapter(mAdapter);
        Log.d(TAG, "onCreate: 3=======================");
        updateList();
        Log.d(TAG, "onCreate: 4=======================");
    }

    public void removeUser(int position) {
        mUserRef.child(result.get(position).getUserId()).removeValue();
    }

    public void updateUser(int position) {
        Intent intent = new Intent(this, SignUpActivity.class);
        intent.putExtra("user", result.get(position));
        startActivity(intent);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case 0:
                updateUser(item.getGroupId());
                break;
            case 1:
                removeUser(item.getGroupId());
                break;

        }
        return super.onContextItemSelected(item);
    }

    public int getItemIndex(User user) {
        int index = -1;
        for (User userResult : result) {
            if (user.getUserId().equals(userResult.getUserId())) {
                index = result.indexOf(userResult);
            }
        }
        return index;
    }


    public void updateList() {
        mUserRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Log.d(TAG, "onChildAdded: KEY " + dataSnapshot.getKey());
                User user = dataSnapshot.getValue(User.class);
                if (!user.getUserName().equals("admin")) {
                    result.add(user);
                    mAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                User user = dataSnapshot.getValue(User.class);
                int index = getItemIndex(user);
                if (index >= 0) {
                    result.set(index, user);
                }
                mAdapter.notifyItemChanged(index);

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);
                int index = getItemIndex(user);
                Log.d(TAG, "onChildRemoved: index: " + index);
                if (index >= 0) {
                    result.remove(index);
                }
                mAdapter.notifyItemRemoved(index);
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}

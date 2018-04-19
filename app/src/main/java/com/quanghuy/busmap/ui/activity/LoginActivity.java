package com.quanghuy.busmap.ui.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import com.quanghuy.busmap.Constants;
import com.quanghuy.busmap.R;
import com.quanghuy.busmap.SignUpActivity;
import com.quanghuy.busmap.database.OnGetDataListener;
import com.quanghuy.busmap.database.UserFirebaseHandler;
import com.quanghuy.busmap.database.UserManager;
import com.quanghuy.busmap.entity.User;
import com.quanghuy.busmap.utils.JsonUtils;
import com.quanghuy.busmap.utils.SharedPrefsUtils;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class LoginActivity extends AppCompatActivity {
    private static final String TAG = "LoginActivity";
    public static Activity fa;

    @InjectView(R.id.txtUserName) EditText txtUserName;
    @InjectView(R.id.txtPassword) EditText txtPassword;
    @InjectView(R.id.btnSignin) Button btnSignin;
    @InjectView(R.id.signupLink) TextView signupLink;

    private UserManager userManager;
    private UserFirebaseHandler userFirebaseHandler;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        User currentUser = SharedPrefsUtils.getCurrentUser(this);
        Log.d(TAG, "onCreate: " + JsonUtils.encode(currentUser));

        if (currentUser != null) {
            Intent i = new Intent(this, MainActivity.class);
            startActivity(i);
            finish();
        }
        setContentView(R.layout.activity_login);
        fa = this;
        ButterKnife.inject(this);

        userManager = new UserManager(this);
        userManager.open();

        userFirebaseHandler = new UserFirebaseHandler();

        signupLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), SignUpActivity.class);
                startActivity(intent);
            }
        });
        btnSignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
//                    User user = userManager.getUser(txtUserName.getText().toString());
                    final User[] user = {null};
                    userFirebaseHandler.getUser(txtUserName.getText().toString(), new OnGetDataListener() {
                        @Override
                        public void onStart() {

                        }

                        @Override
                        public void onSuccess(Object object) {
                            Log.d(TAG, "onSuccess: object " + JsonUtils.encode((User) object));
                            user[0] = (User) object;
                            Log.d(TAG, "onSuccess: " + JsonUtils.encode(user[0]));
                            if (user[0] != null && checkValid(user[0])) {
                                SharedPrefsUtils.setCurrentUser(LoginActivity.this, user[0]);
                                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                intent.putExtra("user", user[0]);
                                startActivity(intent);
                            } else {
                                Log.d(TAG, "Sai cmnr");
                                Toast.makeText(LoginActivity.this, "Sai thông tin đăng nhập! Hãy nhập lại.", Toast.LENGTH_LONG).show();
                            }
                        }

                        @Override
                        public void onFailed(Object object) {
                            Log.d(TAG, "Sai cmnr");
                            Toast.makeText(LoginActivity.this, "Sai thông tin đăng nhập! Hãy nhập lại.", Toast.LENGTH_LONG).show();
                        }
                    });

                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });
    }
    public boolean checkValid(User user) {
        String password = txtPassword.getText().toString();
        return user.getPassword().equals(password);
    }


}
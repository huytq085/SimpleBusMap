package com.quanghuy.busmap.ui.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
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
import com.quanghuy.busmap.client.RouteAPIClient;
import com.quanghuy.busmap.client.UserAPIClient;
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

    @InjectView(R.id.txtUserName)
    EditText txtUserName;
    @InjectView(R.id.txtPassword)
    EditText txtPassword;
    @InjectView(R.id.btnSignin)
    Button btnSignin;
    @InjectView(R.id.signupLink)
    TextView signupLink;

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
//        button login
        btnSignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
//                    User user = userManager.getUser(txtUserName.getText().toString());
                    new LoginTask().execute(txtUserName.getText().toString().toLowerCase(), txtPassword.getText().toString());
//
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

    class LoginTask extends AsyncTask<String, Void, Boolean> {

        @Override
        protected Boolean doInBackground(String... strings) {
            String username = strings[0];
            String password = strings[1];
            UserAPIClient client = new UserAPIClient();
            User user = client.login(username, password);
            if (user != null) {
                SharedPrefsUtils.setCurrentUser(LoginActivity.this, user);
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
            return false;
        }
    }

}
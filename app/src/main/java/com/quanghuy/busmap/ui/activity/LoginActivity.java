package com.quanghuy.busmap.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import com.quanghuy.busmap.R;
import com.quanghuy.busmap.SignUpActivity;
import com.quanghuy.busmap.database.UserManager;
import com.quanghuy.busmap.entity.User;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class LoginActivity extends AppCompatActivity {
    private static final String TAG = "SignupActivity";

    @InjectView(R.id.txtUserName) EditText txtUserName;
    @InjectView(R.id.txtPassword) EditText txtPassword;
    @InjectView(R.id.btnSignin) Button btnSignin;
    @InjectView(R.id.signupLink) TextView signupLink;

    private UserManager userManager;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.inject(this);

        userManager = new UserManager(this);
        userManager.open();

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
                    User user = userManager.getUser(txtUserName.getText().toString());
                    if (user != null && checkValid(user)) {
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        intent.putExtra("user", user);
                        startActivity(intent);
                    } else {
                        Log.d(TAG, "Sai cmnr");
                        Toast.makeText(LoginActivity.this, "Sai thông tin đăng nhập! Hãy nhập lại.", Toast.LENGTH_LONG).show();
                    }
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
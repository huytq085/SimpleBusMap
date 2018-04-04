package com.quanghuy.busmap;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.quanghuy.busmap.entity.User;
import com.quanghuy.busmap.database.UserManager;
import com.quanghuy.busmap.utils.JsonUtils;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class SignUpActivity extends AppCompatActivity {
    private final String TAG = "SignUp";

    @InjectView(R.id.btnSignup)
    Button btnSignup;
    @InjectView(R.id.txtUserName)
    EditText txtUserName;
    @InjectView(R.id.txtPassword)
    EditText txtPassword;
    @InjectView(R.id.txtFirstName)
    EditText txtFirstName;
    @InjectView(R.id.txtLastName)
    EditText txtLastName;
    @InjectView(R.id.radioGender)
    RadioGroup rgGender;
    RadioButton rbGender;

    private UserManager userManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        ButterKnife.inject(this);

        userManager = new UserManager(this);
        userManager.open();

        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                User user = new User();
                user.setUserName(txtUserName.getText().toString());
                user.setPassword(txtPassword.getText().toString());
                user.setFirstName(txtFirstName.getText().toString());
                user.setLastName(txtLastName.getText().toString());
                user.setUserName(txtUserName.getText().toString());
                rbGender = findViewById(rgGender.getCheckedRadioButtonId());
                user.setGender(rbGender.getText().toString());
                Log.d(TAG, "user object: " + JsonUtils.encode(user));
                try {
                    user = userManager.addUser(user);
                    Toast.makeText(SignUpActivity.this, "Successful!", Toast.LENGTH_LONG).show();
                } catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
    }

}


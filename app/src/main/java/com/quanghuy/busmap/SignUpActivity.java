package com.quanghuy.busmap;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.quanghuy.busmap.database.OnCheckDataListener;
import com.quanghuy.busmap.database.UserFirebaseHandler;
import com.quanghuy.busmap.entity.User;
import com.quanghuy.busmap.database.UserManager;
import com.quanghuy.busmap.utils.JsonUtils;

import java.util.HashMap;
import java.util.Map;

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
    @InjectView(R.id.btnLogin)
    TextView tvLogin;

    private UserManager userManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        ButterKnife.inject(this);

        userManager = new UserManager(this);
        userManager.open();

        final UserFirebaseHandler userFirebaseHandler = new UserFirebaseHandler();


        tvLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        Intent intent = getIntent();
        if (intent.hasExtra("user")) {
            tvLogin.setText("Quay lại");
            final User user = (User) intent.getSerializableExtra("user");

            txtUserName.setText(user.getUserName());
            txtLastName.setText(user.getLastName());
            txtFirstName.setText(user.getFirstName());
            txtPassword.setText(user.getPassword());
            if (user.getGender().equals("Nam")) {
                rbGender = findViewById(R.id.radio_male);
                rbGender.setChecked(true);
            } else {
                rbGender = findViewById(R.id.radio_female);
                rbGender.setChecked(true);
            }
            btnSignup.setText("Lưu");
            btnSignup.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    user.setUserName(txtUserName.getText().toString());
                    user.setPassword(txtPassword.getText().toString());
                    user.setFirstName(txtFirstName.getText().toString());
                    user.setLastName(txtLastName.getText().toString());
                    user.setUserName(txtUserName.getText().toString());
                    rbGender = findViewById(rgGender.getCheckedRadioButtonId());

                    user.setGender(rbGender.getText().toString());

                    Map<String, Object> userValue = user.toMap();
                    Map<String, Object> newUser = new HashMap<>();
                    newUser.put(user.getUserId(), userValue);
                    userFirebaseHandler.updateUser(newUser);
                    Toast.makeText(SignUpActivity.this, "Successful", Toast.LENGTH_LONG).show();
                }
            });
        } else {
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
//                    user = userManager.addUser(user);
                        userFirebaseHandler.addUser(user, new OnCheckDataListener() {
                            @Override
                            public void onStart() {

                            }

                            @Override
                            public void onSuccess(Boolean ok) {
                                if (ok){
                                    Toast.makeText(SignUpActivity.this, "Successful", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(SignUpActivity.this, "Failed", Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onFailed(Boolean ok) {

                            }
                        });

                    } catch (Exception e){
                        e.printStackTrace();
                    }
                }
            });
        }

    }

}


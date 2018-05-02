package com.quanghuy.busmap;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.quanghuy.busmap.client.RouteAPIClient;
import com.quanghuy.busmap.client.UserAPIClient;
import com.quanghuy.busmap.database.OnCheckDataListener;
import com.quanghuy.busmap.database.UserFirebaseHandler;
import com.quanghuy.busmap.entity.Route;
import com.quanghuy.busmap.entity.User;
import com.quanghuy.busmap.database.UserManager;
import com.quanghuy.busmap.ui.activity.NewRouteActivity;
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

            txtUserName.setText(user.getUsername());
//            txtLastName.setText(user.getLastName());
            txtFirstName.setText(user.getFullName());
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
                    user.setUsername(txtUserName.getText().toString());
                    user.setPassword(txtPassword.getText().toString());
                    user.setFullName(txtFirstName.getText().toString() + " " + txtLastName.getText().toString());
                    user.setUsername(txtUserName.getText().toString());
                    rbGender = findViewById(rgGender.getCheckedRadioButtonId());
                    user.setGender(rbGender.getText().toString());
                    new UpdateUserTask().execute(user);
                    Toast.makeText(SignUpActivity.this, "Successful", Toast.LENGTH_LONG).show();
                }
            });
        } else {
            btnSignup.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    User user = new User();
                    user.setUsername(txtUserName.getText().toString());
                    user.setPassword(txtPassword.getText().toString());
                    user.setFullName(txtFirstName.getText().toString() + " " + txtLastName.getText().toString());
                    user.setUsername(txtUserName.getText().toString());
                    rbGender = findViewById(rgGender.getCheckedRadioButtonId());

                    user.setGender(rbGender.getText().toString());
                    Log.d(TAG, "user object: " + JsonUtils.encode(user));
                    new RegisterTask().execute(user);
                }
            });
        }

    }
    class RegisterTask extends AsyncTask<User, Void, Boolean> {

        @Override
        protected Boolean doInBackground(User... users) {
            User user = users[0];
            UserAPIClient client = new UserAPIClient();

//            addRoute
            return client.register(user);
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
            AlertDialog alertDialog = new AlertDialog.Builder(SignUpActivity.this).create();
            alertDialog.setTitle("Alert");
            alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
            if (aBoolean) {
                alertDialog.setMessage("Successful!");
            } else {
                alertDialog.setMessage("Error");
            }
            alertDialog.show();
        }
    }
    class UpdateUserTask extends AsyncTask<User, Void, Boolean> {

        @Override
        protected Boolean doInBackground(User... users) {
            User user = users[0];
            UserAPIClient client = new UserAPIClient();

//            addRoute
            return client.update(user);
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
            AlertDialog alertDialog = new AlertDialog.Builder(SignUpActivity.this).create();
            alertDialog.setTitle("Alert");
            alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
            if (aBoolean) {
                alertDialog.setMessage("Successful!");
            } else {
                alertDialog.setMessage("Error");
            }
            alertDialog.show();
        }
    }

}


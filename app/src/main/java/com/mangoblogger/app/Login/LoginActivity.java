package com.mangoblogger.app.Login;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import com.mangoblogger.app.AppUtils;
import com.mangoblogger.app.MainActivity;
import com.mangoblogger.app.PreferenceUtil;
import com.mangoblogger.app.R;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class LoginActivity extends BaseAuthActivity {

    private static final String TAG = "Main_Activity";

    private ProgressDialog mProgressDialog;
    private EditText inputEmail, inputPassword;
    private AuthApi mAuthApi;
    private TextView mErrorTextView;
    private ProgressBar progressBar;
    private Button btnSignup, btnLogin, btnReset;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // set the view now
        setContentView(R.layout.activity_login);

        mAuthApi = initAdapter();

        inputEmail = (EditText) findViewById(R.id.email);
        inputPassword = (EditText) findViewById(R.id.password);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        btnSignup = (Button) findViewById(R.id.btn_signup);
        btnLogin = (Button) findViewById(R.id.btn_login);
        btnReset = (Button) findViewById(R.id.btn_reset_password);
        mErrorTextView = (TextView) findViewById(R.id.error_text);





        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = inputEmail.getText().toString();
                String password = inputPassword.getText().toString();
                if(!validateForm(inputEmail, inputPassword)) {
                    return;
                }
                signIn(email, password);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        if(PreferenceUtil.isSignedIn(this)) {
            startApp();
        }
    }




    private void signIn(String emailId, String password) {
        if (!AppUtils.isNetworkConnected(this)) {
            showErrorDialog();
        } else {
            showProgressDialog();
            mAuthApi.loginUser(emailId, password, new Callback<LoginResponse>() {
                @Override
                public void success(LoginResponse loginResponse, Response response) {
                    if(loginResponse.getStatus().equals("ok")) {
                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        loginResponse.getUser().setLoggedIn(true);
                        PreferenceUtil.writeUserToPreferences(getApplicationContext(), loginResponse.getUser());
                        startApp();
                    } else {
                        mErrorTextView.setText(loginResponse.getError());
                    }
                    hideProgressDialog();
                }

                @Override
                public void failure(RetrofitError error) {
                    hideProgressDialog();
                    Log.e("Failure : NonceId", error.getLocalizedMessage());
                    Toast.makeText(getApplicationContext(), error.getLocalizedMessage(), Toast.LENGTH_LONG).show();
                }
            });
        }

    }




    private boolean validateForm(EditText emailField, EditText passwordField) {
        boolean result = true;
        if (TextUtils.isEmpty(emailField.getText().toString())) {
            emailField.setError("Required");
            result = false;
        } else {
            emailField.setError(null);
        }

        if (TextUtils.isEmpty(passwordField.getText().toString())) {
            passwordField.setError("Required");
            result = false;
        } else {
            passwordField.setError(null);
        }

        return result;
    }
}


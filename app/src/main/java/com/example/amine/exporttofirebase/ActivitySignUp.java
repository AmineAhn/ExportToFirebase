package com.example.amine.exporttofirebase;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import java.util.Arrays;

public class ActivitySignUp extends AppCompatActivity {
    TextView        txtStatus      ;
    LoginButton     loginButton    ;
    CallbackManager callbackManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState)                ;
        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.activity_sign_up)         ;
        initializeControllers()                           ;
        FacebookLoginAction()                                   ;

    }

    public void initializeControllers()
    {
        callbackManager = CallbackManager.Factory.create();
        txtStatus       = (TextView) findViewById(R.id.txtStatus);
        loginButton     = (LoginButton)findViewById(R.id.loginButton);

    }

    private void FacebookLoginAction()
    {
        LoginManager.getInstance().registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult)
            {
                txtStatus.setText("LOGIN SUCCESFUL: " + loginResult.getAccessToken());
            }

            @Override
            public void onCancel()
            {
                txtStatus.setText("LOGIN CANCELLED");
            }

            @Override
            public void onError(FacebookException error)
            {
                txtStatus.setText("LOGIN ERROR: " + error.getMessage());
            }
        });
        LoginManager.getInstance().logInWithReadPermissions(this, Arrays.asList("public_profile"));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }
}

package com.example.amine.exporttofirebase;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.Profile;
import com.facebook.ProfileTracker;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import java.util.Arrays;

public class ActivitySignUp extends AppCompatActivity {
    private TextView           txtStatus                  ;
    private LoginButton        loginButton                ;
    private CallbackManager    callbackManager            ;
    private AccessTokenTracker accessTokenTracker         ;
    private ProfileTracker     profileTracker             ;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState)                ;
        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.activity_sign_up)         ;
        initializeControllers()                           ;
        startTracking()                                   ;
        FacebookLoginAction()                             ;

    }

    @Override
    protected void onResume()
    {
        super.onResume();
        Profile profile = Profile.getCurrentProfile();
        nextActivity(profile);
    }
    @Override
    protected void onPause()
    {
        super.onPause();
    }
    @Override
    protected void onStop()
    {
        super.onStop();
        stopTracking();
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }
    public void initializeControllers()
    {
        callbackManager    = CallbackManager.Factory.create()           ;
        //txtStatus          = (TextView) findViewById(R.id.txtStatus)    ;
        loginButton        = (LoginButton)findViewById(R.id.loginButton);
        accessTokenTracker = new AccessTokenTracker()
        {
            @Override
            protected void onCurrentAccessTokenChanged(AccessToken oldToken, AccessToken currentToken) {

            }
        };
        profileTracker     = new ProfileTracker()
        {
            @Override
            protected void onCurrentProfileChanged(Profile oldProfile, Profile newProfile)
            {
                nextActivity(newProfile);
            }
        };

    }

    private void FacebookLoginAction()
    {
        FacebookCallback<LoginResult> facebookCallback = new FacebookCallback<LoginResult>()
        {
            @Override
            public void onSuccess(LoginResult loginResult)
            {
                Profile profile = Profile.getCurrentProfile();
                Toast.makeText(getApplicationContext(), "Loggin in ... ", Toast.LENGTH_SHORT).show();
                //txtStatus.setText("LOGIN SUCCESFUL: " + loginResult.getAccessToken());
                nextActivity(profile);
            }

            @Override
            public void onCancel()
            {
                //txtStatus.setText("LOGIN CANCELLED");
            }

            @Override
            public void onError(FacebookException error)
            {
                //txtStatus.setText("LOGIN ERROR: " + error.getMessage());
            }
        };
        LoginManager.getInstance().registerCallback(callbackManager, facebookCallback) ;
        LoginManager.getInstance().logInWithReadPermissions(this, Arrays.asList("user_photos"));
        loginButton.registerCallback(callbackManager, facebookCallback );
    }


    private void startTracking()
    {
        accessTokenTracker.startTracking();
        profileTracker.startTracking()    ;
    }
    private void stopTracking()
    {
        accessTokenTracker.startTracking();
        profileTracker.startTracking()    ;
    }

    private void nextActivity(Profile profile)
    {
        if(profile != null)
        {
            Intent main = new Intent(ActivitySignUp.this, ActivityUserProfile.class);
            main.putExtra("name", profile.getFirstName());
            main.putExtra("surname", profile.getLastName());
            main.putExtra("imageUrl", profile.getProfilePictureUri(200,200));
        }
    }
}

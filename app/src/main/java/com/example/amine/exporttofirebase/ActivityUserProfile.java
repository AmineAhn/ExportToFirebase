package com.example.amine.exporttofirebase;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.FacebookSdk;
import com.facebook.login.LoginManager;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.widget.ShareDialog;

import java.io.InputStream;
import java.io.InputStreamReader;

public class ActivityUserProfile extends AppCompatActivity {

    private ShareDialog shareDialog ;
    private Button      logoutButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        System.out.println(" HEEEEEEEEEEEEEy");
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.activity_user_profile);
        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        shareDialog = new ShareDialog(this);
        FloatingActionButton fab = (FloatingActionButton)findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                ShareLinkContent content = new ShareLinkContent.Builder().build();
                shareDialog.show(content);

            }
        });

        Bundle inBundle = getIntent().getExtras();
        String name     = inBundle.get("name").toString();
        String last     = inBundle.get("surname").toString();
        String imageUrl = inBundle.get("imageUrl").toString();

        TextView nameView = (TextView)findViewById(R.id.username);
        nameView.setText(name + " " + last);
        Button logoutButton = (Button)findViewById(R.id.logoutBtn);
        logoutButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view)
            {
                LoginManager.getInstance().logOut();
                Intent login = new Intent(ActivityUserProfile.this, ActivitySignUp.class);
                startActivity(login);
                finish();
            }
        });
        new ActivityUserProfile.DownloadImage((ImageView)findViewById(R.id.profileImg)).execute(imageUrl);
    }


    public class DownloadImage extends AsyncTask<String, Void, Bitmap>
    {
        ImageView bmImage;
        public DownloadImage(ImageView bmImage)
        {
            this.bmImage = bmImage;
        }

        protected Bitmap doInBackground(String... urls)
        {
            String urldisplay = urls[0];
            Bitmap mIconll    = null;

            try
            {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIconll = BitmapFactory.decodeStream(in);
            }catch(Exception e)
            {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return mIconll;
        }

        protected void onPostExecute(Bitmap result)
        {
            bmImage.setImageBitmap(result);
        }
    }

}

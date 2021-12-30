package com.MCure.MCure;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.DexterError;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.PermissionRequestErrorListener;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.MCure.MCure.others.APPCONSTANT;
import com.MCure.MCure.others.SharedHelper;

import java.util.List;

public class SplashScreen extends AppCompatActivity {
    String UserId ="";
    String Type = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        UserId  = SharedHelper.getKey(SplashScreen.this, APPCONSTANT.USERID);
        Type= SharedHelper.getKey(SplashScreen.this, APPCONSTANT.TYPE);
        Dexter.withContext(this)
                .withPermissions(
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.CAMERA,
                        Manifest.permission.INTERNET

                )
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report) {
                        Log.e("urer", Type);
                        Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {

                                if (Type.equals("")){

                                    if (UserId.equals("")){
                                        Log.e("fgjhgkh","first");
                                        startActivity(new Intent(SplashScreen.this,SelectScreenActivity.class));    // docter side

                                    } else {
                                        Log.e("fgjhgkh","second");

                                       // startActivity(new Intent(SplashScreen.this,NavActivity.class));
                                    }

                                }

                             else  if (Type.equals("1")){
                                    if (UserId.equals("")){
                                        Log.e("fgjhgkh","first");
                                        startActivity(new Intent(SplashScreen.this,SelectScreenActivity.class));    // docter side

                                    } else {
                                        Log.e("fgjhgkh","second");

                                        startActivity(new Intent(SplashScreen.this,NavActivity.class));
                                    }
                                }
                                if (Type.equals("2")) {
                                    if (UserId.equals("")) {
                                        Log.e("fgjhgkh","third");

                                        startActivity(new Intent(SplashScreen.this, SelectScreenActivity.class));    //lab side

                                    } else {
                                        Log.e("fgjhgkh","fourth");

                                        startActivity(new Intent(SplashScreen.this, LabHomeActivity.class));
                                    }
                                }
                            }
                        },2000);
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                        token.continuePermissionRequest();
                    }
                }).
                withErrorListener(new PermissionRequestErrorListener() {
                    @Override
                    public void onError(DexterError error) {
                        Toast.makeText(getApplicationContext(), "Error occurred! ", Toast.LENGTH_SHORT).show();
                    }
                })
                .onSameThread()
                .check();



       /* Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
              if (Type.equals("1")){
                  if (UserId.equals("")){
                      startActivity(new Intent(SplashScreen.this,SelectScreenActivity.class));    // docter side

                  } else {
                      startActivity(new Intent(SplashScreen.this,NavActivity.class));
                  }
              }
                if (Type.equals("2")) {
                    if (UserId.equals("")) {
                        startActivity(new Intent(SplashScreen.this, SelectScreenActivity.class));    //lab side

                    } else {
                        startActivity(new Intent(SplashScreen.this, LabHomeActivity.class));
                    }
                }
            }
        },2000);*/
    }
}

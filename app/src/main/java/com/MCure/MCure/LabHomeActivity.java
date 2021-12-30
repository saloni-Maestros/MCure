package com.MCure.MCure;


import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;


import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.MCure.MCure.others.APPCONSTANT;
import com.MCure.MCure.others.SharedHelper;

public class LabHomeActivity extends AppCompatActivity {

  ImageView iv_back_account,iv_back_labtest,iv_back_order,iv_package,img_notification,iv_logout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_labhome);
        img_notification = findViewById(R.id.img_notification);
        iv_back_labtest=findViewById(R.id.iv_back_labtest);
        iv_back_account=findViewById(R.id.iv_back_account);
        iv_back_order = findViewById(R.id.iv_back_order);
        iv_logout = findViewById(R.id.iv_logout);
        iv_package=findViewById(R.id.iv_package);
        iv_package.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LabHomeActivity.this,ShowPackageTestActivity.class));
            }
        });
        iv_back_account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LabHomeActivity.this,LabAccountActivity.class));
            }
        });
        iv_back_labtest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LabHomeActivity.this, LabShowPackageActivity.class));
            }
        });
        img_notification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        iv_back_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LabHomeActivity.this, LabOrderActivity.class));

            }
        });
        iv_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    builder = new AlertDialog.Builder(LabHomeActivity.this, android.R.style.Theme_Material_Light_Dialog_Alert);
                } else {
                    builder = new AlertDialog.Builder(LabHomeActivity.this);
                }
                builder.setTitle(getResources().getString(R.string.app_name))
                        .setMessage("Are you sure you want to logout this app")
                        .setPositiveButton(Html.fromHtml("<font color='#0095B7'>Ok</font>"), new DialogInterface.OnClickListener() {
                            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)

                            public void onClick(final DialogInterface dialog, int which) {

                                Toast.makeText(LabHomeActivity.this, " Successfully logout..", Toast.LENGTH_LONG).show();
                                // startActivity(new Intent(NavActivity.this, SplashScreen.class));
                                SharedHelper.putkey(LabHomeActivity.this, APPCONSTANT.USERID,"");
                                startActivity(new Intent(LabHomeActivity.this, SplashScreen.class));
                                Log.e("fghgfhfghf","DONE");

                            }
                        })
                        .setNegativeButton(Html.fromHtml("<font color='#0095B7'>Cancel</font>"), new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {

                                Toast.makeText(LabHomeActivity.this, " Not logout..", Toast.LENGTH_LONG).show();
                                dialog.dismiss();

                            }
                        })
                        .setIcon(R.drawable.arogyalogo)
                        .show();

            }
        });



    }
}
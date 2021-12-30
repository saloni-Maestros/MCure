package com.MCure.MCure;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.MCure.MCure.Fragment.HomeFragment;
import com.MCure.MCure.others.APPCONSTANT;
import com.MCure.MCure.others.SharedHelper;

public class NavActivity extends AppCompatActivity implements View.OnClickListener {
public static DrawerLayout drawer_layout;
NavigationView nav_view;
LinearLayout LinearLayoutConsultation;
LinearLayout LinearLayoutCustomer;
LinearLayout LinearLayoutProfile;
LinearLayout LinearLayoutSetting;
LinearLayout LinearLayoutCalender;
LinearLayout LinearLayoutLogOut;
LinearLayout LinearLayoutEarning;
LinearLayout LinearLayoutReport;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nav);
        drawer_layout=findViewById(R.id.drawer_layout);
        nav_view=findViewById(R.id.nav_view);
        LinearLayoutConsultation=findViewById(R.id.LinearLayoutConsultation);
        LinearLayoutCustomer = findViewById(R.id.LinearLayoutCustomer);
        LinearLayoutProfile = findViewById(R.id.LinearLayoutProfile);
        LinearLayoutSetting = findViewById(R.id.LinearLayoutSetting);
        LinearLayoutCalender=findViewById(R.id.LinearLayoutCalender);
        LinearLayoutLogOut=findViewById(R.id.LinearLayoutLogOut);
        LinearLayoutEarning=findViewById(R.id.LinearLayoutEarning);
        LinearLayoutReport = findViewById(R.id.LinearLayoutReport);
        LinearLayoutReport.setOnClickListener(this);
        LinearLayoutSetting.setOnClickListener(this);
        LinearLayoutProfile.setOnClickListener(this);
        LinearLayoutConsultation.setOnClickListener(this);
        LinearLayoutCustomer.setOnClickListener(this);
        LinearLayoutCalender.setOnClickListener(this);
        LinearLayoutLogOut.setOnClickListener(this);
        LinearLayoutEarning.setOnClickListener(this);

        getSupportFragmentManager().beginTransaction().replace(R.id.item_container, new HomeFragment()).commit();
    }




    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.LinearLayoutConsultation:
           //     startActivity(new Intent(NavActivity.this,ConsultationChargeActivity.class));
                break;
            case R.id.LinearLayoutCustomer:
                startActivity(new Intent(NavActivity.this,CustomSupportActivity.class));
                break;
            case R.id.LinearLayoutProfile:
                startActivity(new Intent(NavActivity.this,ProfileSettingActivity.class));
                break;
            case R.id.LinearLayoutSetting:
               startActivity(new Intent(NavActivity.this,ChangeMobNoActivity.class));
                break;

            case R.id.LinearLayoutCalender:
                startActivity(new Intent(NavActivity.this, CalendarActivity.class));
                break;

            case R.id.LinearLayoutReport:
                startActivity(new Intent(NavActivity.this, ReportScreenActivity.class));
                break;
                //startActivity(new Intent(NavActivity.this,CalendarActivity.class));

            case R.id.LinearLayoutEarning:
                startActivity(new Intent(NavActivity.this,ConsultationChargeActivity.class));
                break;

            case R.id.LinearLayoutLogOut:
                LinearLayoutLogOut.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        AlertDialog.Builder builder;
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                            builder = new AlertDialog.Builder(NavActivity.this, android.R.style.Theme_Material_Light_Dialog_Alert);
                        } else {
                            builder = new AlertDialog.Builder(NavActivity.this);
                        }
                        builder.setTitle(getResources().getString(R.string.app_name))
                                .setMessage("Are you sure you want to logout this app")
                                .setPositiveButton(Html.fromHtml("<font color='#0095B7'>Ok</font>"), new DialogInterface.OnClickListener() {
                                    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)

                                    public void onClick(final DialogInterface dialog, int which) {

                                        Toast.makeText(NavActivity.this, " Successfully logout..", Toast.LENGTH_LONG).show();
                                       // startActivity(new Intent(NavActivity.this, SplashScreen.class));
                                        SharedHelper.putkey(NavActivity.this, APPCONSTANT.USERID,"");
                                        startActivity(new Intent(NavActivity.this, SplashScreen.class));
                                        Log.e("fghgfhfghf","DONE");

                                    }
                                })
                                .setNegativeButton(Html.fromHtml("<font color='#0095B7'>Cancel</font>"), new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {

                                        Toast.makeText(NavActivity.this, " Not logout..", Toast.LENGTH_LONG).show();
                                        dialog.dismiss();

                                    }
                                })
                                .setIcon(R.drawable.arogyalogo)
                                .show();
                    }
                });
                break;


    }


      /* LinearLayoutLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    builder = new AlertDialog.Builder(NavActivity.this, android.R.style.Theme_Material_Light_Dialog_Alert);
                } else {
                    builder = new AlertDialog.Builder(NavActivity.this);
                }
                builder.setTitle(getResources().getString(R.string.app_name))
                        .setMessage("Are you sure you want to logout in the app")
                        .setPositiveButton(Html.fromHtml("<font color='#BB0D81'>Ok</font>"), new DialogInterface.OnClickListener() {
                            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)

                            public void onClick(final DialogInterface dialog, int which) {

                                Toast.makeText(NavActivity.this, " Successfuly logout..", Toast.LENGTH_LONG).show();
                                SharedHelper.putkey(NavActivity.this, APPCONSTANT.USERID,"");
                               startActivity(new Intent(NavActivity.this, SplashScreen.class));
                                Log.e("fghgfhfghf","DONE");

                            }
                        })
                        .setNegativeButton(Html.fromHtml("<font color='#BB0D81'>Cancel</font>"), new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {

                                dialog.dismiss();

                            }
                        })
                        .setIcon(R.drawable.arogyalogo)
                        .show();
            }
        }); */
}}


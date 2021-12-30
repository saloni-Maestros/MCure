package com.MCure.MCure;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;
import com.MCure.MCure.others.APPCONSTANT;
import com.MCure.MCure.others.SharedHelper;

public class SelectScreenActivity extends AppCompatActivity {
    MaterialButton materialbuttonnext;
    RadioGroup radiogroup_doctor, radiogroup_lab;
    RadioButton radio_btn_doctor, radio_btn_lab;
    String Type = "";
    ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_screen);
        progressBar = findViewById(R.id.progressBar);
        radio_btn_doctor = findViewById(R.id.radio_btn_doctor);
        /*radiogroup_doctor=findViewById(R.id.radiogroup_doctor);
        radiogroup_lab=findViewById(R.id.radiogroup_lab);*/
        radio_btn_lab = findViewById(R.id.radio_btn_lab);
        materialbuttonnext = findViewById(R.id.materialbuttonnext);


        materialbuttonnext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedHelper.putkey(SelectScreenActivity.this, APPCONSTANT.TYPE, Type);
                startActivity(new Intent(SelectScreenActivity.this, LoginActivity.class));
                Log.e("tgrtrtretfrr", Type);
                progressBar.setVisibility(View.VISIBLE);
            }
        });
        radio_btn_doctor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Type = "1";
                radio_btn_doctor.setChecked(true);
                radio_btn_lab.setChecked(false);
                Log.e("rtgrgt", Type);
                progressBar.setVisibility(View.GONE);
            }
        });

        radio_btn_lab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //      SharedHelper.putkey(SelectScreenActivity.this, APPCONSTANT.TYPE,getString(Integer.parseInt("2")));
                Type = "2";
                radio_btn_doctor.setChecked(false);
                radio_btn_lab.setChecked(true);
                progressBar.setVisibility(View.GONE);


            }
        });

    }
}
package com.MCure.MCure;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.google.android.material.button.MaterialButton;
import com.MCure.MCure.others.API;
import com.MCure.MCure.others.APPCONSTANT;
import com.MCure.MCure.others.SharedHelper;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class LabPackageActivity extends AppCompatActivity {
   ImageView iv_backarrow;
    Spinner pac_spinner;
    ArrayList<String> arrayList;
    String product_type="";
    String UserId;

    EditText ed_title_sp,ed_des_sp,ed_labcharge;
    MaterialButton mbtn_labtest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lab_package);
        iv_backarrow = findViewById(R.id.iv_backarrow);
        iv_backarrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        ed_title_sp=findViewById(R.id.ed_title_sp);
        ed_des_sp=findViewById(R.id.ed_des_sp);
        ed_labcharge=findViewById(R.id.ed_labcharge);
        mbtn_labtest=findViewById(R.id.mbtn_labtest);
        UserId = SharedHelper.getKey(LabPackageActivity.this, APPCONSTANT.USERID);
        Log.e("userid", UserId);




        mbtn_labtest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                add_lab_package();
            }
        });



    }

    private void add_lab_package() {

        Log.e("UserId", UserId);
        AndroidNetworking.post(API.add_lab_package)
                .addBodyParameter("user_id",UserId)
                .addBodyParameter("price",ed_labcharge.getText().toString().trim())
                .addBodyParameter("title",ed_title_sp.getText().toString().trim())
                .addBodyParameter("description",ed_des_sp.getText().toString().trim())
                .setTag("add_lab_package")
                .setPriority(Priority.HIGH)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.e("yuyu", "onResponse: " +response.toString());

                        try {
                            if (response.getString("result").equals("Successfully")) {
                                startActivity(new Intent(LabPackageActivity.this,LabShowPackageActivity.class));

                            }
                        } catch (JSONException jsonException) {
                            Log.e("exceptionmsg", "onResponse: " +jsonException.getMessage());
                            jsonException.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        Log.e("errormsg", "onError: " +anError.getMessage());
                    }
                });


    }
}
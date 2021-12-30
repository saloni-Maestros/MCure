package com.MCure.MCure;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.google.android.material.button.MaterialButton;
import com.MCure.MCure.others.API;
import com.MCure.MCure.others.APPCONSTANT;
import com.MCure.MCure.others.SharedHelper;

import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity {
   MaterialButton materialbuttonotp;
   EditText etMobileNo;
    ProgressBar progressBar;
    String Type = "",UserId="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        materialbuttonotp = findViewById(R.id.materialbuttonotp);
        etMobileNo = findViewById(R.id.etMobileNo);
        progressBar = findViewById(R.id.progressBar);

        Type = SharedHelper.getKey(LoginActivity.this, APPCONSTANT.TYPE);
        Log.e("rfgsdgrg",Type );

       // UserId = SharedHelper.putkey(LoginActivity.this,APPCONSTANT.USERID);


        materialbuttonotp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              
                if (etMobileNo.getText().toString().trim().isEmpty()){
                    Toast.makeText(LoginActivity.this, "Please enter mobile number", Toast.LENGTH_SHORT).show();
                }else {
                    signupLogin();
                }


            }
        });

    }

    public void signupLogin(){
        progressBar.setVisibility(View.VISIBLE);
        AndroidNetworking.post(API.signup)
                .addBodyParameter("mobile",etMobileNo.getText().toString().trim())
                .addBodyParameter("type",Type)
                .setPriority(Priority.HIGH)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        progressBar.setVisibility(View.GONE);
                        Log.e("ewdfgdsf", "onResponse: " +response);
                       try {
                           if (response.getString("result").equals("Registration successful")){
                               SharedHelper.putkey(LoginActivity.this, APPCONSTANT.MOBILE,response.getString("mobile"));
                               SharedHelper.putkey(LoginActivity.this, APPCONSTANT.RESULT,"Registration");
                               SharedHelper.putkey(LoginActivity.this, APPCONSTANT.TYPE, response.getString("type"));
                               SharedHelper.putkey(LoginActivity.this,APPCONSTANT.USERID,response.getString("id"));
                               if (response.getString("type").equals("1")){
                                   startActivity(new Intent(LoginActivity.this,OtpActivity.class));
                               } else if (response.getString("type").equals("2")){
                                   startActivity(new Intent(LoginActivity.this,OtpActivity.class));

                               }
                            /*   if (response.getString("type").equals("1")){
                                   startActivity(new Intent(LoginActivity.this,OtpActivity.class));

                               } else if (response.getString("type").equals("2")){
                                   startActivity(new Intent(LoginActivity.this,OtpActivity.class));
                               }*/
                               //SharedHelper.putkey(LoginActivity.this, APPCONSTANT.TYPE,getString(Integer.parseInt("1")));
                           }else if (response.getString("result").equals("Login successful")){
                               SharedHelper.putkey(LoginActivity.this, APPCONSTANT.MOBILE,response.getString("mobile"));
                               SharedHelper.putkey(LoginActivity.this, APPCONSTANT.RESULT,"Login");
                               SharedHelper.putkey(LoginActivity.this,APPCONSTANT.USERID,response.getString("id"));
                                startActivity(new Intent(LoginActivity.this,OtpActivity.class));

                           }else {
                                   Toast.makeText(LoginActivity.this, ""+response.getString("result"), Toast.LENGTH_SHORT).show();
                           }
                       }catch (Exception e){
                           progressBar.setVisibility(View.GONE);
                           Log.e("kdjcfkldzxck", "onResponse: " +e.getMessage());

                       }


                    }

                    @Override
                    public void onError(ANError anError) {
                        progressBar.setVisibility(View.GONE);
                        Log.e("saaassaa", anError.getMessage());

                    }
                });


    }
}
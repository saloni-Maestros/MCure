package com.MCure.MCure;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.google.android.material.button.MaterialButton;
import com.MCure.MCure.others.API;
import com.MCure.MCure.others.APPCONSTANT;
import com.MCure.MCure.others.SharedHelper;

import org.json.JSONObject;

public class OtpActivity extends AppCompatActivity {
    MaterialButton materialbuttonlogin;
    String MOBILE = "";
    ProgressBar progressBar;
    EditText Edittext_otp;
    String Type="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp);
        MOBILE = SharedHelper.getKey(OtpActivity.this, APPCONSTANT.MOBILE);
        materialbuttonlogin = findViewById(R.id.materialbuttonlogin);
        Edittext_otp = findViewById(R.id.Edittext_otp);
        progressBar = findViewById(R.id.progressBar);
        Type= SharedHelper.getKey(OtpActivity.this, APPCONSTANT.TYPE);

      //  UserId= ShareHelper.getKey(TailorProductActivity.this, APPCONSTANT.USERID);

       // Type= SharedHelper.getKey(OtpActivity.this,APPCONSTANT.TYPE);

        materialbuttonlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (Edittext_otp.getText().toString().trim().isEmpty()) {
                    Toast.makeText(OtpActivity.this, "please Enter Otp", Toast.LENGTH_SHORT).show();
                } else
                    otpVerify();

            }
        });
    }

    public void otpVerify() {
        progressBar.setVisibility(View.VISIBLE);
        AndroidNetworking.post(API.otp)
                .addBodyParameter("otp", Edittext_otp.getText().toString().trim())
                .addBodyParameter("mobile", MOBILE)
                .setPriority(Priority.HIGH)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        progressBar.setVisibility(View.GONE);
                        Log.e("OtpActivity", response.toString());
                        try {
                            if (response.getString("result").equals("Login Successful")) {
                                if(Type.equals("1"))
                                {
                                    Toast.makeText(OtpActivity.this, "mobile" + response.getString("result"), Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(OtpActivity.this,NavActivity.class));
                                }
                                else {
                                    startActivity(new Intent(OtpActivity.this,LabHomeActivity.class));
                                }

                             //  SharedHelper.getKey(OtpActivity.this, APPCONSTANT.TYPE,getString(Integer.parseInt("2")));
                              /*  if (Type.equals("1")) {
                                    startActivity(new Intent(OtpActivity.this, LabHomeActivity.class));
                                } else if (Type.equals("2")) {
                                    startActivity(new Intent(OtpActivity.this,NavActivity .class));
                                } */

                            } else if (response.getString("result").equals("Registration successful")){
                                Type=SharedHelper.getKey(OtpActivity.this,APPCONSTANT.TYPE);
                                startActivity(new Intent(OtpActivity.this, HomeScreen1MainActivity.class));

                            }

                            else {
                                Toast.makeText(OtpActivity.this, "" + response.getString("result"), Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception e) {
                            progressBar.setVisibility(View.GONE);
                            Log.e("fgfggfdg",e.getMessage() );

                        }
                    }


                    @Override
                    public void onError(ANError anError) {
                        progressBar.setVisibility(View.GONE);
                        Log.e("OtpActivity", anError.getMessage());
                    }
                });


    }
}
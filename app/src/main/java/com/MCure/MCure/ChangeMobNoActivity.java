package com.MCure.MCure;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
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

public class ChangeMobNoActivity extends AppCompatActivity {

    EditText et_Mobno, et_otp;
    MaterialButton btn_change_number;
    String UserId,Moblile;
    ProgressBar progressbar_change;
    ImageView imgarrow_change;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_mob_no);

        et_Mobno = findViewById(R.id.et_Mobno);
        et_otp = findViewById(R.id.et_otp);
        btn_change_number = findViewById(R.id.btn_change_number);
        progressbar_change=findViewById(R.id.progressbar_change);
        imgarrow_change=findViewById(R.id.imgarrow_change);

        UserId = SharedHelper.getKey(ChangeMobNoActivity.this, APPCONSTANT.USERID);
        Moblile=SharedHelper.getKey(ChangeMobNoActivity.this,APPCONSTANT.MOBILE);

        Log.e("ttt",UserId);
        Log.e("rr",Moblile);

        imgarrow_change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        btn_change_number.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if (et_Mobno.getText().toString().trim().isEmpty()) {
                    Toast.makeText(ChangeMobNoActivity.this, "Please enter mobile number", Toast.LENGTH_SHORT).show();
                } else if (et_otp.getText().toString().trim().isEmpty()) {
                    Toast.makeText(ChangeMobNoActivity.this, "Please enter otp", Toast.LENGTH_SHORT).show();
                } else {
                    changeMobileNumber();
                }
            }
        });
    }

    private void changeMobileNumber() {

        progressbar_change.setVisibility(View.VISIBLE);
        AndroidNetworking.post(API.change_mobile_number)
                .addBodyParameter("mobile", et_Mobno.getText().toString().trim())
                .addBodyParameter("type", "1")
                .addBodyParameter("user_id", UserId)
                .setPriority(Priority.HIGH)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.e("show", response.toString());

                        progressbar_change.setVisibility(View.GONE);
                        try {
                            if (response.getString("result").equals("succesfully")) {

                                Log.e("tag", response.toString());
                                SharedHelper.putkey(ChangeMobNoActivity.this, APPCONSTANT.MOBILE, response.getString("mobile"));
                                Toast.makeText(ChangeMobNoActivity.this, " Successfuly Change Mobile Number..", Toast.LENGTH_LONG).show();
                                // SharedHelper.putkey(ChangeMobNoActivity.this, APPCONSTANT.RESULT,"succesfully");

                                startActivity(new Intent(ChangeMobNoActivity.this,HomeScreen1MainActivity.class));
                            } else {
                                Toast.makeText(ChangeMobNoActivity.this, "" + response.getString("result"), Toast.LENGTH_SHORT).show();
                            }

                        } catch (Exception e) {
                            progressbar_change.setVisibility(View.GONE);
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(ANError anError) {

                        progressbar_change.setVisibility(View.GONE);
                        Log.e("saaassaa", anError.getMessage());
                    }

                });
    }
}


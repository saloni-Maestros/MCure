package com.MCure.MCure;

import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.MCure.MCure.others.API;
import com.MCure.MCure.others.APPCONSTANT;
import com.MCure.MCure.others.SharedHelper;

import org.json.JSONException;
import org.json.JSONObject;

public class CustomSupportActivity extends AppCompatActivity {

    private static final int MY_PERMISSIONS_REQUEST_CALL_PHONE = 1;
    String contact;
    ImageView imgarrow_support, img_call, img_email;
    RelativeLayout rel_chat_us, rel_email;
    String UserId = "";
    TextView tv_emailmsg, tv_callmsg, tv_chat1,tv_chat3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_support);
        tv_chat3 = findViewById(R.id.tv_chat3);
        imgarrow_support = findViewById(R.id.imgarrow_support);
        rel_chat_us = findViewById(R.id.rel_chat_us);
        tv_chat1 = findViewById(R.id.tv_chat1);
        rel_email = findViewById(R.id.rel_email);
        tv_emailmsg = findViewById(R.id.tv_emailmsg);
        tv_callmsg = findViewById(R.id.tv_callmsg);
        img_call = findViewById(R.id.img_call);
        img_email = findViewById(R.id.img_email);
        UserId = SharedHelper.getKey(CustomSupportActivity.this, APPCONSTANT.USERID);
        Log.e("userid", UserId);
        imgarrow_support.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        rel_email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "https://mail.google.com/mail/";
                Intent intent1 = new Intent(Intent.ACTION_VIEW);
                intent1.setData(Uri.parse(url));
                startActivity(intent1);
            }
        });
        rel_chat_us.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CustomSupportActivity.this, CaseHistoryscrenActivity.class));
            }
        });
        tv_chat3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog = new Dialog(CustomSupportActivity.this);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setCancelable(true);
                dialog.setContentView(R.layout.calling_dialog);

                TextView btn_cancel = dialog.findViewById(R.id.btn_cancel);
                TextView btn_call = dialog.findViewById(R.id.btn_call);

                btn_cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                btn_call.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Uri number = Uri.parse("tel:9987639012");
                        Intent callIntent = new Intent(Intent.ACTION_DIAL, number);
                        startActivity(callIntent);

                    }
                });
                dialog.show();

            }
        });
        support();


    }

    private void support() {
        AndroidNetworking.post(API.support)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {

                            tv_emailmsg.setText(response.getString("email"));
                            tv_callmsg.setText(response.getString("phone"));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                    }

                });
    }

}




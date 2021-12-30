package com.MCure.MCure;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONArrayRequestListener;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.MCure.MCure.others.API;
import com.MCure.MCure.others.APPCONSTANT;
import com.MCure.MCure.others.SharedHelper;

import org.json.JSONArray;
import org.json.JSONObject;

public class ShowHistoryActivity extends AppCompatActivity {
    ImageView img_arrow, img_pic;
    String ID = "";
    ProgressBar progressBar;
    TextView tv_username,tv_age,tv_time,tv_date,tv_name,tv_hisName,tv_Age,tv_hisAge,tv_hisRelation,tv_hisMob,tv_hisEmail,tv_status;
   String UserId = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_history);
        progressBar = findViewById(R.id.progressBar);
        img_arrow = findViewById(R.id.img_arrow);
        img_pic = findViewById(R.id.img_pic);
        tv_username = findViewById(R.id.tv_username);
        tv_status = findViewById(R.id.tv_status);
        tv_age = findViewById(R.id.tv_age);
        tv_time = findViewById(R.id.tv_time);
        tv_date = findViewById(R.id.tv_date);
        tv_name = findViewById(R.id.tv_name);
        tv_hisName = findViewById(R.id.tv_hisName);
        tv_Age = findViewById(R.id.tv_Age);
        tv_hisAge = findViewById(R.id.tv_hisAge);
        tv_hisRelation = findViewById(R.id.tv_hisRelation);
        tv_hisMob = findViewById(R.id.tv_hisMob);
        tv_hisEmail = findViewById(R.id.tv_hisEmail);
        tv_username = findViewById(R.id.tv_username);
        img_arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

      ID = SharedHelper.getKey(ShowHistoryActivity.this, APPCONSTANT.ID);
        Log.e("jhklsdjfckdfc", ID);
        UserId = SharedHelper.getKey(ShowHistoryActivity.this, APPCONSTANT.USERID);
        Log.e("jhklsdjfckdfc", UserId);

        show_booking_details();
    }

    public void show_booking_details(){
        progressBar.setVisibility(View.VISIBLE);
        AndroidNetworking.post(API.show_booking_details)
                .addBodyParameter("id", ID)
                .setTag("show_booking_details")
                .setPriority(Priority.HIGH)
                .build()
                .getAsJSONArray(new JSONArrayRequestListener() {
                    @Override
                    public void onResponse(JSONArray response) {
                        progressBar.setVisibility(View.GONE);
                        Log.e("cihdnckfsd", "onResponse: " +response.toString());
                        try {
                            for (int i=0; i<response.length(); i++){
                                JSONObject jsonObject = new JSONObject(response.getString(i));
                                jsonObject.getString("id");
                                Log.e("cfkncffv", "id");
                                jsonObject.getString("user_id");
                                tv_username.setText(jsonObject.getString("name"));
                                tv_age.setText(jsonObject.getString("age"));
                                tv_date.setText(jsonObject.getString("dates"));
                                tv_time.setText(jsonObject.getString("time"));
                                tv_status.setText(jsonObject.getString("status"));


                                JSONArray jsonArray = new JSONArray(jsonObject.getString("user_details"));
                                for (int j = 0; j < jsonArray.length(); j++) {
                                    JSONObject jsonObject1 = jsonArray.getJSONObject(j);
                                    jsonObject1.getString("id");
                                   /* tv_hisName.setText(jsonObject1.getString("name"));
                                    tv_hisAge.setText(jsonObject1.getString("age"));
                                    tv_hisRelation.setText(jsonObject1.getString("relation"));
                                    tv_hisEmail.setText(jsonObject1.getString("email"));
                                    tv_hisMob.setText(jsonObject1.getString("mobile"));*/
                                    Glide.with(ShowHistoryActivity.this).load(jsonObject1.getString("path") + jsonObject1.getString("image"))
                                            // .placeholder(R.drawable.user_icon).override(250, 250)
                                            .diskCacheStrategy(DiskCacheStrategy.ALL).into(img_pic);
                                }

                                }
                        } catch (Exception e){
                            progressBar.setVisibility(View.GONE);
                            Log.e("jdkjdfcmdc", "onResponse: " +e.getMessage());

                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        progressBar.setVisibility(View.GONE);
                        Log.e("hjsdxhjdx", "onError: " +anError);
                    }
                });

    }
}
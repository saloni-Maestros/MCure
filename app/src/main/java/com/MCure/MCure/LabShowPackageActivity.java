package com.MCure.MCure;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONArrayRequestListener;
import com.google.android.material.button.MaterialButton;
import com.MCure.MCure.Adapter.LabPackageAdapter;
import com.MCure.MCure.ModelClass.ModelPackage;
import com.MCure.MCure.others.API;
import com.MCure.MCure.others.APPCONSTANT;
import com.MCure.MCure.others.SharedHelper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class LabShowPackageActivity extends AppCompatActivity {
 ImageView img_leftarrow;

    RecyclerView rev_showpac;
    LabPackageAdapter packageAdapter;
    ArrayList<ModelPackage> ModelPackages;

    MaterialButton mbtn_pclab;
    String UserId="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lab_show_package);
        img_leftarrow = findViewById(R.id.img_leftarrow);
        mbtn_pclab=findViewById(R.id.mbtn_pclab);
        rev_showpac=findViewById(R.id.rev_showpac);
        img_leftarrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        UserId = SharedHelper.getKey(LabShowPackageActivity.this, APPCONSTANT.USERID);
        Log.e("userid", UserId);
        mbtn_pclab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               startActivity(new Intent(LabShowPackageActivity.this, LabPackageActivity.class));
            }
        });
        showLabPackage();

    }

    private void showLabPackage() {

        Log.e("UserId", UserId);
        AndroidNetworking.post(API.show_lab_package)
                .addBodyParameter("user_id",UserId)
                .setTag("show_lab_package")
                .setPriority(Priority.HIGH)
                .build()
                .getAsJSONArray(new JSONArrayRequestListener() {
                    @Override
                    public void onResponse(JSONArray response) {
                        ModelPackages=new ArrayList<>();

                        Log.e("jfdhjgv", "onResponse: " +response);
                        try {
                            for (int i=0;i<response.length();i++) {
                                JSONObject jsonObject = response.getJSONObject(i);
                                ModelPackage modelPackage = new ModelPackage();
                                modelPackage.setPc_Id(jsonObject.getString("id"));
                                modelPackage.setPc_dec(jsonObject.getString("description"));
                                modelPackage.setPc_Title(jsonObject.getString("title"));
                                modelPackage.setPc_price(jsonObject.getString("price"));
                                ModelPackages.add(modelPackage);

                            }
                            packageAdapter = new LabPackageAdapter(ModelPackages, LabShowPackageActivity.this);
                            rev_showpac.setHasFixedSize(true);
                            rev_showpac.setLayoutManager(new LinearLayoutManager(LabShowPackageActivity.this, LinearLayoutManager.VERTICAL, false));
                            rev_showpac.setAdapter(packageAdapter);

                        } catch (JSONException e) {
                            Log.e("dreifeft", "onResponse: " + e.getMessage());
                            e.printStackTrace();
                        }

                    }

                    @Override
                    public void onError(ANError anError) {
                        Log.e("jgkfjgvkf", "onError: " + anError.getMessage());

                    }
                });
    }
}
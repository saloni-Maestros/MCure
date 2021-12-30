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
import com.MCure.MCure.Adapter.ShowPackageTestAdapter;
import com.MCure.MCure.ModelClass.ModelShowPackageTest;
import com.MCure.MCure.others.API;
import com.MCure.MCure.others.APPCONSTANT;
import com.MCure.MCure.others.SharedHelper;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class ShowPackageTestActivity extends AppCompatActivity {
    ImageView img_leftarrow;
    MaterialButton  mbtn_pclab;
    String  UserId = "";
    String packageId;

    RecyclerView Rv_Showlabtest;
    ArrayList<ModelShowPackageTest> modelShowPackageTestArrayList;
    ShowPackageTestAdapter showPackageTestAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_package_test);
        img_leftarrow = findViewById(R.id.img_leftarrow);
        mbtn_pclab=findViewById(R.id.mbtn_pclab);
        Rv_Showlabtest=findViewById(R.id.Rv_Showlabtest);
        img_leftarrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        UserId = SharedHelper.getKey(ShowPackageTestActivity.this, APPCONSTANT.USERID);
        Log.e("ShowPackageTestActivity", UserId);

     //   packageId = SharedHelper.getKey(ShowPackageTestActivity.this, APPCONSTANT.PACKAGEID);
        mbtn_pclab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ShowPackageTestActivity.this, PackageActivity.class));
            }
        });
        show_package_test();
    }

    public void show_package_test(){
        AndroidNetworking.post(API.show_package_test)
                .addBodyParameter("user_id", UserId)
                .setTag("show_package_test")
                .setPriority(Priority.HIGH)
                .build()
              .getAsJSONArray(new JSONArrayRequestListener() {
                  @Override
                  public void onResponse(JSONArray response) {
                      modelShowPackageTestArrayList = new ArrayList<>();
                      Log.e("xdijfdjf", "onResponse: " +response.toString());
                      try {
                          for (int i=0; i<response.length(); i++){
                              JSONObject jsonObject = response.getJSONObject(i);
                              ModelShowPackageTest modelShowPackageTest = new ModelShowPackageTest();
                              modelShowPackageTest.setId(jsonObject.getString("id"));
                              modelShowPackageTest.setPackage_id(jsonObject.getString("package_id"));
                              modelShowPackageTest.setTitle(jsonObject.getString("title"));
                              modelShowPackageTest.setPackage_title(jsonObject.getString("package_title"));
                              modelShowPackageTest.setDescription(jsonObject.getString("description"));
                              SharedHelper.putkey(ShowPackageTestActivity.this,APPCONSTANT.PACKAGEID,jsonObject.getString("package_id"));
                              Log.e("sdessdsdsd",jsonObject.getString("package_id"));
                              modelShowPackageTestArrayList.add(modelShowPackageTest);

                          }
                          showPackageTestAdapter = new ShowPackageTestAdapter(modelShowPackageTestArrayList, ShowPackageTestActivity.this);
                          Rv_Showlabtest.setHasFixedSize(true);
                          Rv_Showlabtest.setLayoutManager(new LinearLayoutManager(ShowPackageTestActivity.this, LinearLayoutManager.VERTICAL, false));
                          Rv_Showlabtest.setAdapter(showPackageTestAdapter);



                      } catch (Exception e){
                          Log.e("iujidfjrdf", "onResponse: " +e.getMessage());

                      }
                  }

                  @Override
                  public void onError(ANError anError) {
                      Log.e("cvcmv", "onError: " +anError);

                  }
              });



    }
}
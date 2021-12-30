package com.MCure.MCure;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONArrayRequestListener;
import com.google.android.material.button.MaterialButton;
import com.MCure.MCure.Adapter.LabCompleteBookingAdapter;
import com.MCure.MCure.Adapter.LabShowBookingAdapter;
import com.MCure.MCure.ModelClass.ModelLabCompleteBooking;
import com.MCure.MCure.ModelClass.ModelLabShowBooking;
import com.MCure.MCure.others.API;
import com.MCure.MCure.others.APPCONSTANT;
import com.MCure.MCure.others.SharedHelper;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class LabOrderActivity extends AppCompatActivity {
    MaterialButton NewCasebtnLab,SolveCaseBtnLab;
    String UserId ="";

    RecyclerView Recycle_Showbooking;
    LabShowBookingAdapter labShowBookingAdapter;
    ArrayList<ModelLabShowBooking> modelLabShowBookingArrayList;

    RecyclerView  Recycle_completebooking;
    LabCompleteBookingAdapter labCompleteBookingAdapter;
    ArrayList<ModelLabCompleteBooking> modelLabCompleteBookingArrayList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lab_order);
        SolveCaseBtnLab = findViewById(R.id.SolveCaseBtnLab);
        NewCasebtnLab = findViewById(R.id.NewCasebtnLab);
        Recycle_completebooking = findViewById(R.id.Recycle_completebooking);
        Recycle_Showbooking = findViewById(R.id.Recycle_Showbooking);


        UserId = SharedHelper.getKey(LabOrderActivity.this, APPCONSTANT.USERID);
        Log.e("userid", UserId);

        NewCasebtnLab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Recycle_Showbooking.setVisibility(View.VISIBLE);
                Recycle_completebooking.setVisibility(View.GONE);

                NewCasebtnLab.setBackgroundColor(getResources().getColor(R.color.blue));
                SolveCaseBtnLab.setBackgroundColor(getResources().getColor(R.color.white));


            }
        });

        SolveCaseBtnLab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Recycle_Showbooking.setVisibility(View.GONE);
                Recycle_completebooking.setVisibility(View.VISIBLE);

                SolveCaseBtnLab.setBackgroundColor(getResources().getColor(R.color.blue));
                NewCasebtnLab.setBackgroundColor(getResources().getColor(R.color.white));
                //startActivity(new Intent(getActivity(), HomeScreen1MainActivity.class));
            }
        });



     labShowBooking();
labCompleteBooking();
    }

     public void labShowBooking(){
         AndroidNetworking.post(API.show_doctor_booking)
                 .addBodyParameter("user_id", UserId)
                 .setTag("show_doctor_booking")
                 .setPriority(Priority.HIGH)
                 .build()
                 .getAsJSONArray(new JSONArrayRequestListener() {
                     @Override
                     public void onResponse(JSONArray response) {
                         modelLabShowBookingArrayList = new ArrayList<>();
                         Log.e("jdkfkd", "onResponse: " +response.toString());
                         try {
                             for (int i = 0; i < response.length(); i++) {
                                 JSONObject jsonObject = response.getJSONObject(i);
                                 ModelLabShowBooking modelLabShowBooking = new ModelLabShowBooking();
                                 modelLabShowBooking.setDates(jsonObject.getString("dates"));
                                 modelLabShowBooking.setTime(jsonObject.getString("time"));
                                 modelLabShowBooking.setStatus(jsonObject.getString("status"));

                                 JSONArray jsonArray = new JSONArray(jsonObject.getString("user_details"));
                                 for (int j = 0; j < jsonArray.length(); j++) {

                                     Log.e("fyyt", "onResponse: "+response);
                                     JSONObject jsonObject1 = jsonArray.getJSONObject(j);
                                     modelLabShowBooking.setId(jsonObject1.getString("id"));
                                     modelLabShowBooking.setImage(jsonObject1.getString("image"));
                                     modelLabShowBooking.setPath(jsonObject1.getString("path"));
                                     modelLabShowBooking.setName(jsonObject1.getString("name"));
                                     modelLabShowBooking.setAge(" Age " + jsonObject1.getString("age"));
                                     modelLabShowBookingArrayList.add(modelLabShowBooking);

                                 }

                                 }
                             Recycle_Showbooking.setHasFixedSize(true);
                             Recycle_Showbooking.setLayoutManager(new LinearLayoutManager(LabOrderActivity.this,LinearLayoutManager.VERTICAL,false));
                             Recycle_Showbooking.setAdapter(new LabShowBookingAdapter(modelLabShowBookingArrayList, LabOrderActivity.this));

                         } catch (Exception e){
                             Log.e("dcjcfn", "onResponse: " +e.getMessage());

                         }

                     }

                     @Override
                     public void onError(ANError anError) {
                         Log.e("nvkfvk", "onError: " +anError.getMessage());

                     }
                 });

     }

     public void labCompleteBooking(){
        AndroidNetworking.post(API.show_complete_booking)
                .addBodyParameter("user_id",UserId)
                .setTag("show_complete_booking")
                .setPriority(Priority.HIGH)
                .build()
                .getAsJSONArray(new JSONArrayRequestListener() {
                    @Override
                    public void onResponse(JSONArray response) {
                        modelLabCompleteBookingArrayList = new ArrayList<>();
                        Log.e("cdcjxn", "onResponse: " +response.toString());
                        try {
                            for (int i=0;i<response.length();i++) {
                                JSONObject jsonObject=response.getJSONObject(i);
                                ModelLabCompleteBooking modelLabCompleteBooking=new ModelLabCompleteBooking();
                                modelLabCompleteBooking.setDates(jsonObject.getString("dates"));
                                modelLabCompleteBooking.setTime(jsonObject.getString("time"));
                                modelLabCompleteBooking.setStatus(jsonObject.getString("status"));


                                JSONArray jsonArray=new JSONArray(jsonObject.getString("user_details"));
                                for (int j=0;j<jsonArray.length();j++) {
                                    Log.e("msg", "onResponse: " +response);
                                    JSONObject jsonObject1=jsonArray.getJSONObject(j);
                                    modelLabCompleteBooking.setId(jsonObject1.getString("id"));
                                    modelLabCompleteBooking.setImage(jsonObject1.getString("image"));
                                    modelLabCompleteBooking.setName(jsonObject1.getString("name"));
                                    modelLabCompleteBooking.setAge("  Age " +jsonObject1.getString("age"));
                                    modelLabCompleteBooking.setPath(jsonObject1.getString("path"));
                                    modelLabCompleteBookingArrayList.add(modelLabCompleteBooking);

                                }
                            }
                            Recycle_completebooking.setHasFixedSize(true);
                            Recycle_completebooking.setLayoutManager(new LinearLayoutManager(LabOrderActivity.this,LinearLayoutManager.VERTICAL,false));
                            Recycle_completebooking.setAdapter(new LabCompleteBookingAdapter(modelLabCompleteBookingArrayList, LabOrderActivity.this));



                        } catch (Exception e){

                        }
                    }

                    @Override
                    public void onError(ANError anError) {

                    }
                });
     }

}
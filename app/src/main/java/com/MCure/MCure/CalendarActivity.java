package com.MCure.MCure;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONArrayRequestListener;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.google.android.material.button.MaterialButton;
import com.MCure.MCure.Adapter.CalenderDaysAdapter;
import com.MCure.MCure.Adapter.CalenderTimeAdapter;
import com.MCure.MCure.ModelClass.ModelCalenderDays;
import com.MCure.MCure.ModelClass.ModelCalenderTimeSlot;
import com.MCure.MCure.others.API;
import com.MCure.MCure.others.APPCONSTANT;
import com.MCure.MCure.others.SharedHelper;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class CalendarActivity extends AppCompatActivity {
    boolean iscolor = true;
    ImageView imgarrow;
    String UserId = "";
    MaterialButton BtnSunday, BtnMonday, BtnTuesday, BtnWednesday, BtnThursday, BtnFriday, BtnSaturday;
    TextView tv_Days, tv_Times;
    String Id = "";
    ProgressBar progressBar;

    RecyclerView Recyclerview_Calender;
    CalenderTimeAdapter calenderTimeAdapter;
    ArrayList<ModelCalenderTimeSlot> modelCalenderTimeSlotArrayList;

    RecyclerView Recyclerview_Days;
    CalenderDaysAdapter calenderDaysAdapter;
    ArrayList<ModelCalenderDays> modelCalenderDaysArrayList;
    MaterialButton mbtn_Submit;

    public static ArrayList<String> Arr_finalIds;
    public static ArrayList<String> Arr_finalName;


      String DAYS ="";
      String TIMES = "";
      String loginToken = "";



    String strCatIds = "";




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);
        progressBar = findViewById(R.id.progressBar);
        Recyclerview_Calender = findViewById(R.id.Recyclerview_Calender);
        Recyclerview_Days = findViewById(R.id.Recyclerview_Days);

        imgarrow = findViewById(R.id.imgarrow);

        tv_Days = findViewById(R.id.tv_Days);
        tv_Times = findViewById(R.id.tv_Times);
        mbtn_Submit = findViewById(R.id.mbtn_Submit);

        Arr_finalIds = new ArrayList<>();
        Arr_finalName = new ArrayList<>();


        imgarrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        BtnSunday = findViewById(R.id.BtnSunday);

        /*BtnSunday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (iscolor) {
                    BtnSunday.setBackgroundColor(getResources().getColor(R.color.blue));           //select multiple button and chnage color
                    iscolor = false;
                } else {
                    BtnSunday.setBackgroundColor(getResources().getColor(R.color.white));
                    iscolor = true;
                }


            }

        });*/




        UserId = SharedHelper.getKey(CalendarActivity.this, APPCONSTANT.USERID);
      Id = SharedHelper.getKey(CalendarActivity.this, APPCONSTANT.ID);
        Log.e("cdcnx", Id);

        show_doctor_times();
        show_days();
        mbtn_Submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                add_dr_times();
            }
        });


    }

    public void show_days(){
        progressBar.setVisibility(View.VISIBLE);
         AndroidNetworking.post(API.show_days)
                 .addBodyParameter("user_id", UserId)
                 .setTag("show_days")
                 .setPriority(Priority.HIGH)
                 .build()
                 .getAsJSONArray(new JSONArrayRequestListener() {
                     @Override
                     public void onResponse(JSONArray response) {
                         progressBar.setVisibility(View.GONE);
                         Log.e("kdjskszjdx", "onResponse: " +response.toString());
                         modelCalenderDaysArrayList = new ArrayList<>();
                         try {
                             for (int i = 0; i < response.length(); i++) {
                                 JSONObject jsonObject = response.getJSONObject(i);
                                 ModelCalenderDays modelCalenderDays = new ModelCalenderDays();
                                modelCalenderDays.setDay(jsonObject.getString("day"));
                                 modelCalenderDays.setId(jsonObject.getString("id"));
                                 modelCalenderDays.setDay_status(jsonObject.getString("day_status"));
                                 modelCalenderDaysArrayList.add(modelCalenderDays);
                             }
                             Recyclerview_Days.setHasFixedSize(true);
                             Recyclerview_Days.setLayoutManager(new GridLayoutManager(CalendarActivity.this, 3, RecyclerView.VERTICAL, false));
                             Recyclerview_Days.setAdapter(new CalenderDaysAdapter(modelCalenderDaysArrayList, CalendarActivity.this));
                         } catch (Exception e) {
                             progressBar.setVisibility(View.GONE);
                             Log.e("ddxcc", "onResponse: " +e.getMessage());

                         }
                     }

                     @Override
                     public void onError(ANError anError) {
                         progressBar.setVisibility(View.GONE);
                         Log.e("mxcmxc", "onError: " +anError);

                     }
                 });

         }

    public void show_doctor_times() {
        progressBar.setVisibility(View.VISIBLE);
        AndroidNetworking.post(API.show_doctor_times)
                .addBodyParameter("user_id", UserId)
                .setTag("show_doctor_times")
                .setPriority(Priority.HIGH)
                .build()
                .getAsJSONArray(new JSONArrayRequestListener() {
                    @Override
                    public void onResponse(JSONArray response) {
                        progressBar.setVisibility(View.GONE);
                        Log.e("yeuedwdweu", "onResponse: " + response.toString());
                        modelCalenderTimeSlotArrayList = new ArrayList<>();
                        try {
                            for (int i = 0; i < response.length(); i++) {
                                JSONObject jsonObject = response.getJSONObject(i);
                                ModelCalenderTimeSlot modelCalenderTimeSlot = new ModelCalenderTimeSlot();
                                modelCalenderTimeSlot.setTimes(jsonObject.getString("times"));
                                modelCalenderTimeSlot.setId(jsonObject.getString("id"));
                                modelCalenderTimeSlot.setDr_status(jsonObject.getString("dr_status"));
                                modelCalenderTimeSlotArrayList.add(modelCalenderTimeSlot);
                            }
                            Recyclerview_Calender.setHasFixedSize(true);
                            Recyclerview_Calender.setLayoutManager(new GridLayoutManager(CalendarActivity.this, 2, RecyclerView.VERTICAL, false));
                            Recyclerview_Calender.setAdapter(new CalenderTimeAdapter(modelCalenderTimeSlotArrayList, CalendarActivity.this));
                        } catch (Exception e) {
                            progressBar.setVisibility(View.GONE);
                            Log.e("dkxjfkd", "onResponse: " + e.getMessage());

                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        progressBar.setVisibility(View.GONE);
                        Log.e("flklcfgv", "onError: " + anError);

                    }
                });
    }

    public void add_dr_times() {
        DAYS = SharedHelper.getKey(CalendarActivity.this, APPCONSTANT.DAYS);
        TIMES = SharedHelper.getKey(CalendarActivity.this, APPCONSTANT.TIMES);
        loginToken = SharedHelper.getKey(CalendarActivity.this, APPCONSTANT.loginToken);
        Log.e("fghgfhgh",DAYS);
        Log.e("fghgfhgh", TIMES);
        Log.e("fghgfhgh", loginToken );
        progressBar.setVisibility(View.VISIBLE);
        AndroidNetworking.post(API.add_dr_times)
                .addBodyParameter("user_id", UserId)
                .addBodyParameter("day", DAYS)
                .addBodyParameter("dr_time", TIMES)
                .setTag("add_dr_times")
                .setPriority(Priority.HIGH)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        progressBar.setVisibility(View.GONE);
                        Log.e("cmnmxxvmnv", "onResponse: " + response.toString());
                        try {
                            if (response.get("message").equals("successfully")) {
                                Toast.makeText(CalendarActivity.this, "" + response.getString("message"), Toast.LENGTH_SHORT).show();

                            }

                        } catch (Exception e) {
                            progressBar.setVisibility(View.GONE);
                            Log.e("vmkcmvcmv", "onResponse: " + e.getMessage());
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        progressBar.setVisibility(View.GONE);
                        Log.e("vcbbvcvb", "onError: " + anError);
                    }
                });

    }


}
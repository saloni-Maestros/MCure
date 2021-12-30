package com.MCure.MCure.Fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONArrayRequestListener;
import com.google.android.material.button.MaterialButton;
import com.MCure.MCure.Adapter.CompleteBookingAdapter;
import com.MCure.MCure.Adapter.ShowBookingAdapter;
import com.MCure.MCure.ModelClass.ModelCompleteBooking;
import com.MCure.MCure.ModelClass.ModelShowBooking;
import com.MCure.MCure.NavActivity;
import com.MCure.MCure.R;
import com.MCure.MCure.others.API;
import com.MCure.MCure.others.APPCONSTANT;
import com.MCure.MCure.others.SharedHelper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class HomeFragment extends Fragment {
    LinearLayout ll_solvecxse;
    LinearLayout ll_my_case;
    String UserId ="";
    ProgressBar progressBar;
    String ID = "";
   ImageView img_menu;
   MaterialButton NewCasebtn,SolveCaseBtn;


   RecyclerView rev_booking;
    ShowBookingAdapter bookingAdapter;
    ArrayList<ModelShowBooking> modelShowBookingArrayList;


    RecyclerView rev_complete_booking;
    CompleteBookingAdapter completeAdapter;
    ArrayList<ModelCompleteBooking>modelCompleteBookingArrayList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view= inflater.inflate(R.layout.fragment_home, container, false);
        img_menu=view.findViewById(R.id.img_menu);
        progressBar = view.findViewById(R.id.progressBar);
        rev_booking=view.findViewById(R.id.rev_booking);
        rev_complete_booking=view.findViewById(R.id.rev_complete_booking);
        NewCasebtn = view.findViewById(R.id.NewCasebtn);
        SolveCaseBtn=view.findViewById(R.id.SolveCaseBtn);

        UserId = SharedHelper.getKey(getActivity(), APPCONSTANT.USERID);
        Log.e("fcjdhnjfc", UserId);
        ID = SharedHelper.getKey(getActivity(),APPCONSTANT.ID);
        Log.e("fcjdhnjfc", ID);

           img_menu.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("WrongConstant")
            @Override
            public void onClick(View view) {
                 NavActivity.drawer_layout.openDrawer(Gravity.START);
            }
        });

       NewCasebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

               rev_booking.setVisibility(View.VISIBLE);
               rev_complete_booking.setVisibility(View.GONE);

                NewCasebtn.setBackgroundColor(getResources().getColor(R.color.blue));
                SolveCaseBtn.setBackgroundColor(getResources().getColor(R.color.white));


            }
        });

        SolveCaseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                rev_booking.setVisibility(View.GONE);
                rev_complete_booking.setVisibility(View.VISIBLE);

                SolveCaseBtn.setBackgroundColor(getResources().getColor(R.color.blue));
                 NewCasebtn.setBackgroundColor(getResources().getColor(R.color.white));
                //startActivity(new Intent(getActivity(), HomeScreen1MainActivity.class));
            }
        });

        showBooking();
        completeBooking();

        return view;
    }


    private void showBooking() {
        progressBar.setVisibility(View.VISIBLE);
        Log.e("UserId", UserId);
        AndroidNetworking.post(API.show_doctor_booking)
                .addBodyParameter("user_id",UserId)
                .setTag("ShowBooking")
                .setPriority(Priority.HIGH)
                .build()
                .getAsJSONArray(new JSONArrayRequestListener() {
                    @Override
                    public void onResponse(JSONArray response) {
                        progressBar.setVisibility(View.GONE);
                        modelShowBookingArrayList = new ArrayList<>();

                        Log.e("trgtffgf", "onResponse: "+response.toString());

                        try {
                            for (int i = 0; i < response.length(); i++) {
                                JSONObject jsonObject = response.getJSONObject(i);
                                ModelShowBooking showBooking = new ModelShowBooking();
                                showBooking.setDates(jsonObject.getString("dates"));
                                showBooking.setTime(jsonObject.getString("time"));
                             //   showBooking.setName(jsonObject.getString("name"));
                                showBooking.setStatus(jsonObject.getString("status"));
                                showBooking.setId(jsonObject.getString("id"));
                                Log.e("dknfckldncfkmcf", jsonObject.getString("id"));


                                JSONArray jsonArray = new JSONArray(jsonObject.getString("user_details"));
                                for (int j = 0; j < jsonArray.length(); j++) {

                                    Log.e("fyyt", "onResponse: "+response);
                                    JSONObject jsonObject1 = jsonArray.getJSONObject(j);

                                    showBooking.setImage(jsonObject1.getString("image"));
                                    showBooking.setPath(jsonObject1.getString("path"));
                                    showBooking.setName(jsonObject1.getString("name"));
                                    Log.e("dgvdfgbhb", jsonObject1.getString("name"));
                                    showBooking.setAge(" Age " + jsonObject1.getString("age"));
                                    modelShowBookingArrayList.add(showBooking);
                                }


                            }



                            rev_booking.setHasFixedSize(true);
                            rev_booking.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false));
                            rev_booking.setAdapter(new ShowBookingAdapter(modelShowBookingArrayList, getActivity()));


                        } catch (JSONException e) {
                            progressBar.setVisibility(View.GONE);
                            e.printStackTrace();
                            Log.e("rtyrttgrgt", "onResponse: " +e.getMessage());
                        }


                    }

                    @Override
                    public void onError(ANError anError) {
                        progressBar.setVisibility(View.GONE);
                        Log.e("fcdsdsfcsd", "onError: " +anError.getMessage());



                    }
                });



    }



    private void completeBooking() {

        Log.e("UserId", UserId);
        AndroidNetworking.post(API.show_complete_booking)
                    .addBodyParameter("user_id",UserId)
                .setTag("CompleteBooking")
                .setPriority(Priority.HIGH)
                .build()
                .getAsJSONArray(new JSONArrayRequestListener() {
                    @Override
                    public void onResponse(JSONArray response) {

                        modelCompleteBookingArrayList=new ArrayList<>();
                                        Log.e("vcnvgv", "onResponse: " +response);
                             try {
                                 for (int i=0;i<response.length();i++) {
                                     JSONObject jsonObject=response.getJSONObject(i);
                                     ModelCompleteBooking completeBooking=new ModelCompleteBooking();
                                     completeBooking.setDates(jsonObject.getString("dates"));
                                     completeBooking.setTime(jsonObject.getString("time"));
                                     completeBooking.setStatus(jsonObject.getString("status"));


                                     JSONArray jsonArray=new JSONArray(jsonObject.getString("user_details"));
                                     for (int j=0;j<jsonArray.length();j++) {
                                         Log.e("msg", "onResponse: " +response);
                                         JSONObject jsonObject1=jsonArray.getJSONObject(j);
                                         completeBooking.setId(jsonObject1.getString("id"));
                                         completeBooking.setImage(jsonObject1.getString("image"));
                                         completeBooking.setName(jsonObject1.getString("name"));
                                         completeBooking.setAge("  Age " +jsonObject1.getString("age"));
                                         completeBooking.setPath(jsonObject1.getString("path"));
                                         modelCompleteBookingArrayList.add(completeBooking);

                                     }
                                 }

                                 rev_complete_booking.setHasFixedSize(true);
                                 rev_complete_booking.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false));
                                 rev_complete_booking.setAdapter(new CompleteBookingAdapter(modelCompleteBookingArrayList, getActivity()));




                             } catch (Exception e) {
                                 e.printStackTrace();
                                 Log.e("vbklvgbm", "onResponse: " +e.getMessage());
                             }

                    }

                    @Override
                    public void onError(ANError anError) {

                        Log.e("xjshnxdjscd", "onError: " +anError.getMessage());
                    }
                });
    }



}
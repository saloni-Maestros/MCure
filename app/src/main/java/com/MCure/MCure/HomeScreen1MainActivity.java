package com.MCure.MCure;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.google.android.material.button.MaterialButton;
import com.MCure.MCure.Adapter.ShowBookingAdapter;
import com.MCure.MCure.ModelClass.ModelShowBooking;

import java.util.ArrayList;

public class HomeScreen1MainActivity extends AppCompatActivity {

    MaterialButton SolveCaseBtn, NewCasebtn;
    RecyclerView recyclerview;
    ShowBookingAdapter bookingAdapter;
    ArrayList<ModelShowBooking> ModelShowBookings;
    String UserId = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen1_main);
/*        SolveCaseBtn = findViewById(R.id.SolveCaseBtn);
        NewCasebtn=findViewById(R.id.NewCasebtn);
        recyclerview = findViewById(R.id.rev_booking);

        UserId = SharedHelper.getKey(HomeScreen1MainActivity.this, APPCONSTANT.USERID);
        Log.e("userid", UserId);

        NewCasebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                showBooking();
            }
        });

        SolveCaseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
             // startActivity(new Intent(HomeScreen1MainActivity.this, HomeScreen2Activity.class));

              // showBooking();


            }
        });

      // showBooking();

    }

    private void showBooking() {

        AndroidNetworking.post(API.show_doctor_booking)
                .addBodyParameter("user_id", UserId)
                .setTag("ShowBooking")
                .setPriority(Priority.HIGH)
                .build()
                .getAsJSONArray(new JSONArrayRequestListener() {

                    @Override
                    public void onResponse(JSONArray response) {
                        ModelShowBookings = new ArrayList<>();

                        Log.e("tttt", "onResponse: "+response);

                        try {
                            for (int i = 0; i < response.length(); i++) {
                                JSONObject jsonObject = response.getJSONObject(i);
                                ModelShowBooking showBooking = new ModelShowBooking();

                               showBooking.setShowId(jsonObject.getString("id"));
                                showBooking.setShowDatetime(jsonObject.getString("dates"));
                                showBooking.setShowDatetime(jsonObject.getString("time"));
                                showBooking.setShowStatus(jsonObject.getString("status"));
                                showBooking.setShowUserName(jsonObject.getString("name"));
                                showBooking.setShowAge(jsonObject.getString("age"));
                              // showBooking.setShowPath(jsonObject.getString("ImagePath"));


                                JSONArray jsonArray1 = new JSONArray(jsonObject.getString("user_details"));
                                for (int j = 0; j < jsonArray1.length(); j++) {
                                    JSONObject jsonObject1 = jsonArray1.getJSONObject(j);
                                    showBooking.setShowPic(jsonObject1.getString("image"));
                                    showBooking.setShowPath(jsonObject1.getString("Path"));

                                    ModelShowBookings.add(showBooking);


                                }


                            }


                            bookingAdapter = new ShowBookingAdapter(ModelShowBookings, HomeScreen1MainActivity.this);
                            recyclerview.setHasFixedSize(true);
                            recyclerview.setLayoutManager(new LinearLayoutManager(HomeScreen1MainActivity.this,LinearLayoutManager.VERTICAL,false));
                            recyclerview.setAdapter(bookingAdapter);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }

                    @Override
                    public void onError(ANError anError) {

                    }
                });

    }*/
    }
}
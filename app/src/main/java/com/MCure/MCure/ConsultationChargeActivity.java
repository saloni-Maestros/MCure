package com.MCure.MCure;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONArrayRequestListener;
import com.google.android.material.button.MaterialButton;
import com.MCure.MCure.Adapter.BankTransationAdapter;
import com.MCure.MCure.Adapter.MyEarningAdapter;
import com.MCure.MCure.ModelClass.ModelBankTransation;
import com.MCure.MCure.ModelClass.ModelsEarning;
import com.MCure.MCure.others.API;
import com.MCure.MCure.others.APPCONSTANT;
import com.MCure.MCure.others.SharedHelper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ConsultationChargeActivity extends AppCompatActivity {

  ProgressBar progressBar;

    MaterialButton mbtn_earning, mbtn_Banktrans;
    ImageView imgarrow_charges;
    String UserId = "";

    RecyclerView rev_Myearning;
    MyEarningAdapter earningAdapter;                   //for Earning button data
    ArrayList<ModelsEarning> modelsEarningArrayList;

    RecyclerView rev_BankTransaction;        // for bank transations button data
    BankTransationAdapter bankTransationAdapter;
    ArrayList<ModelBankTransation> modelBankTransationArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consultation_charge);
        progressBar = findViewById(R.id.progressBar);
        imgarrow_charges = findViewById(R.id.imgarrow_charges);
        mbtn_earning = findViewById(R.id.mbtn_earning);
        mbtn_Banktrans = findViewById(R.id.mbtn_Banktrans);
        rev_Myearning = findViewById(R.id.rev_Myearning);
        rev_BankTransaction = findViewById(R.id.rev_BankTransaction);


        UserId = SharedHelper.getKey(ConsultationChargeActivity.this, APPCONSTANT.USERID);
        Log.e("userid", UserId);

        imgarrow_charges.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        mbtn_earning.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rev_Myearning.setVisibility(View.VISIBLE);
                rev_BankTransaction.setVisibility(View.GONE);

                mbtn_earning.setBackgroundColor(getResources().getColor(R.color.blue));
                mbtn_Banktrans.setBackgroundColor(getResources().getColor(R.color.white));


            }
        });
        mbtn_Banktrans.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rev_Myearning.setVisibility(View.GONE);
                rev_BankTransaction.setVisibility(View.VISIBLE);

                mbtn_earning.setBackgroundColor(getResources().getColor(R.color.white));
                mbtn_Banktrans.setBackgroundColor(getResources().getColor(R.color.blue));
                bank_Transation_History();

            }
        });

        showEarning();

    }

    private void showEarning() {

        Log.e("UserId", UserId);
        progressBar.setVisibility(View.VISIBLE);
        AndroidNetworking.post(API.show_earning_history)
                .addBodyParameter("user_id", UserId)
                .setTag("showEarning")
                .setPriority(Priority.HIGH)
                .build()
                .getAsJSONArray(new JSONArrayRequestListener() {
                    @Override
                    public void onResponse(JSONArray response) {
                        progressBar.setVisibility(View.GONE);
                        modelsEarningArrayList = new ArrayList<>();

                        Log.e("response", "onResponse: " + response);

                        try {
                            for (int i = 0; i < response.length(); i++) {
                                JSONObject jsonObject = response.getJSONObject(i);
                                ModelsEarning modelsEarning = new ModelsEarning();
                                modelsEarning.setPayment_amount(jsonObject.getString("payment_amount"));
                                modelsEarning.setDate_time(jsonObject.getString("date_time"));


                                JSONArray jsonArray1 = new JSONArray(jsonObject.getString("user_details"));
                                for (int j = 0; j < jsonArray1.length(); j++) {

                                    Log.e("kjdff", "onResponse: " + response);


                                    JSONObject jsonObject1 = jsonArray1.getJSONObject(j);
                                    modelsEarning.setId(jsonObject1.getString("id"));
                                    modelsEarning.setName(jsonObject1.getString("name"));
                                    modelsEarning.setAge(" Age " + jsonObject1.getString("age"));
                                    modelsEarning.setImage(jsonObject1.getString("image"));
                                    modelsEarning.setPath(jsonObject1.getString("path"));
                                    modelsEarningArrayList.add(modelsEarning);

                                }
                            }


                            rev_Myearning.setHasFixedSize(true);
                            rev_Myearning.setLayoutManager(new LinearLayoutManager(ConsultationChargeActivity.this, LinearLayoutManager.VERTICAL, false));
                            rev_Myearning.setAdapter(new MyEarningAdapter(modelsEarningArrayList, ConsultationChargeActivity.this));
                        } catch (JSONException e) {
                            progressBar.setVisibility(View.GONE);
                            e.printStackTrace();
                            Log.e("rtyrttgrgt", "onResponse: " + e.getMessage());
                        }


                    }

                    @Override
                    public void onError(ANError anError) {
                        progressBar.setVisibility(View.GONE);
                        Log.e("fcdsdsfcsd", "onError: " + anError.getMessage());


                    }
                });
    }

    public void bank_Transation_History() {
        progressBar.setVisibility(View.VISIBLE);
        AndroidNetworking.post(API.show_earning_history)
                .addBodyParameter("user_id", UserId)
                .setTag("bank_Transation_History")
                .setPriority(Priority.HIGH)
                .build()
                .getAsJSONArray(new JSONArrayRequestListener() {
                    @Override
                    public void onResponse(JSONArray response) {
                        progressBar.setVisibility(View.GONE);
                        modelBankTransationArrayList = new ArrayList<>();
                        Log.e("kjnckcnc", "onResponse: " + response.toString());
                        try {
                            for (int i = 0; i < response.length(); i++) {
                                JSONObject jsonObject = response.getJSONObject(i);
                                ModelBankTransation modelBankTransation = new ModelBankTransation();
                                modelBankTransation.setPayment_amount(jsonObject.getString("payment_amount"));
                                modelBankTransation.setAccount_number(jsonObject.getString("account_number"));
                                modelBankTransationArrayList.add(modelBankTransation);
                            }

                            rev_BankTransaction.setHasFixedSize(true);
                            rev_BankTransaction.setLayoutManager(new LinearLayoutManager(ConsultationChargeActivity.this, LinearLayoutManager.VERTICAL, false));
                            rev_BankTransaction.setAdapter(new BankTransationAdapter(modelBankTransationArrayList, ConsultationChargeActivity.this));
                        } catch (Exception e) {
                            progressBar.setVisibility(View.GONE);
                            Log.e("ndcvmnvyur", "onResponse: " + e.getMessage());
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        progressBar.setVisibility(View.GONE);
                        Log.e("hjkdhcddc", "onError: " + anError);

                    }
                });
    }
}
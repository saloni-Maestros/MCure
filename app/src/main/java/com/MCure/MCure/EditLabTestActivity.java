package com.MCure.MCure;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONArrayRequestListener;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.google.android.material.button.MaterialButton;
import com.MCure.MCure.others.API;
import com.MCure.MCure.others.APPCONSTANT;
import com.MCure.MCure.others.SharedHelper;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class EditLabTestActivity extends AppCompatActivity  implements AdapterView.OnItemSelectedListener {
    ImageView iv_backarrow;
    String Id="";
    String packageId="";
    String UserId="";
    Spinner pac_spinner;
    EditText et_Tittle,et_description;
    MaterialButton btn_submit;
    LinearLayout et_select;

    ArrayList<String> Arr_id = new ArrayList<>();                    // show multiple occupation dynamicallly
    ArrayList<String> Arr_Tittle = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_lab_test);
        et_select = findViewById(R.id.et_select);
        et_Tittle = findViewById(R.id.et_Tittle);
        et_description = findViewById(R.id.et_description);
        btn_submit = findViewById(R.id.btn_submit);
        pac_spinner = findViewById(R.id.pac_spinner);
        pac_spinner.setOnItemSelectedListener(this);


        iv_backarrow = findViewById(R.id.iv_backarrow);
        iv_backarrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        Id = getIntent().getStringExtra("id");
       // et_select.setText(getIntent().getStringExtra("title"));
        et_Tittle.setText(getIntent().getStringExtra("title"));
        et_description.setText(getIntent().getStringExtra("description"));

        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edit_package_test();
            }
        });

        UserId = SharedHelper.getKey(EditLabTestActivity.this, APPCONSTANT.USERID);
        packageId = SharedHelper.getKey(EditLabTestActivity.this, APPCONSTANT.PACKAGEID);
        Log.e("fgddfdfxdx", packageId);
        Log.e("fgddfdfxdx", UserId);
        show_pacakge();
    }
        public void   edit_package_test(){
            AndroidNetworking.post(API.edit_package_test)
                    .addBodyParameter("id",Id)
                    .addBodyParameter("package_id",packageId)
                    .addBodyParameter("title",et_Tittle.getText().toString().trim())
                    .addBodyParameter("description",et_description.getText().toString().trim())
                    .setPriority(Priority.HIGH)
                    .setTag("edit_package_test")
                    .build()
                    .getAsJSONObject(new JSONObjectRequestListener() {
                        @Override
                        public void onResponse(JSONObject response) {
                            Log.e("fcfff", "onResponse: " +response.toString());
                            try {
                                if (response.getString("result").equals("succesfully")){
                                    startActivity(new Intent(EditLabTestActivity.this,ShowPackageTestActivity.class));
                                }

                            } catch (Exception e){
                                Log.e("fjkcjfkcjf", "onResponse: " +e.getMessage());

                            }
                        }

                        @Override
                        public void onError(ANError anError) {
                            Log.e("ncvmvnmxv", "onError: " +anError);

                        }
                    });


    }


    public void show_pacakge(){
        AndroidNetworking.post(API.show_lab_package)
                .addBodyParameter("user_id",UserId)
                .setTag("show_lab_package")
                .setPriority(Priority.HIGH)
                .build()
                .getAsJSONArray(new JSONArrayRequestListener() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.e("hfcudhufd", "onResponse: " +response.toString());
                        Arr_id = new ArrayList<>();
                        Arr_Tittle = new ArrayList<>();
                        Arr_id.add("0");
                        Arr_Tittle.add("Select Package");
                        try {
                            for (int i =0;i<response.length(); i++){
                                JSONObject jsonObject = response.getJSONObject(i);
                                Arr_id.add(jsonObject.getString("id"));
                                Arr_Tittle.add(jsonObject.getString("title"));
                            }
                            ArrayAdapter adapter = new ArrayAdapter<String>(getApplicationContext(),
                                    android.R.layout.simple_spinner_item, Arr_Tittle);
                            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            pac_spinner.setAdapter(adapter);
                            pac_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                    String tutorialsName = Arr_Tittle.get(position);
                                }

                                @Override
                                public void onNothingSelected(AdapterView<?> parent) {

                                }
                            });




                        } catch (Exception e){
                            Log.e("tutkl", e.getMessage());

                        }


                    }

                    @Override
                    public void onError(ANError anError) {
                        Log.e("hlkdkl", "onError: " + anError);

                    }
                });
    }
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }


}
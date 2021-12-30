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

public class PackageActivity extends AppCompatActivity  implements AdapterView.OnItemSelectedListener {
    ImageView iv_backarrow;
    Spinner pac_spinner;
    ArrayList<String> arrayList;
    String UserId="";
    String packageId="";
    EditText et_title,et_des_packg;
    MaterialButton btn_Add;
    LinearLayout et_select_items;
    ArrayList<String> Arr_id = new ArrayList<>();                    // show multiple occupation dynamicallly
    ArrayList<String> Arr_Tittle = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_package);
        et_select_items = findViewById(R.id.et_select_items);

        pac_spinner = findViewById(R.id.pac_spinner);
        pac_spinner.setOnItemSelectedListener(this);


        btn_Add = findViewById(R.id.btn_Add);
        et_title=findViewById(R.id.et_title);
        et_des_packg=findViewById(R.id.et_des_packg);
        iv_backarrow = findViewById(R.id.iv_backarrow);
        iv_backarrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    /*    pac_spinner = findViewById(R.id.pac_spinner);
        arrayList = new ArrayList<>();
        arrayList.add("Select items type");
        arrayList.add("PACKAGE TITLE");
        arrayList.add("TITLE");
        arrayList.add("PACKAGE");

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, arrayList);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        pac_spinner.setAdapter(arrayAdapter);
        pac_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ((TextView) parent.getChildAt(0)).setTextSize(12);
                ((TextView) parent.getChildAt(0)).setTextColor(getResources().getColor(R.color.black));
                String strselectr = parent.getItemAtPosition(position).toString();
                String tct = parent.getSelectedItem().toString();
                Log.i("TAG", "Value" + tct);
                if (strselectr.equals("Select store type")) {



                } else if (strselectr.equals("PACKAGE TITLE")) {
                     // product_type="1";

                } else if (strselectr.equals("TITLE")) {
                     // product_type="2";

                } else if (strselectr.equals("PACKAGE")) {
                    // product_type="3";
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
*/
        btn_Add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                add_package_test();

            }
        });
        UserId = SharedHelper.getKey(PackageActivity.this, APPCONSTANT.USERID);
        packageId = SharedHelper.getKey(PackageActivity.this, APPCONSTANT.PACKAGEID);
         Log.e("fgddfdfxdx", packageId);
         Log.e("fgddfdfxdx", UserId);

         show_pacakge();
    }

     public void add_package_test(){
         Log.e("dfdsfdsfdsf", packageId);
         AndroidNetworking.post(API.add_package_test)
                 .addBodyParameter("user_id",UserId)
                 .addBodyParameter("package_id ",packageId )
                 .addBodyParameter("title", et_title.getText().toString().trim())
                 .addBodyParameter("description", et_des_packg.getText().toString().trim())
                 .setTag("add_package_test")
                 .setPriority(Priority.HIGH)
                 .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.e("shdhdjsdh", "onResponse: " +response.toString());
                        try {
                            if (response.getString("result").equals("Successfully")){
                                startActivity(new Intent(PackageActivity.this,ShowPackageTestActivity.class));
                            }
                        } catch (Exception e){
                            Log.e("qwooiew", "onResponse: " +e.getMessage());
                        }
                    }
                    @Override
                    public void onError(ANError anError) {
                        Log.e("vfdfjcjkd", "onError: " +anError);
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
package com.MCure.MCure;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.google.android.material.button.MaterialButton;
import com.MCure.MCure.others.API;

import org.json.JSONException;
import org.json.JSONObject;

public class EditLabPackageActivity extends AppCompatActivity {

    EditText ed_title_editpg,ed_des_editpg,ed_labcharge_editpg;
    MaterialButton mbtn_ed_submit;
    String Id="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_lab_package);
        ed_title_editpg=findViewById(R.id.ed_title_editpg);
        ed_des_editpg=findViewById(R.id.ed_des_editpg);
        ed_labcharge_editpg=findViewById(R.id.ed_labcharge_editpg);
        mbtn_ed_submit=findViewById(R.id.mbtn_ed_submit);

       /* Id= SharedHelper.getKey(EditLabPackageActivity.this, APPCONSTANT.ID);
        Log.e("id", Id); */
        Id = getIntent().getStringExtra("id");
        ed_title_editpg.setText(getIntent().getStringExtra("title"));
        ed_des_editpg.setText(getIntent().getStringExtra("description"));
        ed_labcharge_editpg.setText(getIntent().getStringExtra("price"));

        mbtn_ed_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editLabPackage();
            }
        });



    }

    private void editLabPackage() {

        Log.e("id",Id);
        AndroidNetworking.post(API.edit_lab_package)
                .addBodyParameter("id",Id)
                .addBodyParameter("title",ed_title_editpg.getText().toString().trim())
                .addBodyParameter("description",ed_des_editpg.getText().toString().trim())
                .addBodyParameter("price",ed_labcharge_editpg.getText().toString().trim())
                .setPriority(Priority.HIGH)
                .setTag("delete_lab_package")
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {

                        Log.e("hjsa", "onResponse: " +response.toString());
                        try {
                            if (response.getString("result").equals("succesfully")){
                                startActivity(new Intent(EditLabPackageActivity.this,LabShowPackageActivity.class));
                            }
                        } catch (JSONException e) {
                            Log.e("exceptionmsg", "onResponse: " +e.getMessage());
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        Log.e("errormsg", "onError: " +anError.getMessage());

                    }
                });
    }
}
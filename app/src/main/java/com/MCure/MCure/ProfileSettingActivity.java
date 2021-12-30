package com.MCure.MCure;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONArrayRequestListener;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.android.material.button.MaterialButton;
import com.MCure.MCure.others.API;
import com.MCure.MCure.others.APPCONSTANT;
import com.MCure.MCure.others.SharedHelper;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;

public class ProfileSettingActivity extends AppCompatActivity {
    MaterialButton materialbuttonsave;
    ProgressBar progressBar;
    String USERID = "";
    String DRNAME = "";
    EditText ev_drname, et_experience, et_city, et_clgUniver, et_clgname, et_clgname2;
    ImageView iv_upload;
    EditText Ev_Regno;
    Spinner sp_mbbs;
    ArrayList<String> arr_specID;
    ArrayList<String> arr_specName;
    ImageView imgarrow_profile;
    private static final String IMAGE_DIRECTORY = "/directory";
    private final int GALLERY = 1;
    private final int CAMERA = 2;
    File f;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_setting);
        progressBar = findViewById(R.id.progressBar);
        ev_drname = findViewById(R.id.ev_drname);
        Ev_Regno = findViewById(R.id.Ev_Regno);
        et_clgUniver = findViewById(R.id.et_clgUniver);
        et_clgname = findViewById(R.id.et_clgname);
        et_experience = findViewById(R.id.et_experience);
        et_city = findViewById(R.id.et_city);
        materialbuttonsave = findViewById(R.id.materialbuttonsave);
        sp_mbbs = findViewById(R.id.sp_mbbs);
        imgarrow_profile = findViewById(R.id.imgarrow_profile);
        iv_upload = findViewById(R.id.iv_upload);
        imgarrow_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        materialbuttonsave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ev_drname.getText().toString().trim().isEmpty()) {
                    Toast.makeText(ProfileSettingActivity.this, "Please enter Dr.Name ", Toast.LENGTH_SHORT).show();
                } else if (Ev_Regno.getText().toString().trim().isEmpty()) {
                    Toast.makeText(ProfileSettingActivity.this, "Please enter Reg Number", Toast.LENGTH_SHORT).show();
                } else if (et_clgUniver.getText().toString().trim().isEmpty()) {
                    Toast.makeText(ProfileSettingActivity.this, "Please enter Clg Univercity", Toast.LENGTH_SHORT).show();
                } else if (et_clgname.getText().toString().trim().isEmpty()) {
                    Toast.makeText(ProfileSettingActivity.this, "Please enter Clg Name", Toast.LENGTH_SHORT).show();
                } else if (et_experience.getText().toString().trim().isEmpty()) {
                    Toast.makeText(ProfileSettingActivity.this, "Please enter Experience", Toast.LENGTH_SHORT).show();
                } else if (et_city.getText().toString().trim().isEmpty()) {
                    Toast.makeText(ProfileSettingActivity.this, "please enter City", Toast.LENGTH_SHORT).show();
                } else {
                    update_profile();
                    progressBar.setVisibility(View.VISIBLE);
                }

            }
        });
        iv_upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPictureDialog();
            }
        });
        USERID = SharedHelper.getKey(ProfileSettingActivity.this, APPCONSTANT.USERID);
        Log.e("gdfgdf", USERID);
        shopwSpecialist();
        DRNAME = SharedHelper.getKey(ProfileSettingActivity.this, APPCONSTANT.DRNAME);
        Log.e("vfrdxgv", DRNAME);


    }


    public void showPictureDialog() {
        androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(ProfileSettingActivity.this);
        builder.setTitle("Select Action");
        String[] pictureDialogItems = {"Select photo from gallery", "Capture image from camera"};

        builder.setItems(pictureDialogItems, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                switch (which) {

                    case 0:
                        choosePhotoFromGallery();
                        break;

                    case 1:
                        captureFromCamera();
                        break;
                }

            }
        });

        builder.show();
    }

    public void choosePhotoFromGallery() {

        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

        startActivityForResult(intent, GALLERY);
    }

    public void captureFromCamera() {

        Intent intent_2 = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent_2, CAMERA);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_CANCELED) {
            return;
        }
        if (requestCode == GALLERY) {
            if (data != null) {
                Uri contentURI = data.getData();
                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(ProfileSettingActivity.this.getContentResolver(), contentURI);
                    String path = saveImage(bitmap);
                    iv_upload.setImageBitmap(bitmap);

                } catch (IOException e) {
                    e.printStackTrace();
                    Toast.makeText(ProfileSettingActivity.this, "Failed!", Toast.LENGTH_SHORT).show();
                }
            }

        } else if (requestCode == CAMERA) {
            Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
            iv_upload.setImageBitmap(thumbnail);
            saveImage(thumbnail);

            Toast.makeText(ProfileSettingActivity.this, "Image Saved!", Toast.LENGTH_SHORT).show();
        }
    }

    public String saveImage(Bitmap myBitmap) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        myBitmap.compress(Bitmap.CompressFormat.JPEG, 90, bytes);
        File wallpaperDirectory = new File(
                Environment.getExternalStorageDirectory() + IMAGE_DIRECTORY);

        if (!wallpaperDirectory.exists()) {
            wallpaperDirectory.mkdirs();
        }

        try {
            f = new File(wallpaperDirectory, Calendar.getInstance()
                    .getTimeInMillis() + ".jpg");
            f.createNewFile();
            FileOutputStream fo = new FileOutputStream(f);
            fo.write(bytes.toByteArray());
            MediaScannerConnection.scanFile(ProfileSettingActivity.this,
                    new String[]{f.getPath() + "/path"},
                    new String[]{"image/jpeg"}, null);
            fo.close();
            Log.d("fvbcbv", "File Saved::---&gt;" + f.getAbsolutePath() + "/path");

            return f.getAbsolutePath() + "/path";
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        return "";
    }

    public void update_profile() {
        progressBar.setVisibility(View.VISIBLE);
        Log.e("ututru", f + "1");
        AndroidNetworking.upload(API.update_profile)
                .addMultipartParameter("user_id", USERID)
                .addMultipartParameter("name", ev_drname.getText().toString().trim())
                .addMultipartParameter("registration_number", Ev_Regno.getText().toString().trim())
                .addMultipartParameter("univercity", et_clgUniver.getText().toString().trim())
                .addMultipartParameter("collega_name", et_clgname.getText().toString().trim())
                .addMultipartParameter("expriece", et_experience.getText().toString().trim())
                .addMultipartParameter("city", et_city.getText().toString().trim())
                .addMultipartFile("image", f)
                .setTag("update_profile")
                .setPriority(Priority.HIGH)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        progressBar.setVisibility(View.GONE);
                        Log.e("jcjmvcmv", "onResponse: " + response.toString());
                        try {
                            if (response.getString("result").equals("Profile update succesfully")) {
                                Glide.with(ProfileSettingActivity.this).load(response.getString("path") + response.getString("image"))
                                        // .placeholder(R.drawable.user_icon).override(250, 250)
                                        .diskCacheStrategy(DiskCacheStrategy.ALL).into(iv_upload);
                                ev_drname.setText(response.getString("name"));
                                SharedHelper.putkey(ProfileSettingActivity.this, APPCONSTANT.DRNAME, "name");
                                Log.e("fcdfb", response.getString("name"));
                                Ev_Regno.setText(response.getString("registration_number"));
                                et_clgUniver.setText(response.getString("univercity"));
                                et_clgname.setText(response.getString("collega_name"));
                                et_experience.setText(response.getString("expriece"));
                                et_city.setText(response.getString("city"));
                                Toast.makeText(ProfileSettingActivity.this, "" + response.getString("result"), Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(ProfileSettingActivity.this, "" + response.getString("result"), Toast.LENGTH_SHORT).show();
                            }

                        } catch (Exception e) {
                            progressBar.setVisibility(View.GONE);
                            Log.e("dmncmfdxnc", "onResponse: " + e.getMessage());

                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        progressBar.setVisibility(View.GONE);
                        Log.e("mcckdkm", "onError: " + anError);

                    }
                });

    }


    public void shopwSpecialist() {
        AndroidNetworking.post(API.show_speciality)
                .setPriority(Priority.HIGH)
                .build()
                .getAsJSONArray(new JSONArrayRequestListener() {
                    @Override
                    public void onResponse(JSONArray response) {

                        arr_specID = new ArrayList<>();
                        arr_specName = new ArrayList<>();
                        arr_specID.add("0");
                        arr_specName.add("Select speciality");

                        try {
                            for (int i = 0; i < response.length(); i++) {
                                JSONObject jsonObject = response.getJSONObject(i);
                                arr_specID.add(jsonObject.getString("id"));
                                arr_specName.add(jsonObject.getString("name"));

                            }
                            ArrayAdapter adapter = new ArrayAdapter<String>(getApplicationContext(),
                                    android.R.layout.simple_spinner_item, arr_specName);
                            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            sp_mbbs.setAdapter(adapter);
                            sp_mbbs.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                    String tutorialsName = arr_specName.get(position);
                                    Toast.makeText(parent.getContext(), "Selected: " + tutorialsName, Toast.LENGTH_LONG).show();
                                }

                                @Override
                                public void onNothingSelected(AdapterView<?> parent) {
                                }
                            });
                        } catch (Exception e) {
                            Log.e("msdkcfldk", "onResponse: " +e.getMessage());

                        }

                    }

                    @Override
                    public void onError(ANError anError) {
                        Log.e("eddhdjemd", "onError: " +anError);

                    }
                });
    }
}
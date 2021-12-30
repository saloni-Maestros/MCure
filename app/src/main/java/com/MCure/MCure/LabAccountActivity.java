package com.MCure.MCure;

import androidx.appcompat.app.AppCompatActivity;

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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.MCure.MCure.others.API;
import com.MCure.MCure.others.APPCONSTANT;
import com.MCure.MCure.others.SharedHelper;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.Calendar;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.OkHttpClient;
import okhttp3.Protocol;

public class LabAccountActivity extends AppCompatActivity {
    ImageView iv_back,img_LabuserCamera;
    EditText et_email, et_address, et_contact, et_labname;
    Button btn_update;
    ProgressBar progressBar;
    String UserId="";
    CircleImageView img_Labuser;
    private static final String IMAGE_DIRECTORY = "/directory";
    private final int GALLERY = 1;
    private final int CAMERA = 2;
    File f;
   // private Handler AndroidNetworking;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lab_account);
        img_LabuserCamera = findViewById(R.id.img_LabuserCamera);
        iv_back=findViewById(R.id.iv_back);
        et_email=findViewById(R.id.et_email);
        et_address=findViewById(R.id.et_address);
        et_contact=findViewById(R.id.et_contact);
        et_labname=findViewById(R.id.et_labname);
        progressBar = findViewById(R.id.progressBar);
        btn_update=findViewById(R.id.btn_update);
        img_Labuser = findViewById(R.id.img_Labuser);

        UserId = SharedHelper.getKey(LabAccountActivity.this, APPCONSTANT.USERID);
        Log.e("userid", UserId);

        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

       btn_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (et_labname.getText().toString().trim().isEmpty()){
                    Toast.makeText(LabAccountActivity.this, "please Enter lab Name", Toast.LENGTH_SHORT).show();
                }else if(et_contact.getText().toString().trim().isEmpty()){
                    Toast.makeText(LabAccountActivity.this, "please Enter contact Number", Toast.LENGTH_SHORT).show();
                }else if (et_address.getText().toString().trim().isEmpty()){
                    Toast.makeText(LabAccountActivity.this,"please Enter Address",Toast.LENGTH_SHORT).show();
                }else if (et_email.getText().toString().trim().isEmpty()){
                    Toast.makeText(LabAccountActivity.this,"please Enter Email",Toast.LENGTH_SHORT).show();
                }else{

                    update_labuser_profile();

                }
            }
        });
        img_LabuserCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPictureDialog();
            }
        });
        show_profile();

    }
    public void showPictureDialog() {
        androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(LabAccountActivity.this);
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
    } public void choosePhotoFromGallery() {

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
        if (resultCode ==RESULT_CANCELED) {
            return;
        }
        if (requestCode == GALLERY) {
            if (data != null) {
                Uri contentURI = data.getData();
                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(LabAccountActivity.this.getContentResolver(), contentURI);
                    String path = saveImage(bitmap);

                    img_Labuser.setImageBitmap(bitmap);

                } catch (IOException e) {
                    e.printStackTrace();
                    Toast.makeText(LabAccountActivity.this, "Failed!", Toast.LENGTH_SHORT).show();
                }
            }

        } else if (requestCode == CAMERA) {
            Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
            img_Labuser.setImageBitmap(thumbnail);
            saveImage(thumbnail);

            Toast.makeText(LabAccountActivity.this, "Image Saved!", Toast.LENGTH_SHORT).show();
        }
    } public String saveImage(Bitmap myBitmap) {
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
            MediaScannerConnection.scanFile(LabAccountActivity.this,
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
    private void update_labuser_profile(){
        if (f == null) {
            AndroidNetworking.initialize(LabAccountActivity.this);
            OkHttpClient okHttpClient = new OkHttpClient().newBuilder().protocols(Arrays.asList(Protocol.HTTP_1_1)).build();
            AndroidNetworking.initialize(LabAccountActivity.this, okHttpClient);
            Log.e("LabAccountActivity", UserId);
            AndroidNetworking.post(API.update_labuser_profile)
                    .addBodyParameter("user_id",UserId)
                    .addBodyParameter("lab_name",et_labname.getText().toString().trim())
                    .addBodyParameter("lab_contact_number",et_contact.getText().toString().trim())
                    .addBodyParameter("address",et_address.getText().toString().trim())
                    .addBodyParameter("email",et_email.getText().toString().trim())
                    .setTag("update_labuser_profile")
                    .setPriority(Priority.HIGH)
                    .build()
                    .getAsJSONObject(new JSONObjectRequestListener() {
                        @Override
                        public void onResponse(JSONObject response) {
                            Log.e("yuyued", "onResponse: " +response.toString());

                            try {
                                if (response.getString("result").equals("Profile update succesfully")) {
                                    // startActivity(new Intent(LabAccountActivity.this,LabHomeActivity.class));
                                    Toast.makeText(LabAccountActivity.this, "" + response.getString("result"), Toast.LENGTH_SHORT).show();
                                } else {
                                   // Toast.makeText(LabAccountActivity.this, "" + response.getString("result"), Toast.LENGTH_SHORT).show();

                                }

                            } catch (Exception e) {
                                e.printStackTrace();
                                Log.e("kgfvdljd", "onResponse: " +e.getMessage());
                            }
                        }

                        @Override
                        public void onError(ANError anError) {
                            Log.e("swasxb", "onError: " +anError.getMessage());

                        }
                    });

        }  else {
            Log.e("hfghfdg", f + "");
            AndroidNetworking.initialize(LabAccountActivity.this);
            OkHttpClient okHttpClient = new OkHttpClient().newBuilder().protocols(Arrays.asList(Protocol.HTTP_1_1)).build();
            AndroidNetworking.initialize(LabAccountActivity.this, okHttpClient);
            AndroidNetworking.upload(API.update_labuser_profile)
            .addMultipartParameter("user_id",UserId)
                    .addMultipartParameter("lab_name",et_labname.getText().toString().trim())
                    .addMultipartParameter("lab_contact_number",et_contact.getText().toString().trim())
                    .addMultipartParameter("address",et_address.getText().toString().trim())
                    .addMultipartParameter("email",et_email.getText().toString().trim())
                    .addMultipartFile("image", f)
                    .setTag("update_labuser_profile")
                    .setPriority(Priority.HIGH)
                    .build()
                    .getAsJSONObject(new JSONObjectRequestListener() {
                        @Override
                        public void onResponse(JSONObject response) {
                            Log.e("fgvkdfjkd", "onResponse: " +response.toString());

                            try {
                                if (response.getString("result").equals("Profile update succesfully")) {
                                    Toast.makeText(LabAccountActivity.this, "" + response.getString("result"), Toast.LENGTH_SHORT).show();
                                } else {
                                  //  Toast.makeText(LabAccountActivity.this, "" + response.getString("result"), Toast.LENGTH_SHORT).show();

                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                                Log.e("wqiiwee", "onResponse: " +e.getMessage());
                            }
                        }

                        @Override
                        public void onError(ANError anError) {
                            Log.e("jdfdj", "onError: " +anError.getMessage());

                        }
                    });

        }

    }

       public void show_profile(){
        AndroidNetworking.post(API.show_profile)
                .addBodyParameter("user_id",UserId)
                .addBodyParameter("lab_name",et_labname.getText().toString().trim())
                .addBodyParameter("lab_contact_number",et_contact.getText().toString().trim())
                .addBodyParameter("address",et_address.getText().toString().trim())
                .addBodyParameter("email",et_email.getText().toString().trim())
                .setTag("update_labuser_profile")
                .setPriority(Priority.HIGH)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.e("jjjkkjk", "onResponse: " +response.toString());
                         try {
                             Glide.with(LabAccountActivity.this).load(response.getString("path") + response.getString("image"))
                                     // .placeholder(R.drawable.user_icon).override(250, 250)
                                     .diskCacheStrategy(DiskCacheStrategy.ALL).into(img_Labuser);
                             et_labname.setText(response.getString("lab_name"));
                             et_contact.setText(response.getString("lab_contact_number"));
                             et_address.setText(response.getString("address"));
                             et_email.setText(response.getString("email"));
                         } catch (Exception e){
                             Log.e("gkkfkf", "onResponse: " +e.getMessage());

                         }

                    }

                    @Override
                    public void onError(ANError anError) {
                        Log.e("cnfnfv", "onError: " +anError);
                    }
                });
       }
}
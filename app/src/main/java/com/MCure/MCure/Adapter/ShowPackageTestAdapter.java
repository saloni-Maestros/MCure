package com.MCure.MCure.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.MCure.MCure.EditLabTestActivity;
import com.MCure.MCure.ModelClass.ModelShowPackageTest;
import com.MCure.MCure.R;
import com.MCure.MCure.others.API;

import org.json.JSONObject;

import java.util.ArrayList;

public class ShowPackageTestAdapter  extends RecyclerView.Adapter<ShowPackageTestAdapter.ViewHolder> {
    String ID="";
    String id = "";
    String PACKAGEID = "";

     ArrayList<ModelShowPackageTest>modelShowPackageTestArrayList;
     Context context;

    public ShowPackageTestAdapter(ArrayList<ModelShowPackageTest> ModelShowPackageTest, Context context) {
        this.modelShowPackageTestArrayList = ModelShowPackageTest;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater=LayoutInflater.from(parent.getContext());
        View listItem=layoutInflater.inflate(R.layout.show_package_test_list,parent,false);
       ViewHolder viewHolder  = new ViewHolder(listItem);
   //     ID= SharedHelper.getKey(context, APPCONSTANT.USERID);
    //    PACKAGEID = SharedHelper.getKey(context, APPCONSTANT.PACKAGEID);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        final ModelShowPackageTest ModelShowPackageTest =modelShowPackageTestArrayList.get(position);

        holder.tv_tittle.setText(ModelShowPackageTest.getTitle());
        holder.tv_PackageTittle.setText(ModelShowPackageTest.getPackage_title());
        holder.tv_dec.setText(ModelShowPackageTest.getDescription());

        holder.iv_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                delete_package_test(ModelShowPackageTest.getId(),modelShowPackageTestArrayList,position);
            }
        });

        holder.iv_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context, EditLabTestActivity.class);
                intent.putExtra("id",ModelShowPackageTest.getId());
                intent.putExtra("title",ModelShowPackageTest.getTitle());
                intent.putExtra("description",ModelShowPackageTest.getDescription());
                intent.putExtra("package_id",ModelShowPackageTest.getPackage_id());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);

            }
        });

    }

    @Override
    public int getItemCount() {
        return modelShowPackageTestArrayList.size();
    }
     public   class  ViewHolder extends RecyclerView.ViewHolder{
         public    TextView tv_tittle,tv_PackageTittle,tv_dec;
         public  ImageView  iv_delete,iv_edit;


         public ViewHolder(@NonNull View itemView) {
             super(itemView);
             tv_tittle = itemView.findViewById(R.id.tv_tittle);
             tv_PackageTittle = itemView.findViewById(R.id.tv_PackageTittle);
             tv_dec = itemView.findViewById(R.id.tv_dec);
             tv_dec = itemView.findViewById(R.id.tv_dec);
             iv_delete = itemView.findViewById(R.id.iv_delete);
             iv_edit = itemView.findViewById(R.id.iv_edit);

         }
     }
    private void delete_package_test(String id, ArrayList<ModelShowPackageTest> modelShowPackageTestArrayList, int position) {

        Log.e("id",ID);
        AndroidNetworking.post(API.delete_package_test)
                .addBodyParameter("id",id)
                .setPriority(Priority.HIGH)
                .setTag("delete_package_test")
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.e("efdtgfrt", "onRespo " + response.toString());
                        try {

                            String str =  response.getString("result");
                            if(str.equals("Delete Successfully")){
                                Toast.makeText(context, "Deleted Product", Toast.LENGTH_SHORT).show();
                                modelShowPackageTestArrayList.remove(position);
                                notifyDataSetChanged();
                            }else{
                                Toast.makeText(context, "error", Toast.LENGTH_SHORT).show();
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                            Log.e("fgfggfdg",e.getMessage() );
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        Log.e("xszdsdsad", "onError: " + anError);
                    }
                });
    }
}

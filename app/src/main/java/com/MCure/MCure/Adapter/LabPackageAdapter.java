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
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.MCure.MCure.EditLabPackageActivity;
import com.MCure.MCure.ModelClass.ModelPackage;
import com.MCure.MCure.R;
import com.MCure.MCure.others.API;
import com.MCure.MCure.others.APPCONSTANT;
import com.MCure.MCure.others.SharedHelper;

import org.json.JSONObject;

import java.util.ArrayList;

public class LabPackageAdapter extends RecyclerView.Adapter<LabPackageAdapter.ViewHolder> {

         String ID="";
         ArrayList<ModelPackage>ModelPackages;
        Context contexts;

        public LabPackageAdapter(ArrayList<ModelPackage>ModelPackage, Context contexts) {
            this.ModelPackages = ModelPackage;
            this.contexts = contexts;
        }


        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater=LayoutInflater.from(parent.getContext());
            View listItem=layoutInflater.inflate(R.layout.layout_show_package,parent,false);
            ViewHolder viewHolder=new ViewHolder(listItem);
            ID= SharedHelper.getKey(contexts,APPCONSTANT.USERID);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {

            final ModelPackage ModelPackage=ModelPackages.get(position);

            holder.tv_tile_show.setText(ModelPackage.getPc_Title());
            holder.tv_dec_show.setText(ModelPackage.getPc_dec());
            holder.tv_prize_show.setText(ModelPackage.getPc_price());

            holder.iv_delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                  //  Log.e("hdfjkghdfjkgg", ModelPackage.getPc_Id()+"");

                    deleteLabPackage(ModelPackage.getPc_Id(),ModelPackages,position);
                }
            });
            holder.iv_edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent(contexts,EditLabPackageActivity.class);
                    intent.putExtra("id",ModelPackage.getPc_Id());
                    intent.putExtra("title",ModelPackage.getPc_Title());
                    intent.putExtra("description",ModelPackage.getPc_dec());
                    intent.putExtra("price",ModelPackage.getPc_price());
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                     contexts.startActivity(intent);

                }
            });

            //Glide.with(contexts).load(ModelPackage.getEarPath()+ModelPackage.getEarPic()).into(holder.img_docter_earning);


        }


    @Override
        public int getItemCount() {
            return ModelPackages.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {


            public TextView tv_tile_show,tv_dec_show,tv_prize_show;
            public CardView cv_show_labpack;
            public ImageView img_ruppee_pk,iv_delete,iv_edit;


            public ViewHolder(@NonNull View itemView) {
                super(itemView);

                tv_tile_show=itemView.findViewById(R.id.tv_tile_show);
                tv_dec_show=itemView.findViewById(R.id.tv_dec_show);
                tv_prize_show=itemView.findViewById(R.id.tv_prize_show);
                img_ruppee_pk=itemView.findViewById(R.id.img_ruppee_pk);
                iv_delete=itemView.findViewById(R.id.iv_delete);
                cv_show_labpack=itemView.findViewById(R.id.cv_show_labpack);
                iv_edit=itemView.findViewById(R.id.iv_edit);


            }
        }

    private void deleteLabPackage(String id, ArrayList<ModelPackage> modelPackages, int position) {

        Log.e("id",ID);
        AndroidNetworking.post(API.delete_lab_package)
                .addBodyParameter("id",id)
                .setPriority(Priority.HIGH)
                .setTag("delete_lab_package")
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.e("hdxjfhd", "onRespo " + response.toString());
                        try {

                            String str =  response.getString("result");
                                    if(str.equals("Delete Successfully")){
                                        Toast.makeText(contexts, "Deleted Product", Toast.LENGTH_SHORT).show();
                                        ModelPackages.remove(position);
                                        notifyDataSetChanged();
                                    }else{
                                        Toast.makeText(contexts, "error", Toast.LENGTH_SHORT).show();
                                    }


                           /* if (response.getString("result").equals("Delete Successfully")) {
                                Toast.makeText(contexts, "Deleted Product", Toast.LENGTH_SHORT).show();
                                ModelPackages.remove(position);
                                notifyDataSetChanged();

                            }*/
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


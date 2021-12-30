package com.MCure.MCure.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.MCure.MCure.ModelClass.ModelsEarning;
import com.MCure.MCure.R;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class MyEarningAdapter extends RecyclerView.Adapter<MyEarningAdapter.MyViewHolder> {

    ArrayList<ModelsEarning> modelsEarningArrayList;
    Context context;

    public MyEarningAdapter(ArrayList<ModelsEarning> ModelsEarning, Context contexts) {
        this.modelsEarningArrayList = ModelsEarning;
        this.context = contexts;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.layout_earning, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyEarningAdapter.MyViewHolder holder, int position) {
        ModelsEarning modelsEarning = modelsEarningArrayList.get(position);
        if (!modelsEarning.equals("")) {
            holder.tv_name_earning.setText(modelsEarning.getName());
            holder.tv_age_earning.setText(modelsEarning.getAge());
            holder.tv_datetime_earning.setText(modelsEarning.getDate_time());
            holder.Tv_amount_earning.setText(modelsEarning.getPayment_amount());
            Glide.with(context).load(modelsEarning.getPath() + modelsEarning.getImage())
                    .placeholder(R.drawable.docter).override(50, 50)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(holder.img_docter_earning);


        }

    }

    @Override
    public int getItemCount() {
        return modelsEarningArrayList.size();
    }
    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView tv_name_earning,tv_age_earning,tv_datetime_earning,Tv_amount_earning;
        CircleImageView img_docter_earning;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_name_earning = itemView.findViewById(R.id.tv_name_earning);
            tv_age_earning = itemView.findViewById(R.id.tv_age_earning);
            tv_datetime_earning = itemView.findViewById(R.id.tv_datetime_earning);
            Tv_amount_earning = itemView.findViewById(R.id.Tv_amount_earning);
            img_docter_earning = itemView.findViewById(R.id.img_docter_earning);
        }
    }
}


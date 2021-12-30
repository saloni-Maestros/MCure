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
import com.MCure.MCure.ModelClass.ModelLabCompleteBooking;
import com.MCure.MCure.R;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class LabCompleteBookingAdapter extends RecyclerView.Adapter<LabCompleteBookingAdapter.MyViewHolder> {
    ArrayList<ModelLabCompleteBooking> modelLabCompleteBookingArrayList;
    Context context;

    public LabCompleteBookingAdapter(ArrayList<ModelLabCompleteBooking> modelLabCompleteBookingArrayList, Context context) {
        this.modelLabCompleteBookingArrayList = modelLabCompleteBookingArrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.lab_complete_booking, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LabCompleteBookingAdapter.MyViewHolder holder, int position) {
        ModelLabCompleteBooking  modelLabCompleteBooking  = modelLabCompleteBookingArrayList.get(position);
        if (!modelLabCompleteBooking.equals("")){
            holder.tv_username.setText(modelLabCompleteBooking.getName());
            holder.tv_age.setText(modelLabCompleteBooking.getAge());
            holder.tv_date.setText(modelLabCompleteBooking.getDates());
            holder.Tv_time.setText(modelLabCompleteBooking.getTime());
            holder.tv_status.setText(modelLabCompleteBooking.getStatus());
            Glide.with(context).load(modelLabCompleteBooking.getPath() + modelLabCompleteBooking.getImage())
                    .placeholder(R.drawable.docter).override(50, 50)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(holder.iv_Userpic);
        }

    }

    @Override
    public int getItemCount() {
     return  modelLabCompleteBookingArrayList.size();
    }
    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView tv_username,tv_age,tv_date,Tv_time,tv_status;
        CircleImageView iv_Userpic;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_username = itemView.findViewById(R.id.tv_username);
            tv_age = itemView.findViewById(R.id.tv_age);
            tv_date = itemView.findViewById(R.id.tv_date);
            Tv_time = itemView.findViewById(R.id.Tv_time);
            tv_status = itemView.findViewById(R.id.tv_status);
            iv_Userpic = itemView.findViewById(R.id.iv_Userpic);

        }
    }
}

package com.MCure.MCure.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.MCure.MCure.CaseHistoryChatActivity;
import com.MCure.MCure.ModelClass.ModelCompleteBooking;
import com.MCure.MCure.R;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class CompleteBookingAdapter extends RecyclerView.Adapter<CompleteBookingAdapter.MyViewHolder> {
    ArrayList<ModelCompleteBooking>modelCompleteBookingArrayList;
    Context context;

    public CompleteBookingAdapter(ArrayList<ModelCompleteBooking> modelCompleteBookingArrayList, Context context) {
        this.modelCompleteBookingArrayList = modelCompleteBookingArrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.layout_complete_booking, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CompleteBookingAdapter.MyViewHolder holder, int position) {
        ModelCompleteBooking modelCompleteBooking = modelCompleteBookingArrayList.get(position);
        if (!modelCompleteBooking.equals("")){
            holder.tv_username_com.setText(modelCompleteBooking.getName());
            holder.tv_age_com.setText(modelCompleteBooking.getAge());
            holder.tv_date.setText(modelCompleteBooking.getDates());
            holder.time_com.setText(modelCompleteBooking.getTime());
            holder.tv_status_com.setText(modelCompleteBooking.getStatus());
            Glide.with(context).load(modelCompleteBooking.getPath() + modelCompleteBooking.getImage())
                    .placeholder(R.drawable.docter).override(50, 50)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(holder.img_pic_doct);

        } holder.iv_chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(context, CaseHistoryChatActivity.class);  //adapter to activity
                intent.putExtra("your_extra","your_class_value");
                context.startActivity(intent);

            }
        });

    }

    @Override
    public int getItemCount() {
        return modelCompleteBookingArrayList.size();
    }
          public  class  MyViewHolder extends  RecyclerView.ViewHolder{
          TextView  tv_username_com,tv_age_com,tv_date,time_com,tv_history_com,tv_status_com;
          CircleImageView img_pic_doct;
          ImageView iv_chat;

              public MyViewHolder(@NonNull View itemView) {
                  super(itemView);
                  tv_username_com = itemView.findViewById(R.id.tv_username_com);
                  tv_age_com = itemView.findViewById(R.id.tv_age_com);
                  tv_date = itemView.findViewById(R.id.tv_date);
                  time_com = itemView.findViewById(R.id.time_com);
                  tv_history_com = itemView.findViewById(R.id.tv_history_com);
                  tv_status_com = itemView.findViewById(R.id.tv_status_com);
                  img_pic_doct = itemView.findViewById(R.id.img_pic_doct);
                  iv_chat = itemView.findViewById(R.id.iv_chat);



              }
          }

    }


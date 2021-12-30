package com.MCure.MCure.Adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.android.material.card.MaterialCardView;
import com.MCure.MCure.ModelClass.ModelShowBooking;
import com.MCure.MCure.R;
import com.MCure.MCure.ShowHistoryActivity;
import com.MCure.MCure.others.APPCONSTANT;
import com.MCure.MCure.others.SharedHelper;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class ShowBookingAdapter extends RecyclerView.Adapter<ShowBookingAdapter.MyViewHolder> {
    ArrayList<ModelShowBooking>modelShowBookingArrayList;
    Context context;

    public ShowBookingAdapter(ArrayList<ModelShowBooking> modelShowBookings, Context context) {
        modelShowBookingArrayList = modelShowBookings;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.layout_show_booking, parent, false);
        return new MyViewHolder(view);
    }



    @Override
    public void onBindViewHolder(@NonNull ShowBookingAdapter.MyViewHolder holder, int position) {
        ModelShowBooking modelShowBooking =modelShowBookingArrayList.get(position);
        if (!modelShowBooking.equals("")){
         holder.cardview.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              SharedHelper.putkey(context, APPCONSTANT.ID,modelShowBooking.getId());
              Log.e("cjkdljfv", modelShowBooking.getId());
              Intent intent= new Intent(context, ShowHistoryActivity.class);  //adapter to activity
              intent.putExtra("your_extra","your_class_value");
              context.startActivity(intent);

          }
      });
            holder.username1.setText(modelShowBooking.getName());
            holder.tv_age1.setText(modelShowBooking.getAge());
            holder.tv_date.setText(modelShowBooking.getDates());
            holder.tv_time.setText(modelShowBooking.getTime());
            holder.tv_status1.setText(modelShowBooking.getStatus());
            Glide.with(context).load(modelShowBooking.getPath() + modelShowBooking.getImage())
                    .placeholder(R.drawable.docter).override(50, 50)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(holder.img_docter);
        }
   /*   holder.Tv_history1.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              Intent intent= new Intent(context, ShowHistoryActivity.class);  //adapter to activity
              intent.putExtra("your_extra","your_class_value");
              context.startActivity(intent);
          }
      });*/

    }

    @Override
    public int getItemCount() {

        return modelShowBookingArrayList.size();
    }
    public class MyViewHolder extends RecyclerView.ViewHolder{
             TextView username1,tv_age1,tv_date,tv_history_com,tv_status1,tv_complete_com,tv_time,Tv_history1;
      CircleImageView img_docter;
      MaterialCardView cardview;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            username1 = itemView.findViewById(R.id.username1);
            tv_age1 = itemView.findViewById(R.id.tv_age1);
            tv_date = itemView.findViewById(R.id.tv_date);
            tv_history_com = itemView.findViewById(R.id.tv_history_com);
            tv_status1 = itemView.findViewById(R.id.tv_status1);
            tv_complete_com = itemView.findViewById(R.id.tv_complete_com);
            tv_time = itemView.findViewById(R.id.tv_time);
            img_docter = itemView.findViewById(R.id.img_docter);
            Tv_history1 = itemView.findViewById(R.id.Tv_history1);
            cardview = itemView.findViewById(R.id.cardview);


        }
    }

    }


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
import com.MCure.MCure.ModelClass.ModelLabShowBooking;
import com.MCure.MCure.R;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class LabShowBookingAdapter extends RecyclerView.Adapter<LabShowBookingAdapter.MyViewHolder> {
    ArrayList<ModelLabShowBooking>modelLabShowBookingArrayList;
    Context context;

    public LabShowBookingAdapter(ArrayList<ModelLabShowBooking> modelLabShowBookingArrayList, Context context) {
        this.modelLabShowBookingArrayList = modelLabShowBookingArrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.lab_show_booking, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LabShowBookingAdapter.MyViewHolder holder, int position) {
        ModelLabShowBooking modelLabShowBooking =  modelLabShowBookingArrayList.get(position);
        if (!modelLabShowBooking.equals("")){
            holder.Tv_username.setText(modelLabShowBooking.getName());
            holder.tv_age.setText(modelLabShowBooking.getAge());
            holder.tv_time.setText(modelLabShowBooking.getTime());
            holder.tv_status.setText(modelLabShowBooking.getStatus());
            Glide.with(context).load(modelLabShowBooking.getPath() + modelLabShowBooking.getImage())
                    .placeholder(R.drawable.docter).override(50, 50)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(holder.img_docter);
        }

    }

    @Override
    public int getItemCount() {
       return modelLabShowBookingArrayList.size();
    }
    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView Tv_username,tv_age,tv_time,Tv_history,tv_status,tv_pending;
        CircleImageView img_docter;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            Tv_username = itemView.findViewById(R.id.Tv_username);
            tv_age = itemView.findViewById(R.id.tv_age);
            tv_time= itemView.findViewById(R.id.tv_time);
            Tv_history = itemView.findViewById(R.id.Tv_history);
            tv_status = itemView.findViewById(R.id.tv_status);
            tv_pending = itemView.findViewById(R.id.tv_pending);
            img_docter = itemView.findViewById(R.id.img_docter);







        }
    }
}

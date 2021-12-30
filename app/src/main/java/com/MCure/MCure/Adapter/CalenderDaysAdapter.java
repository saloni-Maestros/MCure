package com.MCure.MCure.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;
import com.MCure.MCure.ModelClass.ModelCalenderDays;
import com.MCure.MCure.R;
import com.MCure.MCure.others.APPCONSTANT;
import com.MCure.MCure.others.SharedHelper;

import java.util.ArrayList;

public class CalenderDaysAdapter extends RecyclerView.Adapter<CalenderDaysAdapter.MyViewHolder> {
    private final int selectedItem = -1;

    ArrayList<ModelCalenderDays> modelCalenderDaysArrayList;
    Context context;

    ArrayList<String> arrayList = new ArrayList<String>();


    public CalenderDaysAdapter(ArrayList<ModelCalenderDays> modelCalenderDaysArrayList, Context context) {
        this.modelCalenderDaysArrayList = modelCalenderDaysArrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.calender_day_list, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CalenderDaysAdapter.MyViewHolder holder, int position) {
        ModelCalenderDays modelCalenderDays = modelCalenderDaysArrayList.get(position);
        if (!modelCalenderDays.equals("")) {


            if (modelCalenderDays.getDay_status().equals("0")){
                holder.BtnSunday.setBackgroundColor(context.getResources()           //changed button color on adapter
                        .getColor(R.color.white));
            }else{
                holder.BtnSunday.setBackgroundColor(context.getResources()           //changed button color on adapter
                        .getColor(R.color.blue));
            }
            holder.BtnSunday.setText(modelCalenderDays.getDay());
            ModelCalenderDays item = modelCalenderDaysArrayList.get(position);
            holder.BtnSunday.setOnClickListener(new View.OnClickListener() {
                int check = 1;
                @Override
                public void onClick(View v) {
                    SharedHelper.putkey(context, APPCONSTANT.ID, modelCalenderDays.getId());
                    // boolean clicked = true;

                    if (check == 1) {
                        Log.e("fdghdfjkh", "1");
                        arrayList.add(holder.BtnSunday.getText().toString().trim());
                        String str = String.valueOf(arrayList);
                        String loginToken = str;
                        loginToken = loginToken.substring(1,loginToken.length()-1);


                        String result = loginToken.replaceAll(" (?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)", "");
                        SharedHelper.putkey(context, APPCONSTANT.DAYS,result);
                        Log.e("fdgjhfdj", result);
                        holder.BtnSunday.setBackgroundColor(context.getResources()           //changed button color on adapter
                                .getColor(R.color.blue));
                        check = 0;
                    } else {
                        Log.e("fdghdfjkh", "2");
                        boolean clicked = true;
                        arrayList.remove(holder.BtnSunday.getText().toString().trim());
                        String str = String.valueOf(arrayList);
                        String loginToken = str;
                        loginToken = loginToken.substring(1, loginToken.length() - 1);
                        SharedHelper.putkey(context, APPCONSTANT.DAYS, loginToken);
                        Log.e("fdgjhfdj", loginToken + "");
                        holder.BtnSunday.setBackgroundColor(context.getResources()
                                .getColor(R.color.white));
                        if (!clicked) {
                            Log.e("fdghdfjkh", "1");
                            clicked = true;
                            holder.BtnSunday.setBackgroundColor(context.getResources()           //changed button color on adapter
                                    .getColor(R.color.blue));
                        } else {
                            Log.e("fdghdfjkh", "2");
                            clicked = false;
                            holder.BtnSunday.setBackgroundColor(context.getResources()
                                    .getColor(R.color.white));
                        }
                        check = 1;
                    }

                   /* if(!clicked){
                        Log.e("fdghdfjkh","1");
                        clicked = true;
                        holder.BtnSunday.setBackgroundColor(context.getResources()           //changed button color on adapter
                                .getColor(R.color.blue));


                    }else{
                        Log.e("fdghdfjkh","2");

                        clicked = false;
                        holder.BtnSunday.setBackgroundColor(context.getResources()
                                .getColor(R.color.white));

                    }*/


                }
            });
        }
        Log.e("rtgsrdrsdtgrs", modelCalenderDays.getId());
    }

    @Override
    public int getItemCount() {
        return modelCalenderDaysArrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        MaterialButton BtnSunday;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            BtnSunday = itemView.findViewById(R.id.BtnSunday);
        }
    }
}

package com.MCure.MCure.Adapter;

import android.content.Context;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;
import com.MCure.MCure.ModelClass.ModelCalenderTimeSlot;
import com.MCure.MCure.R;
import com.MCure.MCure.others.APPCONSTANT;
import com.MCure.MCure.others.SharedHelper;

import java.util.ArrayList;

public class CalenderTimeAdapter extends RecyclerView.Adapter<CalenderTimeAdapter.MyViewHolder> {

    ArrayList<ModelCalenderTimeSlot> modelCalenderTimeSlotArrayList;
    Context context;
    SparseBooleanArray mStateButtons = new SparseBooleanArray();
    public boolean clicked = false;


    ArrayList<String> arrayList = new ArrayList<String>();
    int index = -1;
    int check = 1;

    public CalenderTimeAdapter(ArrayList<ModelCalenderTimeSlot> modelCalenderTimeSlotArrayList, Context context) {
        this.modelCalenderTimeSlotArrayList = modelCalenderTimeSlotArrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.calender_list, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CalenderTimeAdapter.MyViewHolder holder, int position) {
        ModelCalenderTimeSlot modelCalenderTimeSlot = modelCalenderTimeSlotArrayList.get(position);
        if (!modelCalenderTimeSlot.equals("")) {


            if (modelCalenderTimeSlot.getDr_status().equals("0")){
                holder.BtnTimeSlot1.setBackgroundColor(context.getResources()           //changed button color on adapter
                        .getColor(R.color.white));
            }else{
                holder.BtnTimeSlot1.setBackgroundColor(context.getResources()           //changed button color on adapter
                        .getColor(R.color.blue));
            }

            holder.BtnTimeSlot1.setText(modelCalenderTimeSlot.getTimes());
            holder.BtnTimeSlot2.setText(modelCalenderTimeSlot.getTimes());
            ModelCalenderTimeSlot item = modelCalenderTimeSlotArrayList.get(position);


            // final ItemDataModel model = arrayList.get(position);


            holder.BtnTimeSlot2.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    SharedHelper.putkey(context, APPCONSTANT.ID, modelCalenderTimeSlot.getId());
                    // boolean clicked = true;


                    notifyDataSetChanged();

                    modelCalenderTimeSlot.clicked = false;
                   // arrayList.remove(holder.BtnTimeSlot1.getText().toString().trim());
                    arrayList.remove(modelCalenderTimeSlot.getId());



                    Log.e("fgds",modelCalenderTimeSlot.clicked+"");


                 /* holder.BtnTimeSlot1.setVisibility(View.GONE);
                  holder.BtnTimeSlot2.setVisibility(View.VISIBLE);*/


                }
            });
            holder.BtnTimeSlot1.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    SharedHelper.putkey(context, APPCONSTANT.ID, modelCalenderTimeSlot.getId());
                    // boolean clicked = true;


                    notifyDataSetChanged();

                    modelCalenderTimeSlot.clicked = true;
                   // arrayList.add(holder.BtnTimeSlot1.getText().toString().trim());
                    arrayList.add(modelCalenderTimeSlot.getId());
                    String str = String.valueOf(arrayList);

                    String loginToken = str;
                    loginToken = loginToken.substring(1, loginToken.length() - 1);
                    String result = loginToken.replaceAll(" (?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)", "");


                    SharedHelper.putkey(context, APPCONSTANT.TIMES, result);
                    Log.e("fdgjhfdj", result + "");



                    Log.e("fgds",modelCalenderTimeSlot.clicked+"");


                 /* holder.BtnTimeSlot1.setVisibility(View.GONE);
                  holder.BtnTimeSlot2.setVisibility(View.VISIBLE);*/


                }
            });

            if (modelCalenderTimeSlot.clicked) {
                int check = 1;

                holder.BtnTimeSlot2.setVisibility(View.VISIBLE);
                holder.BtnTimeSlot1.setVisibility(View.GONE);
                Log.e("ghfdsjh", "1");

                //Set the state of the button when is clicked
            } else {
                //Set initial state
                holder.BtnTimeSlot2.setVisibility(View.GONE);
                holder.BtnTimeSlot1.setVisibility(View.VISIBLE);

                Log.e("ghfdsjh", "2");

            }



          /*  holder.BtnTimeSlot2.setOnClickListener(new View.OnClickListener() {

                boolean clicked = Boolean.parseBoolean("");
                @Override
                public void onClick(View v) {
                    SharedHelper.putkey(context, APPCONSTANT.ID, modelCalenderTimeSlot.getId());
                    // boolean clicked = true;


                  holder.BtnTimeSlot2.setVisibility(View.GONE);
                  holder.BtnTimeSlot1.setVisibility(View.VISIBLE);

                   // notifyDataSetChanged();




                }
            });
*/





/*
            holder.BtnTimeSlot1.setOnClickListener(new View.OnClickListener() {
                int check = 1;
                @Override
                public void onClick(View v) {
                    SharedHelper.putkey(context, APPCONSTANT.ID, modelCalenderTimeSlot.getId());
                    // boolean clicked = true;
                   if (check == 1) {
                        Log.e("fdghdfjkh", "1");


                        holder.BtnTimeSlot1.setBackgroundColor(context.getResources()           //changed button color on adapter
                                .getColor(R.color.blue));
                        arrayList.add(holder.BtnTimeSlot1.getText().toString().trim());
                        String str = String.valueOf(arrayList);
                        String loginToken = str;
                        loginToken = loginToken.substring(1, loginToken.length() - 1);
                        SharedHelper.putkey(context, APPCONSTANT.TIMES, loginToken);
                        Log.e("fdgjhfdj", loginToken + "");

                        check = 0;
                    } else {
                        Log.e("fdghdfjkh", "2");
                        boolean clicked = true;
                        arrayList.remove(holder.BtnTimeSlot1.getText().toString().trim());
                        String str = String.valueOf(arrayList);
                        String loginToken = str;
                        loginToken = loginToken.substring(1, loginToken.length() - 1);
                        SharedHelper.putkey(context, APPCONSTANT.TIMES, loginToken);
                        Log.e("fdgjhfdj", loginToken + "");
                        holder.BtnTimeSlot1.setBackgroundColor(context.getResources()
                                .getColor(R.color.white));
                        if (!clicked) {
                            Log.e("fdghdfjkh", "3");
                            clicked = true;
                            holder.BtnTimeSlot1.setBackgroundColor(context.getResources()           //changed button color on adapter
                                    .getColor(R.color.blue));
                        } else {
                            Log.e("fdghdfjkh", "4");
                            clicked = false;
                            holder.BtnTimeSlot1.setBackgroundColor(context.getResources()
                                    .getColor(R.color.white));
                        }
                        check = 1;
                    }


                }
            });
*/


          /*  if (index==position){

                holder.BtnTimeSlot1.setBackgroundColor(context.getResources()           //changed button color on adapter
                        .getColor(R.color.blue));
            }else{

                holder.BtnTimeSlot1.setBackgroundColor(context.getResources()           //changed button color on adapter
                        .getColor(R.color.white));
            }
*/


        }
        Log.e("sdkckjcm", modelCalenderTimeSlot.getId());

    }

    @Override
    public int getItemCount() {

        return modelCalenderTimeSlotArrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        MaterialButton BtnTimeSlot1, BtnTimeSlot2;
        LinearLayout LinearAdapetr;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            BtnTimeSlot1 = itemView.findViewById(R.id.BtnTimeSlot1);
            LinearAdapetr = itemView.findViewById(R.id.LinearAdapetr);
            BtnTimeSlot2 = itemView.findViewById(R.id.BtnTimeSlot2);
        }
    }
}

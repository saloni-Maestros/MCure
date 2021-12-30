package com.MCure.MCure.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.MCure.MCure.ModelClass.ModelBankTransation;
import com.MCure.MCure.R;

import java.util.ArrayList;

public class BankTransationAdapter  extends RecyclerView.Adapter<BankTransationAdapter.MyViewHolder> {
    ArrayList<ModelBankTransation> modelBankTransationArrayList;
    Context context;

    public BankTransationAdapter(ArrayList<ModelBankTransation> ModelsBankTransation, Context contexts) {
        this.modelBankTransationArrayList = ModelsBankTransation;
        this.context = contexts;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.layout_banktransction, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BankTransationAdapter.MyViewHolder holder, int position) {
        ModelBankTransation  modelBankTransation = modelBankTransationArrayList.get(position);
        if (!modelBankTransation.equals("")){
            holder.tv_accountno.setText(modelBankTransation.getAccount_number());
            holder.tv_paymentaccount.setText(modelBankTransation.getPayment_amount());
        }

    }

    @Override
    public int getItemCount() {
        return modelBankTransationArrayList.size();
    }
    public class  MyViewHolder extends RecyclerView.ViewHolder{
        ImageView iv_paymentaccount;
        TextView tv_account,tv_accountno,tv_paymentaccount;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_paymentaccount = itemView.findViewById(R.id.tv_paymentaccount);
            tv_accountno = itemView.findViewById(R.id.tv_accountno);
            tv_account = itemView.findViewById(R.id.tv_account);


        }
    }
}

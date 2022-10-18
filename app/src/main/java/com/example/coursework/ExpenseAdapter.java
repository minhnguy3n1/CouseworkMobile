package com.example.coursework;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ExpenseAdapter extends RecyclerView.Adapter<ExpenseAdapter.MyViewHolder> {

    private Context context;
    private Activity activity;
    private ArrayList expense_id, expense_type, expense_amount, expense_date;


    ExpenseAdapter(Activity activity, Context context, ArrayList expense_id, ArrayList expense_type, ArrayList expense_amount,
                   ArrayList expense_date){
        this.activity = activity;
        this.context = context;
        this.expense_id = expense_id;
        this.expense_type = expense_type;
        this.expense_amount = expense_amount;
        this.expense_date = expense_date;

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.expense_row, parent, false);
        return new ExpenseAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.expense_id_txt.setText(String.valueOf(expense_id.get(position)));
        holder.expense_type_txt.setText(String.valueOf(expense_type.get(position)));
        holder.expense_amount_txt.setText(String.valueOf(expense_amount.get(position)));
        holder.expense_date_txt.setText(String.valueOf(expense_date.get(position)));

    }

    @Override
    public int getItemCount() {
        return expense_id.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView expense_id_txt, expense_type_txt, expense_amount_txt, expense_date_txt;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            expense_id_txt = itemView.findViewById(R.id.expense_id_txt);
            expense_type_txt = itemView.findViewById(R.id.expense_type_txt);
            expense_amount_txt = itemView.findViewById(R.id.expense_amount_txt);
            expense_date_txt = itemView.findViewById(R.id.expense_date_txt);
        }
    }
}

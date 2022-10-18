package com.example.coursework;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MyCustomAdapter extends RecyclerView.Adapter<MyCustomAdapter.MyViewHolder>
{

    private Context context;
    private ArrayList<TripInfo> tripInfos;
    public MyCustomAdapter(ArrayList<TripInfo> tripInfos, Context context){
        this.tripInfos = tripInfos;
        this.context = context;
    }

    public void filterList(ArrayList<TripInfo> filterList){

        tripInfos = filterList;
        notifyDataSetChanged();
    }



    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.search_row, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        TripInfo tripInfo = tripInfos.get(position);
        holder.trip_id_txt.setText(String.valueOf(tripInfo.getId()));
        holder.trip_name_txt.setText(String.valueOf(tripInfo.getName()));
        holder.trip_destination_txt.setText(tripInfo.getDestination());
        holder.trip_date_txt.setText(tripInfo.getDate());
        holder.trip_assessment_txt.setText(tripInfo.getAssessment());

    }



    @Override
    public int getItemCount() {
        return tripInfos.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView trip_id_txt, trip_name_txt, trip_destination_txt, trip_date_txt, trip_assessment_txt;


        MyViewHolder(@NonNull View itemView){
            super(itemView);
            trip_id_txt = itemView.findViewById(R.id.trip_id_txt);
            trip_name_txt = itemView.findViewById(R.id.trip_name_txt);
            trip_destination_txt = itemView.findViewById(R.id.trip_destination_txt);
            trip_date_txt = itemView.findViewById(R.id.trip_date_txt);
            trip_assessment_txt = itemView.findViewById(R.id.trip_assessment_txt);



        }
    }
}

package com.example.eshop_v2;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ViewTransactionsRecyclerAdapter extends RecyclerView.Adapter<ViewTransactionsRecyclerAdapter.MyViewHolder> {


    private List<supplies> list;
    Context context;

    public ViewTransactionsRecyclerAdapter(List<supplies> list, Context context){
        this.list = list;
        this.context = context;
    }

    public void updateList(List<supplies> newList) {
        list = newList;

        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public ViewTransactionsRecyclerAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.supplies_view_layout, parent,false);

        return new ViewTransactionsRecyclerAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewTransactionsRecyclerAdapter.MyViewHolder holder, int position) {


        supplies Supplies =  list.get(position);

        int pid = Supplies.getProduct_id();
        int sid = Supplies.getSupplier_id();
        int qty = Supplies.getQnt();
        String date = Supplies.getDate();

        holder.pid.setText(String.valueOf(pid));
        holder.date.setText(date);
        holder.sid.setText(String.valueOf(sid));
        holder.qty.setText(String.valueOf(qty));

    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView pid,sid,date,qty;



        public MyViewHolder(View itemView) {
            super(itemView);


            pid = itemView.findViewById(R.id.pro_id);
            sid = itemView.findViewById(R.id.supp_id);
            date = itemView.findViewById(R.id.transaction_date);
            qty = itemView.findViewById(R.id.qty);



        }
    }
}

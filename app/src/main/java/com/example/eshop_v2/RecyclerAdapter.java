package com.example.eshop_v2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.MyViewHolder> {
    private List<products> list;
    public RecyclerAdapter(List<products> list){
        this.list = list;
    }

    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.text_view_layout, parent,false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        products Products =  list.get(position);

        String name = Products.getName();
        int Qty = Products.getQuantity();
        double price = Products.getPrice();




        holder.TextViewName.setText(name);
        holder.TextViewQty.setText(String.valueOf(Qty));
        holder.TextViewPrice.setText(String.valueOf(price));


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView TextViewName;
        TextView TextViewQty;
        TextView TextViewPrice;

        public MyViewHolder(View itemView) {
            super(itemView);
            TextViewName = itemView.findViewById(R.id.txtview_name);
            TextViewQty = itemView.findViewById(R.id.txtview_quantity);
            TextViewPrice = itemView.findViewById(R.id.txtview_price);

        }
    }
}


package com.example.eshop_v2;

import android.content.Context;
import android.content.Intent;
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
    Context context;
    public RecyclerAdapter(List<products> list,Context context){
        this.list = list;
        this.context = context;
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
        String desc = Products.getDescription();


        holder.TextViewDesc.setText(desc);
        holder.TextViewName.setText(name);
        holder.TextViewQty.setText(String.valueOf(Qty));
        holder.TextViewPrice.setText(String.valueOf(price));


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, SingleProductActivity.class);
                intent.putExtra("single_product_name", Products.getName());
                intent.putExtra("txtview_price", Products.getPrice());
                intent.putExtra("txtview_quantity", Products.getQuantity());
                intent.putExtra("txtview_description", Products.getDescription());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);

            }
        });


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView TextViewName, TextViewQty, TextViewPrice, TextViewDesc;
        ImageView img;




        public MyViewHolder(View itemView) {
            super(itemView);
            TextViewName = itemView.findViewById(R.id.txtview_name);
            TextViewQty = itemView.findViewById(R.id.txtview_quantity);
            TextViewPrice = itemView.findViewById(R.id.txtview_price);
            TextViewDesc = itemView.findViewById(R.id.txtview_description);
            img = itemView.findViewById(R.id.picture);





        }
    }
}


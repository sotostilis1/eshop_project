package com.example.eshop_v2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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
        String desc = Products.getDescription();


        int number = holder.getNum();

        holder.TextViewDesc.setText(desc);
        holder.TextViewName.setText(name);
        holder.TextViewQty.setText(String.valueOf(Qty));
        holder.TextViewPrice.setText(String.valueOf(price));
        holder.Txtview.setText(String.valueOf(number));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView TextViewName, TextViewQty, TextViewPrice, TextViewDesc;


        private int number = 0;

        Button btn1;
        Button btn2;
        TextView Txtview;

        public MyViewHolder(View itemView) {
            super(itemView);
            TextViewName = itemView.findViewById(R.id.txtview_name);
            TextViewQty = itemView.findViewById(R.id.txtview_quantity);
            TextViewPrice = itemView.findViewById(R.id.txtview_price);
            TextViewDesc = itemView.findViewById(R.id.txtview_description);
            btn1 = itemView.findViewById(R.id.minus_button);
            btn2 = itemView.findViewById(R.id.add_button);

            Txtview = itemView.findViewById(R.id.wanted_quantity);
            btn1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Handle button1 click event
                    int position = getAdapterPosition(); // Get the item position
                    String text = Txtview.getText().toString();
                    try {
                         number = Integer.parseInt(text);
                        number--;
                    } catch (NumberFormatException e) {
                        // Handle the case where the text is not a valid integer
                    }

                }
            });

            btn2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Handle button1 click event
                    int position = getAdapterPosition(); // Get the item position
                    String text = Txtview.getText().toString();
                    try {
                        number = Integer.parseInt(text);
                        number++;
                    } catch (NumberFormatException e) {
                        // Handle the case where the text is not a valid integer
                    }

                }
            });

        }

        public int getNum() {
            return number;
        }
    }
}


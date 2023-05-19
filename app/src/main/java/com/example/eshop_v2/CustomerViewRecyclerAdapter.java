package com.example.eshop_v2;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class CustomerViewRecyclerAdapter extends RecyclerView.Adapter<CustomerViewRecyclerAdapter.MyViewHolder>{

    public void setData(List<Customers> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    private List<Customers> list;
    Context context;

    public CustomerViewRecyclerAdapter(List<Customers> list, Context context){
        this.list = list;
        this.context = context;
    }

    public void updateList(List<Customers> newList) {
        list = newList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.customer_view_layout, parent,false);

        return new MyViewHolder(itemView);

    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        Customers customers =  list.get(position);

        holder.id.setText(String.valueOf(customers.getCid()));
        holder.name.setText(customers.getCname());
        holder.surname.setText(customers.getCsurname());
        holder.address.setText(customers.getCaddress());

    }

    @Override
    public int getItemCount() {
         return list.size();
    }


    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView id,name,surname,address;



        public MyViewHolder(View itemView) {
            super(itemView);


            id = itemView.findViewById(R.id.customer_id);
            name = itemView.findViewById(R.id.customer_name);
            surname = itemView.findViewById(R.id.customer_surname);
            address = itemView.findViewById(R.id.customer_address);


        }
    }
}

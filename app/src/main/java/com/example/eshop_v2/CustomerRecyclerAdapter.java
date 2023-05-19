package com.example.eshop_v2;


import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.subjects.PublishSubject;


public class CustomerRecyclerAdapter extends RecyclerView.Adapter<CustomerRecyclerAdapter.MyViewHolder> {

    private List<Customers> list;
    Context context;

    private PublishSubject<Integer> variableSubject = PublishSubject.create();

    private int selectedItemIndex = RecyclerView.NO_POSITION;



    private int darkerGray = Color.parseColor("#66808080");

    public CustomerRecyclerAdapter(List<Customers> list, Context context){
        this.list = list;
        this.context = context;
    }
    public Observable<Integer> getVariableObservable() {
        return variableSubject;
    }

    public void setData(List<Customers> list) {
        this.list = list;
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

        if (position == selectedItemIndex) {

            holder.itemView.setBackgroundColor(darkerGray);
        } else {

            holder.itemView.setBackgroundColor(Color.WHITE);
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int previousSelectedItemIndex = selectedItemIndex;
                selectedItemIndex = holder.getAdapterPosition();
                int variable = customers.getCid();; // Replace with your variable value
                variableSubject.onNext(variable);



                notifyItemChanged(previousSelectedItemIndex);
                notifyItemChanged(selectedItemIndex);
            }
        });








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

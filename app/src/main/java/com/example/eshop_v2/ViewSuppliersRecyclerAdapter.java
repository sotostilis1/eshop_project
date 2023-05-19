package com.example.eshop_v2;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;



import java.util.List;

public class ViewSuppliersRecyclerAdapter extends RecyclerView.Adapter<ViewSuppliersRecyclerAdapter.MyViewHolder> {


    private List<suppliers> list;
    Context context;

    public ViewSuppliersRecyclerAdapter(List<suppliers> list, Context context){
        this.list = list;
        this.context = context;
    }


    @NonNull
    @Override
    public ViewSuppliersRecyclerAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.supplier_view_layout, parent,false);

        return new ViewSuppliersRecyclerAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewSuppliersRecyclerAdapter.MyViewHolder holder, int position) {


        suppliers Suppliers =  list.get(position);

        int id = Suppliers.getId();
        String name = Suppliers.getName();

        holder.id.setText(String.valueOf(id));
        holder.name.setText(name);

    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView id,name;



        public MyViewHolder(View itemView) {
            super(itemView);


            id = itemView.findViewById(R.id.supplier_id);
            name = itemView.findViewById(R.id.supplier_name);



        }
    }
}

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

public class CartRecyclerAdapter extends RecyclerView.Adapter<CartRecyclerAdapter.MyViewHolder> {

    private List<cart> list;
    Context context;
    public CartRecyclerAdapter(List<cart> list,Context context){
        this.list = list;
        this.context = context;
    }





    @NonNull
    @Override
    public CartRecyclerAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_view_layout, parent,false);

        return new CartRecyclerAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull CartRecyclerAdapter.MyViewHolder holder, int position) {
        cart cart =  list.get(position);

        int id = cart.getId();
        String name = cart.getName();
        int Qty = cart.getQty();
        double price = cart.getPrice();
        double finalprice = cart.getFinalPrice();


        holder.id.setText(id);
        holder.finalprice.setText(String.valueOf(finalprice));
        holder.name.setText(name);
        holder.qty.setText(String.valueOf(Qty));
        holder.price.setText(String.valueOf(price));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, CartFragment.class);
                intent.putExtra("cartview_name", cart.getName());
                intent.putExtra("cartview_price", cart.getPrice());
                intent.putExtra("cartview_quantity", cart.getQty());
                intent.putExtra("cartview_id", cart.getId());
                intent.putExtra("cartview_final_price", cart.getFinalPrice());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);

            }
        });

    }



    @Override
    public int getItemCount() {
        return 0;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView name, qty, price, finalprice, id;


        public MyViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.cartview_name);
            qty = itemView.findViewById(R.id.cartview_quantity);
            price = itemView.findViewById(R.id.cartview_price);
            finalprice = itemView.findViewById(R.id.cartview_final_price);
            id = itemView.findViewById(R.id.cartview_id);







        }
    }
}

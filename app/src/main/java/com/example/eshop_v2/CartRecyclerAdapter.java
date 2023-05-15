package com.example.eshop_v2;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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
    public void deleteItem(int position) {
        cart deletedCart = list.get(position);
        // Remove the item from your data source
        list.remove(position);

        // Notify the adapter that an item has been removed at the specified position
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, getItemCount());

        // Retrieve the product associated with the deleted cart item
        int productId = deletedCart.getId();
        products product = MainActivity.productsDatabase.productsDAOtemp().getById(productId);

        // Increment the quantity of the product by the quantity of the deleted cart item
        int newQuantity = product.getQuantity() + deletedCart.getQty();
        product.setQuantity(newQuantity);

        // Update the product in the database
        MainActivity.productsDatabase.productsDAOtemp().updateProducts(product);

        Toast.makeText(context, "Item removed from cart", Toast.LENGTH_SHORT).show();
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


        holder.id.setText(String.valueOf(id));
        holder.finalprice.setText(String.valueOf(finalprice));
        holder.name.setText(name);
        holder.qty.setText(String.valueOf(Qty));
        holder.price.setText(String.valueOf(price));


        holder.cancel_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cart Cart = new cart();
                Cart.setId(id);
                MainActivity.productsDatabase.productsDAOtemp().deleteCart(Cart);

                int clickedPosition = holder.getAdapterPosition();
                
                deleteItem(clickedPosition);


            }
        });

    }



    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView name, qty, price, finalprice, id;
        Button cancel_btn;


        public MyViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.cartview_name);
            qty = itemView.findViewById(R.id.cartview_quantity);
            price = itemView.findViewById(R.id.cartview_price);
            finalprice = itemView.findViewById(R.id.cartview_final_price);
            id = itemView.findViewById(R.id.cartview_id);
            cancel_btn = itemView.findViewById(R.id.cancel);

        }
    }
}

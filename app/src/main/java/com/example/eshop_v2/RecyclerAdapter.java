package com.example.eshop_v2;

import static android.provider.UserDictionary.Words._ID;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
    private Cursor mCursor;
    public RecyclerAdapter(List<products> list , Cursor cursor){
        this.list = list;
        this.mCursor = cursor;
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

        // Indices for the _id, description, and priority columns
        int idIndex = mCursor.getColumnIndex(_ID);
        int fragranceName = mCursor.getColumnIndex(DbHelper.COLUMN_NAME);



        mCursor.moveToPosition(position); // get to the right location in the cursor

        // Determine the values of the wanted data
        final int id = mCursor.getInt(idIndex);
        byte[] image = mCursor.getBlob(fragranceName);

        //Set values
        holder.itemView.setTag(id);

        Bitmap bmp = BitmapFactory.decodeByteArray(image, 0, image.length);
        holder.image.setImageBitmap(bmp);

    }

    public Cursor swapCursor(Cursor c) {
        // check if this cursor is the same as the previous cursor (mCursor)
        if (mCursor == c) {
            return null; // bc nothing has changed
        }
        Cursor temp = mCursor;
        this.mCursor = c; // new cursor value assigned

        //check if this is a valid cursor, then update the cursor
        if (c != null) {
            this.notifyDataSetChanged();
        }
        return temp;
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView TextViewName;
        TextView TextViewQty;
        TextView TextViewPrice;
        ImageView image;

        public MyViewHolder(View itemView) {
            super(itemView);
            TextViewName = itemView.findViewById(R.id.txtview_name);
            TextViewQty = itemView.findViewById(R.id.txtview_quantity);
            TextViewPrice = itemView.findViewById(R.id.txtview_price);
            image = (ImageView) itemView.findViewById(R.id.picture);

        }
    }
}


package com.example.eshop_v2;

import static android.provider.UserDictionary.Words._ID;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.MyViewHolder> {
    private static final String TAG = "RecyclerAdapter" ;
    private List<products> list;
    Context context;

    private Cursor mCursor;
    public RecyclerAdapter(List<products> list,Cursor cursor ,Context context){
        this.list = list;
        this.mCursor = cursor;
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

        int id = Products.getId();
        String name = Products.getName();
        int Qty = Products.getQuantity();
        double price = Products.getPrice();
        String desc = Products.getDescription();



        holder.TextViewDesc.setText(desc);
        holder.TextViewName.setText(name);
        holder.TextViewQty.setText(String.valueOf(Qty));
        holder.TextViewPrice.setText(String.valueOf(price));

        // Indices for the _id, description, and priority columns
        int idIndex = mCursor.getColumnIndex(_ID);
        int fragranceName = mCursor.getColumnIndex(DbHelper.COLUMN_IMAGE);



        mCursor.moveToPosition(position); // get to the right location in the cursor

        // Determine the values of the wanted data
        final int img_Id = mCursor.getInt(idIndex);
        byte[] image = mCursor.getBlob(fragranceName);

        //Set values
        holder.itemView.setTag(img_Id);
        Bitmap bmp = BitmapFactory.decodeByteArray(image, 0, image.length);
        holder.img.setScaleType(ImageView.ScaleType.CENTER_CROP);
        holder.img.setImageBitmap(bmp);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, SingleProductActivity.class);
                intent.putExtra("single_product_name", Products.getName());
                intent.putExtra("txtview_price", Products.getPrice());
                intent.putExtra("txtview_quantity", Products.getQuantity());
                intent.putExtra("txtview_description", Products.getDescription());
                intent.putExtra("product_id", Products.getId());
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
        TextView TextViewName, TextViewQty, TextViewPrice, TextViewDesc , TextViewId;
        ImageView img;

        public MyViewHolder(View itemView) {
            super(itemView);
            TextViewName = itemView.findViewById(R.id.txtview_name);
            TextViewQty = itemView.findViewById(R.id.txtview_quantity);
            TextViewPrice = itemView.findViewById(R.id.txtview_price);
            TextViewDesc = itemView.findViewById(R.id.txtview_description);
            img = itemView.findViewById(R.id.picture);
            TextViewId = itemView.findViewById(R.id.single_product_id);
        }
    }
}


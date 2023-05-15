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

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ImagesAdapter extends RecyclerView.Adapter<ImagesAdapter.ImagesViewHolder> {

    // Class variables for the Cursor that holds task data and the Context
    private Cursor mCursor;
    private Context mContext;

    private DbHelper dbHelper;



    /**
     * Constructor for the CustomCursorAdapter that initializes the Context.
     *
     * @param mContext the current Context
     */
    public ImagesAdapter(Context mContext, DbHelper dbHelper) {
        this.mContext = mContext;
        this.dbHelper = dbHelper;
    }



    @Override
    public ImagesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.item_main, parent, false);
        return new ImagesViewHolder(view);
    }


    @Override
    public void onBindViewHolder(ImagesViewHolder holder, int position) {

        if (mCursor == null) {
            return;
        }
        mCursor.moveToPosition(position);

        int idIndex = mCursor.getColumnIndex(_ID);
        int fragranceName = mCursor.getColumnIndex(DbHelper.COLUMN_IMAGE);

        int id = mCursor.getInt(idIndex);
        byte[] image = mCursor.getBlob(fragranceName);

        Bitmap bmp = BitmapFactory.decodeByteArray(image, 0, image.length);
        holder.image.setImageBitmap(Bitmap.createScaledBitmap(bmp, 200, 200, false));

    }


    @Override
    public int getItemCount() {
        if (mCursor == null) {
            return 0;
        }
        return mCursor.getCount();
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

    public class ImagesViewHolder extends RecyclerView.ViewHolder {
        ImageView image;
        public ImagesViewHolder(View itemView) {
            super(itemView);

            image = (ImageView) itemView.findViewById(R.id.picture);
        }

    }
}



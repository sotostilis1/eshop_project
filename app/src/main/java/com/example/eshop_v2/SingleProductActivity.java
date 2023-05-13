package com.example.eshop_v2;

import static android.provider.UserDictionary.Words._ID;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class SingleProductActivity extends AppCompatActivity {

    private DbHelper dbHelper;
    private int position;


    Button btn_minus , btn_add, btn_add_to_cart;
    TextView prod_name, prod_quantity , prod_price, prod_desc, prod_wanted_qty;
    ImageView prod_pic;

    class ViewHolder {
        ImageView img;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_product_activity);
        dbHelper = new DbHelper(this);
        products prods = new products();
        int productId = getIntent().getIntExtra("product_id", 0);
        position = getIntent().getIntExtra("product_id", productId+1);


        prod_name = findViewById(R.id.single_product_name);
        prod_pic = findViewById(R.id.single_img);
        prod_quantity = findViewById(R.id.txtview_quantity);
        prod_price = findViewById(R.id.txtview_price);
        prod_desc = findViewById(R.id.txtview_description);
        prod_wanted_qty = findViewById(R.id.wanted_quantity);
        btn_minus = findViewById(R.id.minus_button);
        btn_add = findViewById(R.id.add_button);
        btn_add_to_cart = findViewById(R.id.add_to_cart);

        prod_name.setText(getIntent().getStringExtra("single_product_name"));
        prod_quantity.setText(String.valueOf(getIntent().getIntExtra("txtview_quantity", 0)));
        prod_price.setText(String.valueOf(getIntent().getDoubleExtra("txtview_price", 0)));
        prod_desc.setText(getIntent().getStringExtra("txtview_description"));
        prod_desc.setText(getIntent().getStringExtra("txtview_description"));

        ViewHolder holder = new ViewHolder();
        holder.img = prod_pic;
        Cursor cursor = dbHelper.getAllData(position);
        if (cursor.moveToFirst()) {
                int fragranceName = cursor.getColumnIndex(DbHelper.COLUMN_NAME);
                byte[] image = cursor.getBlob(fragranceName);
                Bitmap bitmap = BitmapFactory.decodeByteArray(image, 0, image.length);
                holder.img.setImageBitmap(bitmap);
        }


        btn_minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int number = 0;

                String text = prod_wanted_qty.getText().toString();

                number = Integer.parseInt(text);
                number--;
                if  (number>=1){
                    prod_wanted_qty.setText(String.valueOf(number)); }
                else {
                    number++;
                    prod_wanted_qty.setText(String.valueOf(number));
                    Toast.makeText(getApplicationContext(), "quantity should be >=1", Toast.LENGTH_SHORT).show();
                }



            }
        });


        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int number = 0;

                String text = prod_wanted_qty.getText().toString();

                    number = Integer.parseInt(text);
                    number++;
                    if  (number<= Integer.parseInt(prod_quantity.getText().toString())){
                    prod_wanted_qty.setText(String.valueOf(number)); }
                    else {
                        number--;
                        prod_wanted_qty.setText(String.valueOf(number));
                        Toast.makeText(getApplicationContext(), "you can't buy more than we have to offer", Toast.LENGTH_SHORT).show();
                    }


            }
        });


    }
}
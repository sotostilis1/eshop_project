package com.example.eshop_v2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.material.navigation.NavigationView;

public class SingleProductActivity extends AppCompatActivity {

    private static final String TAG = "SingleProductActivity";
    private DbHelper dbHelper;
    private int position;
    private String name;

    private ActionBarDrawerToggle drawerToggle;
    private DrawerLayout drawerLayout;
    Button btn_minus, btn_add, btn_add_to_cart;
    TextView prod_name, prod_quantity, prod_price, prod_desc, prod_wanted_qty, prod_id;
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
        position = getIntent().getIntExtra("product_id", productId-1);
        name = getIntent().getStringExtra("single_product_name");


        prod_id = findViewById(R.id.single_product_id);
        prod_name = findViewById(R.id.single_product_name);
        prod_pic = findViewById(R.id.single_img);
        prod_quantity = findViewById(R.id.txtview_quantity);
        prod_price = findViewById(R.id.txtview_price);
        prod_desc = findViewById(R.id.txtview_description);
        prod_wanted_qty = findViewById(R.id.wanted_quantity);
        btn_minus = findViewById(R.id.minus_button);
        btn_add = findViewById(R.id.add_button);
        btn_add_to_cart = findViewById(R.id.add_to_cart);

        prod_id.setText(String.valueOf(position));
        prod_name.setText(getIntent().getStringExtra("single_product_name"));
        prod_quantity.setText(String.valueOf(getIntent().getIntExtra("txtview_quantity", 0)));
        prod_price.setText(String.valueOf(getIntent().getDoubleExtra("txtview_price", 0)));
        prod_desc.setText(getIntent().getStringExtra("txtview_description"));

        ViewHolder holder = new ViewHolder();
        holder.img = prod_pic;
        Cursor cursor = dbHelper.getAllData(position,name);
        if (cursor.moveToFirst()) {
            cursor.moveToFirst();
            int fragranceName = cursor.getColumnIndex(DbHelper.COLUMN_IMAGE);
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
                if (number >= 1) {
                    prod_wanted_qty.setText(String.valueOf(number));
                } else {
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
                if (number <= Integer.parseInt(prod_quantity.getText().toString())) {
                    prod_wanted_qty.setText(String.valueOf(number));
                } else {
                    number--;
                    prod_wanted_qty.setText(String.valueOf(number));
                    Toast.makeText(getApplicationContext(), "you can't buy more than we have to offer", Toast.LENGTH_SHORT).show();
                }


            }
        });


        btn_add_to_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = prod_name.getText().toString();
                Double price = Double.parseDouble(prod_price.getText().toString());
                int qty = Integer.parseInt(prod_wanted_qty.getText().toString());
                int id = Integer.parseInt(prod_id.getText().toString());
                double finalPrice = price * qty;


                try {

                    int quantity = MainActivity.productsDatabase.productsDAOtemp().getById(id).getQuantity() - qty;

                    try {
                        products prods = new products();
                        prods.setId(id);

                        prods.setName(MainActivity.productsDatabase.productsDAOtemp().getById(id).getName());
                        prods.setPrice(MainActivity.productsDatabase.productsDAOtemp().getById(id).getPrice());
                        prods.setDescription(MainActivity.productsDatabase.productsDAOtemp().getById(id).getDescription());
                        prods.setQuantity(quantity);

                        MainActivity.productsDatabase.productsDAOtemp().updateProducts(prods);

                    } catch (Exception e) {
                        String message = e.getMessage();
                        System.out.println(message);
                        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
                    }


                    cart Cart = new cart();
                    Cart.setId(id);
                    Cart.setName(name);
                    Cart.setPrice(price);
                    Cart.setFinalPrice(finalPrice);
                    Cart.setQty(qty);

                    MainActivity.productsDatabase.productsDAOtemp().addCart(Cart);
                    Toast.makeText(getApplicationContext(), "product added to cart", Toast.LENGTH_SHORT).show();

                    prod_quantity.setText(String.valueOf(quantity));


                } catch (Exception e) {
                    String message = e.getMessage();
                    System.out.println(message);
                    Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
                }




            }
        });
    }


}
package com.example.eshop_v2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class SingleProductActivity extends AppCompatActivity {



    Button btn_minus , btn_add, btn_add_to_cart;
    TextView prod_name, prod_quantity , prod_price, prod_desc, prod_wanted_qty;
    ImageView prod_pic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_product_activity);



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
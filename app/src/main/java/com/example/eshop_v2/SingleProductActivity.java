package com.example.eshop_v2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class SingleProductActivity extends AppCompatActivity {



    Button btn1 , btn2, btn3;
    TextView txtview1, txtview2 , txtview3, txtview4, txtview5, txtview6, txtview7, txtview8;
    ImageView imgview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_product_activity);

        txtview1 = findViewById(R.id.single_product_name);
        imgview = findViewById(R.id.single_img);
        txtview2 = findViewById(R.id.txtview_quantity);
        txtview3 = findViewById(R.id.txtview_price);
        txtview4 = findViewById(R.id.txtview_description);
        txtview5 = findViewById(R.id.wanted_quantity);
        txtview6 = findViewById(R.id.qty);
        txtview7 = findViewById(R.id.desc);
        txtview8 = findViewById(R.id.prc);
        btn1 = findViewById(R.id.minus_button);
        btn2 = findViewById(R.id.add_button);
        btn3 = findViewById(R.id.add_to_cart);

    }
}
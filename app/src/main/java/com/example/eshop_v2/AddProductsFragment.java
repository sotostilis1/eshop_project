package com.example.eshop_v2;

import static android.app.Activity.RESULT_OK;
import static com.example.eshop_v2.MainActivity.fragmentManager;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.hardware.lights.LightState;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;


import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import android.widget.Button;
import android.widget.EditText;

import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;

import java.util.List;

import javax.annotation.Nullable;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AddProductsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddProductsFragment extends Fragment {

    public static FragmentManager fragmentManager;

    EditText EdtTxt1 , EdtTxt2 , EdtTxt3 ,EdtTxt4 ,EdtTxt5;
    Button Btn_save , Btn_picture;

    TextView txtview;


    private static final int PICK_IMAGE_REQUEST = 100;
    private Uri imagePath;
    private Bitmap imageToStore;

    ImageView productImage;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public AddProductsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AddProductsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AddProductsFragment newInstance(String param1, String param2) {
        AddProductsFragment fragment = new AddProductsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @SuppressLint({"MissingInflatedId", "ClickableViewAccessibility"})
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_products, container, false);

        txtview = view.findViewById(R.id.prod);
        EdtTxt1 = view.findViewById(R.id.edit_text_name);
        EdtTxt2 = view.findViewById(R.id.edit_text_id);
        EdtTxt3 = view.findViewById(R.id.edit_text_description);
        EdtTxt4 = view.findViewById(R.id.edit_text_price);
        EdtTxt5 = view.findViewById(R.id.edit_text_quantity);
        Btn_picture = view.findViewById(R.id.btn_picture);
        productImage = view.findViewById(R.id.product_image);
        //vagg

        productImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                choseImage();
            }
        });

        Btn_picture.setOnTouchListener(new View.OnTouchListener() {


            //GIA EISAGWGH FOTOGRAFIAS (SE PEIRAMATIKO STADIO AKOMA)
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_UP:
                        MainActivity.fragmentManager.beginTransaction().replace(R.id.fragment_container, new PhotoPickerFragment()).addToBackStack(null).commit();

                        return true;
                }
                return false;
            }
        });




        Btn_save = view.findViewById(R.id.button_save);
        Btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int Var_productid = 0;
                try {
                    Var_productid = Integer.parseInt(EdtTxt2.getText().toString());
                } catch (NumberFormatException ex) {
                    System.out.println("Could not parse " + ex);
                }
                int Var_productprice = 0;
                try {
                    Var_productprice = Integer.parseInt(EdtTxt4.getText().toString());
                } catch (NumberFormatException ex) {
                    System.out.println("Could not parse " + ex);
                }
                int Var_productquantity = 0;
                try {
                    Var_productquantity = Integer.parseInt(EdtTxt5.getText().toString());
                } catch (NumberFormatException ex) {
                    System.out.println("Could not parse " + ex);
                }
                String Var_productname = EdtTxt1.getText().toString();
                String Var_productdesc = EdtTxt3.getText().toString();



                try {
                    products prods = new products();
                    prods.setId(Var_productid);
                    prods.setName(Var_productname);
                    prods.setPrice(Var_productprice);
                    prods.setDescription(Var_productdesc);
                    prods.setQuantity(Var_productquantity);

                    MainActivity.productsDatabase.productsDAOtemp().addProducts(prods);

//                    GIA TIN FIREBASE
//                    MainActivity.db.
//                            collection("products").
//                            document(""+Var_productid).
//                            set(prods).
//                            addOnCompleteListener(new OnCompleteListener<Void>() {
//                                @Override
//                                public void onComplete(@NonNull Task<Void> task) {
//                                    Toast.makeText(getActivity(),"Product added.",Toast.LENGTH_LONG).show();
//                                }
//                            })
//                            .addOnFailureListener(new OnFailureListener() {
//                                @Override
//                                public void onFailure(@NonNull Exception e) {
//                                    Toast.makeText(getActivity(),"add operation failed.",Toast.LENGTH_LONG).show();
//                                }
//                            });
                } catch (Exception e) {
                    String message = e.getMessage();
                    System.out.println(message);
                    Toast.makeText(getActivity(),message,Toast.LENGTH_LONG).show();
                }
                Toast.makeText(getActivity(),"product added",Toast.LENGTH_LONG).show();
                EdtTxt1.setText("");
                EdtTxt2.setText("");
                EdtTxt3.setText("");
                EdtTxt4.setText("");
                EdtTxt5.setText("");

            }
        });

        return view;
    }

    private void choseImage()
    {
        try{
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(intent.ACTION_GET_CONTENT);
            startActivityForResult(intent,PICK_IMAGE_REQUEST);
        }catch (Exception e)
        {
            Toast.makeText(getActivity().getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT ).show();
        }
    }

    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data){
        try {
            super.onActivityResult(requestCode, resultCode, data);

            if (resultCode == RESULT_OK && data != null && data.getData() != null) {
                imagePath = data.getData();
                imageToStore = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(),imagePath);
                productImage.setImageBitmap(imageToStore);
            }
        }catch (Exception e)
        {
            Toast.makeText(getActivity().getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT ).show();
        }

    }
}
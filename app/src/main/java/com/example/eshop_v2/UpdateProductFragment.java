package com.example.eshop_v2;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;


import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import android.widget.Button;
import android.widget.EditText;

import android.widget.Toast;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link UpdateProductFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class UpdateProductFragment extends Fragment {

    public static FragmentManager fragmentManager;

    EditText EdtTxt1 , EdtTxt2 , EdtTxt3 ,EdtTxt4 ,EdtTxt5;
    Button Btn_save , Btn_picture;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public UpdateProductFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment UpdateFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static UpdateProductFragment newInstance(String param1, String param2) {
        UpdateProductFragment fragment = new UpdateProductFragment();
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_update_products, container, false);
        EdtTxt1 = view.findViewById(R.id.edit_text_name2);
        EdtTxt2 = view.findViewById(R.id.edit_text_id2);
        EdtTxt3 = view.findViewById(R.id.edit_text_description2);
        EdtTxt4 = view.findViewById(R.id.edit_text_price2);
        EdtTxt5 = view.findViewById(R.id.edit_text_quantity2);
        Btn_picture = view.findViewById(R.id.btn_picture2);
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




        Btn_save = view.findViewById(R.id.button_save2);
        Btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int Var_productid = 0;
                try {
                    Var_productid = Integer.parseInt(EdtTxt2.getText().toString());
                } catch (NumberFormatException ex) {
                    System.out.println("Could not parse " + ex);
                }
                int Var_productprice = -1;
                try {
                    Var_productprice = Integer.parseInt(EdtTxt4.getText().toString());
                } catch (NumberFormatException ex) {
                    System.out.println("Could not parse " + ex);
                }
                int Var_productquantity = -1;
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
                    if ( Var_productname.isEmpty()){
                        prods.setName(MainActivity.productsDatabase.productsDAOtemp().getById(Var_productid).getName());
                    } else {
                        prods.setName(Var_productname);
                    }

                    if ( Var_productprice == -1){
                        prods.setPrice(MainActivity.productsDatabase.productsDAOtemp().getById(Var_productid).getPrice());
                    } else {
                        prods.setPrice(Var_productprice);
                    }

                    if ( Var_productdesc.isEmpty()){
                        prods.setDescription(MainActivity.productsDatabase.productsDAOtemp().getById(Var_productid).getDescription());
                    } else {
                        prods.setDescription(Var_productdesc);
                    }

                    if ( Var_productquantity == -1){
                        prods.setQuantity(MainActivity.productsDatabase.productsDAOtemp().getById(Var_productid).getQuantity());
                    } else {
                        prods.setQuantity(Var_productquantity);
                    }






                    MainActivity.productsDatabase.productsDAOtemp().updateProducts(prods);

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
                Toast.makeText(getActivity(),"product updated",Toast.LENGTH_LONG).show();
                EdtTxt1.setText("");
                EdtTxt2.setText("");
                EdtTxt3.setText("");
                EdtTxt4.setText("");
                EdtTxt5.setText("");

            }
        });
        return view;
    }
}
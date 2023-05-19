package com.example.eshop_v2;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link InsertOrdersFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class InsertOrdersFragment extends Fragment {

    EditText editText1, editText2;
    Button bn;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public InsertOrdersFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment InsertOrdersFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static InsertOrdersFragment newInstance(String param1, String param2) {
        InsertOrdersFragment fragment = new InsertOrdersFragment();
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

        View view =  inflater.inflate(R.layout.fragment_insert_orders, container, false);
        editText1 = view.findViewById(R.id.cid_edttxt1);
        editText2 = view.findViewById(R.id.date_edttxt2);
        bn = view.findViewById(R.id.insertOrdersSubmitButton);
        bn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Var_cid = editText1.getText().toString();
                DocumentReference Var_customerid = MainActivity.db.document("/Customers/" + Var_cid);
                String Var_date = editText2.getText().toString();




                try {
                    Sales sales = new Sales();
                    sales.setCid(Var_customerid);
                    sales.setDate(Var_date);


                    MainActivity.db.
                            collection("Sales").
                            add(sales).
                            addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                @Override
                                public void onSuccess(DocumentReference documentReference) {
                                    Toast.makeText(getActivity(),"Order added.",Toast.LENGTH_LONG).show();



                                    List<cart> Cart = MainActivity.productsDatabase.productsDAOtemp().getCart();

                                    for(cart i: Cart) {
                                        double finalprice = i.getFinalPrice();
                                        int id = i.getId();
                                        int quantity = i.getQty();


                                        String orderId = documentReference.getId();


                                        try {

                                            productsFirestore product = new productsFirestore();
                                            product.setProduct_id(id);
                                            product.setQuantity(quantity);
                                            product.setPrice(finalprice);


                                            MainActivity.db.collection("Sales")
                                                    .document(orderId)
                                                    .collection("productsFirestore")
                                                    .add(product)
                                                    .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                                        @Override
                                                        public void onSuccess(DocumentReference documentReference) {
                                                        }
                                                    })
                                                    .addOnFailureListener(new OnFailureListener() {
                                                        @Override
                                                        public void onFailure(@NonNull Exception e) {
                                                        }
                                                    });
                                        } catch (Exception e) {
                                            String message = e.getMessage();
                                            Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
                                        }
                                    }






                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(getActivity(),"add operation failed.",Toast.LENGTH_LONG).show();
                                }
                            });
                } catch (Exception e) {
                    String message = e.getMessage();
                    Toast.makeText(getActivity(),message,Toast.LENGTH_LONG).show();
                }
                editText1.setText("");
                editText2.setText("");


            }
        });





        return view;
    }
}
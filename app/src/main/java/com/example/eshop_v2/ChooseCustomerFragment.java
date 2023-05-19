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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.functions.Consumer;


public class ChooseCustomerFragment extends Fragment {

    private int id;
    private Disposable disposable;

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private List<Customers> list;
    private CustomerRecyclerAdapter adapter;

    FirebaseFirestore db = FirebaseFirestore.getInstance();

    Button btn_save;
    EditText editText;


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ChooseCustomerFragment() {
        // Required empty public constructor
    }


    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ChooseCustomerFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ChooseCustomerFragment newInstance(String param1, String param2) {
        ChooseCustomerFragment fragment = new ChooseCustomerFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public void onDestroyView() {
        super.onDestroyView();
        if (disposable != null && !disposable.isDisposed()) {
            disposable.dispose();
        }
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

        View view = inflater.inflate(R.layout.fragment_choose_customer, container, false);
        recyclerView = view.findViewById(R.id.recyclerview3);
        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        adapter = new CustomerRecyclerAdapter(new ArrayList<>(), getContext());
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);
        editText = view.findViewById(R.id.date_edttxt2);
        btn_save = view.findViewById(R.id.save_order);
        disposable = adapter.getVariableObservable()
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer variable) throws Exception {

                        id = variable;
                        btn_save.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                String Var_cid = String.valueOf(id);

                                DocumentReference Var_customerid = MainActivity.db.document("/Customers/" + Var_cid);
                                String Var_date = editText.getText().toString();


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
                                                    Toast.makeText(getActivity(), "Order completed", Toast.LENGTH_LONG).show();


                                                    List<cart> Cart = MainActivity.productsDatabase.productsDAOtemp().getCart();

                                                    for (cart i : Cart) {
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
                                                            cart C = new cart();
                                                            MainActivity.productsDatabase.productsDAOtemp().deleteWholeCart();


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
                                                    Toast.makeText(getActivity(), "add operation failed.", Toast.LENGTH_LONG).show();
                                                }
                                            });
                                } catch (Exception e) {
                                    String message = e.getMessage();
                                    Toast.makeText(getActivity(), message, Toast.LENGTH_LONG).show();
                                }
                                editText.setText("");


                            }
                        });



                    }
                });


        db.collection("Customers").get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot querySnapshot) {
                        List<Customers> customersList = new ArrayList<>();
                        for (QueryDocumentSnapshot documentSnapshot : querySnapshot) {
                            Customers customer = documentSnapshot.toObject(Customers.class);

                            customersList.add(customer);
                        }

                        adapter.setData(customersList);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                    }
                });


        return view;
    }
}
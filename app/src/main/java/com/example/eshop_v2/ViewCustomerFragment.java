package com.example.eshop_v2;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupMenu;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ViewCustomerFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ViewCustomerFragment extends Fragment {

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private List<Customers> list;
    private CustomerViewRecyclerAdapter adapter;

    private Context context;

    Button filter_btn , check_btn;

    FirebaseFirestore db = FirebaseFirestore.getInstance();

    CollectionReference collectionReference = db.
            collection("Customers");

    Query query = collectionReference;

    EditText editText;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ViewCustomerFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ViewCustomerFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ViewCustomerFragment newInstance(String param1, String param2) {
        ViewCustomerFragment fragment = new ViewCustomerFragment();
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
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_view_customer, container, false);

        recyclerView = view.findViewById(R.id.recyclerview4);
        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        adapter = new CustomerViewRecyclerAdapter(new ArrayList<>(), getContext());
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);

        editText = view.findViewById(R.id.search_id);
        check_btn = view.findViewById(R.id.check_button);


        fetchDataFromFirestore(query);
        check_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int  Var_cid = Integer.parseInt(editText.getText().toString());
                query = collectionReference.whereEqualTo("cid", Var_cid);
                fetchDataFromFirestore(query);
                editText.setVisibility(View.INVISIBLE);
                check_btn.setVisibility(View.INVISIBLE);

            }
        });



        filter_btn = view.findViewById(R.id.filter_button1);
        filter_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu popupMenu = new PopupMenu(getContext(), view);
                popupMenu.getMenuInflater().inflate(R.menu.filter_menu_firebase, popupMenu.getMenu());
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        // Handle menu item clicks here
                        switch (item.getItemId()) {
                            case R.id.filter_item1:
                                query = collectionReference.orderBy("cname", Query.Direction.ASCENDING);
                                fetchDataFromFirestore(query);
                                break;
                            case R.id.filter_item2:
                                query = collectionReference.orderBy("cname", Query.Direction.DESCENDING);
                                fetchDataFromFirestore(query);
                                break;
                            case R.id.filter_item4:
                                query = collectionReference;
                                fetchDataFromFirestore(query);
                                break;
                            case R.id.filter_item5:
                                editText.setVisibility(View.VISIBLE);
                                check_btn.setVisibility(View.VISIBLE);

                                break;


                        }
                        return true;
                    }
                });
                popupMenu.show();

            }
        });










        return view;
    }

    public void fetchDataFromFirestore(Query query) {
        query.get()
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
                        // Handle failure
                    }
                });
    }
}
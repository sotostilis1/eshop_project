package com.example.eshop_v2;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ViewSalesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ViewSalesFragment extends Fragment {

    TextView querytextresult;


    FirebaseFirestore db = FirebaseFirestore.getInstance();

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ViewSalesFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ViewOrderFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ViewSalesFragment newInstance(String param1, String param2) {
        ViewSalesFragment fragment = new ViewSalesFragment();
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
        View view = inflater.inflate(R.layout.fragment_view_sales, container, false);

        querytextresult = view.findViewById(R.id.txtqueryresult);
        CollectionReference collectionReference = db.collection("Sales");
        collectionReference.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
        @Override
        public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
            StringBuilder result = new StringBuilder();
            for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                Sales sales = documentSnapshot.toObject(Sales.class);
                DocumentReference cidRef = sales.getCid();


                cidRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot document) {
                        if (document.exists()) {
                            // Access the data from the fetched document
                            int cid = document.getLong("cid").intValue();
                            String date = sales.getDate();
                            System.out.println(cid +""+ date);

                            // Append the data to the result string
                            result.append(" cid: ").append(cid).append(" date: ").append(date).append("\n");

                            CollectionReference productsCollectionRef = db.collection("Sales").document(documentSnapshot.getId()).collection("productsFirestore");

                            // Query the products subcollection
                            productsCollectionRef.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                                @Override
                                public void onSuccess(QuerySnapshot querySnapshot) {
                                    for (QueryDocumentSnapshot productSnapshot : querySnapshot) {
                                        productsFirestore product = productSnapshot.toObject(productsFirestore.class);
                                        int productid = product.getProduct_id();
                                        int productqty = product.getQuantity();
                                        double productPrice = product.getPrice();
                                        System.out.println(productid +""+productqty+""+productPrice);

                                        // Append the product details to the result string

                                        result.append("   Product: ").append(productid).append(" | Price: ").append(productPrice).append(" | Quantity: ").append(productqty).append("\n");

                                    }


                                    // Update the text view with the result
                                    querytextresult.setText(result.toString());

                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(getActivity(), "Failed to fetch products subcollection.", Toast.LENGTH_LONG).show();
                                }
                            });
                        }
                    }


                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getActivity(), "Failed to fetch document.", Toast.LENGTH_LONG).show();
                    }
                });
            }
        }
    }).addOnFailureListener(new OnFailureListener() {
        @Override
        public void onFailure(@NonNull Exception e) {
            Toast.makeText(getActivity(), "Query operation failed.", Toast.LENGTH_LONG).show();
        }
    });


        return view;
}

}
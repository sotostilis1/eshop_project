package com.example.eshop_v2;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AddCustomerFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddCustomerFragment extends Fragment {

    TextView txtview;
    EditText edttxt1 , edttxt2 , edttxt3 , edttxt4;
    Button btnsave;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public AddCustomerFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AddCustomerFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AddCustomerFragment newInstance(String param1, String param2) {
        AddCustomerFragment fragment = new AddCustomerFragment();
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
        View view =  inflater.inflate(R.layout.fragment_add_customer, container, false);

        edttxt1 = view.findViewById(R.id.edit_text_cid);
        edttxt2 = view.findViewById(R.id.edit_text_cname);
        edttxt3 = view.findViewById(R.id.edit_text_csurname);
        edttxt4 = view.findViewById(R.id.edit_text_caddress);
        btnsave = view.findViewById(R.id.button_csave);
        txtview = view.findViewById(R.id.customers);
        btnsave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int Var_cid = 0;
                try {
                    Var_cid = Integer.parseInt(edttxt1.getText().toString());
                } catch (NumberFormatException ex) {
                    System.out.println("Could not parse " + ex);
                }
                String Var_cname = edttxt2.getText().toString();
                String Var_csurname = edttxt3.getText().toString();
                String Var_caddress = edttxt4.getText().toString();

                try {
                    int finalVar_cid = Var_cid;
                    MainActivity.db.collection("Customers").document("" + Var_cid).get()
                            .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                @Override
                                public void onSuccess(DocumentSnapshot documentSnapshot) {
                                    if (documentSnapshot.exists()) {
                                        // Display an error message if a document with the same ID already exists
                                        Toast.makeText(getActivity(), "Customer with ID " + finalVar_cid + " already exists.", Toast.LENGTH_LONG).show();
                                    } else {

                                        Customers customers = new Customers();
                                        customers.setCid(finalVar_cid);
                                        customers.setCname(Var_cname);
                                        customers.setCsurname(Var_csurname);
                                        customers.setCaddress(Var_caddress);

                                        MainActivity.db.
                                                collection("Customers").
                                                document("" + finalVar_cid).
                                                set(customers).
                                                addOnCompleteListener(new OnCompleteListener<Void>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<Void> task) {
                                                        Toast.makeText(getActivity(), "Customer added.", Toast.LENGTH_LONG).show();
                                                    }
                                                })
                                                .addOnFailureListener(new OnFailureListener() {
                                                    @Override
                                                    public void onFailure(@NonNull Exception e) {
                                                        Toast.makeText(getActivity(), "add operation failed.", Toast.LENGTH_LONG).show();
                                                    }
                                                });
                                    }
                                }
                            });
                                } catch (Exception e) {
                                    String message = e.getMessage();
                                    Toast.makeText(getActivity(),message,Toast.LENGTH_LONG).show();
                                }
                edttxt1.setText("");
                edttxt2.setText("");
                edttxt3.setText("");
                edttxt4.setText("");
                            }

                });






        return view;
    }
}
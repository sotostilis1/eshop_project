package com.example.eshop_v2;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AddSuppliesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddSuppliesFragment extends Fragment {


    public static FragmentManager fragmentManager;

    EditText EdtTxt1 , EdtTxt2 , EdtTxt3 ,EdtTxt4;
    Button Btn_save , btn_query;
    TextView txtview , txtview1;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public AddSuppliesFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AddSuppliesFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AddSuppliesFragment newInstance(String param1, String param2) {
        AddSuppliesFragment fragment = new AddSuppliesFragment();
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

        View view =  inflater.inflate(R.layout.fragment_add_supplies, container, false);

        txtview = view.findViewById(R.id.trans);
        txtview1 = view.findViewById(R.id.textview8);
        EdtTxt1 = view.findViewById(R.id.edit_text_pr_id);
        EdtTxt2 = view.findViewById(R.id.edit_text_sup_id);
        EdtTxt3 = view.findViewById(R.id.edit_text_date);
        EdtTxt4 = view.findViewById(R.id.edit_text_qnt);
        Btn_save = view.findViewById(R.id.button_save5);
        Btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int Var_productid = 0;
                try {
                    Var_productid = Integer.parseInt(EdtTxt1.getText().toString());
                } catch (NumberFormatException ex) {
                    System.out.println("Could not parse " + ex);
                }
                int Var_supplierid = 0;
                try {
                    Var_supplierid = Integer.parseInt(EdtTxt2.getText().toString());
                } catch (NumberFormatException ex) {
                    System.out.println("Could not parse " + ex);
                }
                int Var_productquantity = 0;
                try {
                    Var_productquantity = Integer.parseInt(EdtTxt4.getText().toString());
                } catch (NumberFormatException ex) {
                    System.out.println("Could not parse " + ex);
                }
                String Var_transactiondate = EdtTxt3.getText().toString();


                try {
                    supplies supplies = new supplies();
                    supplies.setProduct_id(Var_productid);
                    supplies.setSupplier_id(Var_supplierid);
                    supplies.setDate(Var_transactiondate);
                    supplies.setQnt(Var_productquantity);

                    MainActivity.productsDatabase.productsDAOtemp().addSupplies(supplies);

                } catch (Exception e) {
                    String message = e.getMessage();
                    System.out.println(message);
                    Toast.makeText(getActivity(),message,Toast.LENGTH_LONG).show();
                }
                Toast.makeText(getActivity(),"transaction added",Toast.LENGTH_LONG).show();
                EdtTxt1.setText("");
                EdtTxt2.setText("");
                EdtTxt3.setText("");
                EdtTxt4.setText("");

            }
        });
        btn_query= view.findViewById(R.id.button_query4);
        btn_query.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<supplies> Supplies = MainActivity.productsDatabase.productsDAOtemp().getSupplies();
                String result  = "";
                for(supplies i: Supplies) {
                    int idd = i.getSupplier_id();
                    String date = i.getDate();
                    int quan = i.getQnt();
                    int id = i.getProduct_id();
                    result = result + "\n date: " + date +" \n product id: "+id+" \n supplier id: "+idd+" \n quantity: "+quan;
                }
                txtview1.setText(result);

            }
        });

        return view;
    }
}
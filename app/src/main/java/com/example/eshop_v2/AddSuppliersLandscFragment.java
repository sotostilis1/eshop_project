package com.example.eshop_v2;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

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
 * Use the {@link AddSuppliersFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddSuppliersLandscFragment extends Fragment {


    EditText EdtTxt1 , EdtTxt2;
    Button Btn_save;

    TextView txtview;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public AddSuppliersLandscFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AddSuppliersFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AddSuppliersFragment newInstance(String param1, String param2) {
        AddSuppliersFragment fragment = new AddSuppliersFragment();
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


        View view =inflater.inflate(R.layout.fragment_add_suppliers_landsc, container, false);

        txtview = view.findViewById(R.id.supp1);
        EdtTxt1 = view.findViewById(R.id.edit_text_name3);
        EdtTxt2 = view.findViewById(R.id.edit_text_id3);
        Btn_save = view.findViewById(R.id.button_save3);
        Btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int Var_supplierid = 0;
                try {
                    Var_supplierid = Integer.parseInt(EdtTxt2.getText().toString());
                } catch (NumberFormatException ex) {
                    System.out.println("Could not parse " + ex);
                }
                String Var_suppliername = EdtTxt1.getText().toString();




                try {
                    suppliers supps = new suppliers();
                    supps.setId(Var_supplierid);
                    supps.setName(Var_suppliername);


                    MainActivity.productsDatabase.productsDAOtemp().addSuppliers(supps);
                    Toast.makeText(getActivity(),"supplier added",Toast.LENGTH_LONG).show();


                } catch (Exception e) {
                    String message = e.getMessage();
                    System.out.println(message);
                    Toast.makeText(getActivity(),message,Toast.LENGTH_LONG).show();
                }

                EdtTxt1.setText("");
                EdtTxt2.setText("");


            }
        });





        return view;
    }
}
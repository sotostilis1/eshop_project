package com.example.eshop_v2;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CentralFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CentralFragment extends Fragment implements View.OnClickListener {

    public static FragmentManager fragmentManager;


    Button Btn_product , Btn_supplies , Btn_suppliers;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public CentralFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CentralFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CentralFragment newInstance(String param1, String param2) {
        CentralFragment fragment = new CentralFragment();
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
        View view = inflater.inflate(R.layout.fragment_central, container, false);

        Btn_product = view.findViewById(R.id.button_products);
        Btn_product.setOnClickListener(this);
        Btn_suppliers = view.findViewById(R.id.button_suppliers);
        Btn_suppliers.setOnClickListener(this);
        Btn_supplies = view.findViewById(R.id.button_supplies);
        Btn_supplies.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_products:
                MainActivity.fragmentManager.beginTransaction().replace(R.id.fragment_container, new ProductsFragment()).addToBackStack(null).commit();
                break;
            case R.id.button_suppliers:
                MainActivity.fragmentManager.beginTransaction().replace(R.id.fragment_container, new SuppliersFragment()).addToBackStack(null).commit();
                break;
            case R.id.button_supplies:
                MainActivity.fragmentManager.beginTransaction().replace(R.id.fragment_container, new AddSuppliesFragment()).addToBackStack(null).commit();
                break;
        }

    }


}
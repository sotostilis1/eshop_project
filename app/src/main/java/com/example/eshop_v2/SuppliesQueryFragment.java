package com.example.eshop_v2;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SuppliesQueryFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SuppliesQueryFragment extends Fragment {

    public static FragmentManager fragmentManager;

    TextView txtview;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public SuppliesQueryFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SuppliesQueryFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SuppliesQueryFragment newInstance(String param1, String param2) {
        SuppliesQueryFragment fragment = new SuppliesQueryFragment();
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


        View view =  inflater.inflate(R.layout.fragment_supplies_query, container, false);
        txtview = view.findViewById(R.id.textview1);


        List<supplies> Supplies = MainActivity.productsDatabase.productsDAOtemp().getSupplies();
        String result  = "";
        for(supplies i: Supplies) {
            int idd = i.getSupplier_id();
            String date = i.getDate();
            int quan = i.getQnt();
            int id = i.getProduct_id();
            result = result + "\n date: " + date +" \n product id: "+id+" \n supplier id: "+idd+" \n quantity: "+quan;
        }
        txtview.setText(result);
        return view;
    }
}
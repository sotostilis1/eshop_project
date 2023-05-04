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
 * Use the {@link QueryProductFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class QueryProductFragment extends Fragment {

    public static FragmentManager fragmentManager;

    TextView txtview;
    TextView imageview;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public QueryProductFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment QueryFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static QueryProductFragment newInstance(String param1, String param2) {
        QueryProductFragment fragment = new QueryProductFragment();
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
        View view =  inflater.inflate(R.layout.fragment_query, container, false);

        txtview = view.findViewById(R.id.textview1);
        imageview = view.findViewById(R.id.image_view_product);

                List<products> Products = MainActivity.productsDatabase.productsDAOtemp().getProducts();
                String result  = "";
                for(products i: Products) {
                    String name = i.getName();
                    String desc = i.getDescription();
                    int id = i.getId();
                    double price = i.getPrice();
                    int quantity = i.getQuantity();
                    result = result + "\n name: " + name + " \n desc: "+desc+" \n id: "+id+" \n price: "+price+" \n quantity: "+quantity+"\n ";
                }
                txtview.setText(result);


        return view;
    }
}
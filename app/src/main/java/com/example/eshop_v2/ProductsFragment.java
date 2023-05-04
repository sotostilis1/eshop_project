package com.example.eshop_v2;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProductsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProductsFragment extends Fragment implements View.OnClickListener {

    public static FragmentManager fragmentManager;


    Button Btn_del , Btn_add, Btn_query, Btn_update;
    TextView txtview;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ProductsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SotirisFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ProductsFragment newInstance(String param1, String param2) {
        ProductsFragment fragment = new ProductsFragment();
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
        View view = inflater.inflate(R.layout.fragment_products, container, false);

        txtview = view.findViewById(R.id.prod1);
        Btn_add = view.findViewById(R.id.button_add1);
        Btn_add.setOnClickListener(this);
        Btn_query = view.findViewById(R.id.button_query1);
        Btn_query.setOnClickListener(this);
        Btn_del = view.findViewById(R.id.button_delete1);
        Btn_del.setOnClickListener(this);
        Btn_update = view.findViewById(R.id.button_update1);
        Btn_update.setOnClickListener(this);






        return view;
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_add1:
                MainActivity.fragmentManager.beginTransaction().replace(R.id.fragment_container, new AddProductsFragment()).addToBackStack(null).commit();
            break;
            case R.id.button_delete1:
                MainActivity.fragmentManager.beginTransaction().replace(R.id.fragment_container, new DeleteProductFragment()).addToBackStack(null).commit();
            break;
            case R.id.button_update1:
                MainActivity.fragmentManager.beginTransaction().replace(R.id.fragment_container, new UpdateProductFragment()).addToBackStack(null).commit();
            break;
            case R.id.button_query1:
                MainActivity.fragmentManager.beginTransaction().replace(R.id.fragment_container, new QueryProductFragment()).addToBackStack(null).commit();
            break;
        }

    }
}
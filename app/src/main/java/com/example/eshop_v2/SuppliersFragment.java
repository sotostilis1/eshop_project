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
 * Use the {@link SuppliersFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SuppliersFragment extends Fragment implements View.OnClickListener {

    public static FragmentManager fragmentManager;


    Button Btn_add, Btn_del;
    TextView txtview;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public SuppliersFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SuppliersFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SuppliersFragment newInstance(String param1, String param2) {
        SuppliersFragment fragment = new SuppliersFragment();
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

        View view = inflater.inflate(R.layout.fragment_suppliers, container, false);
        txtview = view.findViewById(R.id.supp);
        Btn_add = view.findViewById(R.id.button_add2);
        Btn_add.setOnClickListener(this);
        Btn_del = view.findViewById(R.id.button_delete2);
        Btn_del.setOnClickListener(this);
        // Inflate the layout for this fragment
        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_add2:
                MainActivity.fragmentManager.beginTransaction().replace(R.id.fragment_container, new AddSuppliersFragment()).addToBackStack(null).commit();
                break;
            case R.id.button_delete2:
                MainActivity.fragmentManager.beginTransaction().replace(R.id.fragment_container, new DeleteSuppliersFragment()).addToBackStack(null).commit();
                break;

        }

    }
}
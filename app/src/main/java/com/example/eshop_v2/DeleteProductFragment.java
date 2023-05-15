package com.example.eshop_v2;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DeleteProductFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DeleteProductFragment extends Fragment {

    private static final String TAG = "DeleteProductFragment" ;
    public static FragmentManager fragmentManager;

    TextView txtview;
    EditText Edt_txt1;
    Button btn_delete;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public DeleteProductFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DeleteFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DeleteProductFragment newInstance(String param1, String param2) {
        DeleteProductFragment fragment = new DeleteProductFragment();
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
        View view = inflater.inflate(R.layout.fragment_delete_products, container, false);
        txtview = view.findViewById(R.id.prod4);
        Edt_txt1 = view.findViewById(R.id.delete_id);
        btn_delete = view.findViewById(R.id.button_del);
        btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int Var_id = 0;
                try {
                    Var_id = Integer.parseInt(Edt_txt1.getText().toString());

                }catch (NumberFormatException ex){
                    System.out.println("Could not parse"+ ex);
                }

                Context context = getActivity(); // Retrieve the context of the parent activity

                DbHelper dbHelper = new DbHelper(context);
                products Products = new products();
                Products.setId(Var_id);
                MainActivity.productsDatabase.productsDAOtemp().deleteProducts(Products);
                Log.d(TAG, Var_id + Products.getName());
                dbHelper.deleteColumn(Var_id);
                Toast.makeText(getActivity(),"Product Deleted",Toast.LENGTH_LONG).show();
                Edt_txt1.setText("");




            }
        });
        return view;
    }


}
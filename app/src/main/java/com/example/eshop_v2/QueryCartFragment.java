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
 * Use the {@link QueryCartFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class QueryCartFragment extends Fragment {

    public static FragmentManager fragmentManager;

    TextView txtview;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public QueryCartFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment QueryCartFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static QueryCartFragment newInstance(String param1, String param2) {
        QueryCartFragment fragment = new QueryCartFragment();
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
        View view =  inflater.inflate(R.layout.fragment_query_cart, container, false);

        txtview = view.findViewById(R.id.textview1);


        List<cart> Cart = MainActivity.productsDatabase.productsDAOtemp().getCart();
        String result  = "";
        for(cart i: Cart) {
            String name = i.getName();
            double finalprice = i.getFinalPrice();
            int id = i.getId();
            double price = i.getPrice();
            int quantity = i.getQty();
            result = result + "\n name: " + name + " \n finalprice: "+finalprice+" \n id: "+id+" \n price: "+price+" \n quantity: "+quantity+"\n ";
        }
        txtview.setText(result);


        return view;
    }
}
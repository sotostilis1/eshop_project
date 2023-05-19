package com.example.eshop_v2;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupMenu;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ViewTransactionsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ViewTransactionsFragment extends Fragment {


    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private List<supplies> list;
    private ViewTransactionsRecyclerAdapter adapter;
    private Context context;
    Button btn_filter ,btn_check;

    EditText editText;

    productsDAO dao = MainActivity.productsDatabase.productsDAOtemp();

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ViewTransactionsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ViewTransactionsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ViewTransactionsFragment newInstance(String param1, String param2) {
        ViewTransactionsFragment fragment = new ViewTransactionsFragment();
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
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_view_transactions, container, false);

        recyclerView = view.findViewById(R.id.recyclerview8);
        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        list = dao.getSupplies();

        editText = view.findViewById(R.id.search_id);
        btn_check = view.findViewById(R.id.check_button);

        adapter = new ViewTransactionsRecyclerAdapter( list, context);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);
        context = getContext();

        btn_filter = view.findViewById(R.id.filter_button);
        btn_check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               int  Var_sid = Integer.parseInt(editText.getText().toString());
                List<supplies> filteredList3 = dao.searchProductsExcludingId(Var_sid);
                adapter.updateList(filteredList3);
                editText.setVisibility(View.INVISIBLE);
                btn_check.setVisibility(View.INVISIBLE);

            }
        });
        btn_filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu popupMenu = new PopupMenu(context, view);
                popupMenu.getMenuInflater().inflate(R.menu.filter_menu, popupMenu.getMenu());
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        // Handle menu item clicks here
                        switch (item.getItemId()) {

                            case R.id.filter_item3:
                                List<supplies> filteredList = dao.getTransactionsByAscendingQty();
                                adapter.updateList(filteredList);
                                break;
                            case R.id.filter_item4:
                                List<supplies> filteredList1 = dao.getTransactionsByDescendingQty();
                                adapter.updateList(filteredList1);
                                break;
                            case R.id.filter_item2:
                                List<supplies> filteredList2 = dao.getSupplies();
                                adapter.updateList(filteredList2);
                                break;
                            case R.id.filter_item1:
                                editText.setVisibility(View.VISIBLE);
                                btn_check.setVisibility(View.VISIBLE);

                                break;
                        }
                        return true;
                    }
                });
                popupMenu.show();

            }
        });


        return view;

    }
}
package com.example.eshop_v2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.annotation.SuppressLint;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    public static FragmentManager fragmentManager;
    public static FirebaseFirestore db;
    public static productsDatabase productsDatabase;
    private ActionBarDrawerToggle drawerToggle;
    private DrawerLayout drawerLayout;



    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel("channel_id", "Channel Name", NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawerLayout = findViewById(R.id.drawer_layout);
        drawerToggle = new ActionBarDrawerToggle(this, drawerLayout,toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(drawerToggle);
        drawerToggle.syncState();

        NavigationView navigationView = findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(this);

        db = FirebaseFirestore.getInstance();
        fragmentManager = getSupportFragmentManager();
        productsDatabase =
                Room.databaseBuilder(getApplicationContext(),productsDatabase.class, "productsDB")
                        .allowMainThreadQueries().build();



        if(findViewById(R.id.fragment_container)!=null){
            if(savedInstanceState!=null){
                return;
            }
            fragmentManager.beginTransaction().add(R.id.fragment_container, new CentralFragment()).commit();
        }







    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_menu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle menu item clicks here
        int itemId = item.getItemId();
        if (itemId == R.id.action_cart) {
            showFragment(new CartFragment());
            return true;
        } else if (itemId == R.id.action_search) {
            // Handle menu item 2 click
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){
            case R.id.home:
                showFragment(new HomeFragment());
                break;
            case R.id.profile:
                showFragment(new ProductsFragment());
                break;
            case R.id.add_product_option:
                showFragment(new AddProductsFragment());
                break;
            case R.id.drop_product_option:
                showFragment(new DeleteProductFragment());
                break;
            case R.id.edit_product_option:
                showFragment(new UpdateProductFragment());
                break;
            case R.id.suppliers:
                showFragment(new SuppliersFragment());
                break;
            case R.id.add_supp_option:
                showFragment(new AddSuppliersFragment());
                break;
            case R.id.drop_supp_option:
                showFragment(new DeleteSuppliersFragment());
                break;
            case R.id.add_transaction:
                showFragment(new AddSuppliesFragment());
                break;
            case R.id.add_customer:
                showFragment(new AddCustomerFragment());
                break;
            case R.id.del_customer:
                showFragment(new DeleteCustomerFragment());
                break;
            case R.id.query_cart:
                showFragment(new QueryCartFragment());
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    private void showFragment (Fragment fragment)
    {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.fragment_container,fragment);
        ft.commit();
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
}
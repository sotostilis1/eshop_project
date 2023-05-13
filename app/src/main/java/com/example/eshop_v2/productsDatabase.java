package com.example.eshop_v2;


import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {products.class , supplies.class, suppliers.class, cart.class}, version = 1)
public abstract class productsDatabase extends RoomDatabase {
    public abstract productsDAO productsDAOtemp();
}

package com.example.eshop_v2;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface productsDAO {
    @Insert
    public void addProducts (products products);

    @Delete
    public void deleteProducts (products products);

    @Update
    public void updateProducts (products products);

    @Query("select * from products")
    public List<products> getProducts();

    @Query("SELECT * FROM products WHERE product_id = :id")
    public products getById(int id);

    @Query("SELECT * FROM products WHERE product_quantity > 0")
    List<products> getProductsByQuantity();

    @Query("SELECT * FROM products ORDER BY product_price ASC")
    List<products> getProductsByAscendingPrice();

    @Query("SELECT * FROM products ORDER BY product_price DESC")
    List<products> getProductsByDescendingPrice();

    @Query("SELECT * FROM products WHERE product_name LIKE '%' || :searchQuery || '%'")
    List<products> searchProductsByName(String searchQuery);




    @Insert
    public void addSuppliers (suppliers suppliers);

    @Delete
    public void deleteSuppliers (suppliers suppliers);

    @Query("select * from suppliers")
    public List<suppliers> getSuppliers();



    @Insert
    public void addSupplies (supplies supplies);

    @Delete
    public void deleteSupplies (supplies supplies);

    @Update
    public void updateSupplies (supplies supplies);

    @Query("select * from supplies")
    public List<supplies> getSupplies();

    @Query("SELECT * FROM supplies ORDER BY quantity ASC")
    List<supplies> getTransactionsByAscendingQty();

    @Query("SELECT * FROM supplies ORDER BY quantity DESC")
    List<supplies> getTransactionsByDescendingQty();

    @Query("SELECT * FROM supplies WHERE sid <> :supplierId")
    List<supplies> searchProductsExcludingId(int supplierId);






    @Insert
    public void addCart (cart cart);

    @Delete
    public void deleteCart (cart cart);

    @Query("select * from cart")
    public List<cart> getCart();

    @Query("Delete from cart")
    void deleteWholeCart();

    @Query("SELECT * FROM cart WHERE product_ID = :id")
    public cart getByIdd(int id);





}

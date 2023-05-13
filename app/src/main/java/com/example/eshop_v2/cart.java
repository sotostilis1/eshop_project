package com.example.eshop_v2;


import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;

import org.jetbrains.annotations.NotNull;

@Entity(tableName = "cart",
        primaryKeys = {"product_ID"},
        foreignKeys = {
                @ForeignKey(entity = products.class,
                        parentColumns = "product_id",
                        childColumns = "product_ID",
                        onDelete = ForeignKey.CASCADE,
                        onUpdate = ForeignKey.CASCADE),
        })
public class cart {

    @ColumnInfo(name = "product_ID")
    private int id; @NotNull

    @ColumnInfo(name = "product_name")
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @ColumnInfo (name = "product_wanted_qty")
    private int qty;

    @ColumnInfo (name = "product_price")
    private double price;

    @ColumnInfo (name = "product_finalPrice")
    private double finalPrice;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getFinalPrice() {
        return finalPrice;
    }

    public void setFinalPrice(double finalPrice) {
        this.finalPrice = finalPrice;
    }
}

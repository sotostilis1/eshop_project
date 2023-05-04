package com.example.eshop_v2;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity
public class products {

    @ColumnInfo (name = "product_name")
    private String name;
    @ColumnInfo (name = "product_quantity")
    private int quantity;
    @ColumnInfo (name = "product_price")
    private double price;
    @ColumnInfo (name = "product_desc")
    private String description;

    @ColumnInfo (name = "product_id")
    @PrimaryKey
    private int id;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}

package com.example.eshop_v2;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;

import org.jetbrains.annotations.NotNull;

@Entity(tableName = "supplies",
        primaryKeys = {"pid", "sid", "supply_date"},
        foreignKeys = {
        @ForeignKey(entity = products.class,
                    parentColumns = "product_id",
                    childColumns = "pid",
                    onDelete = ForeignKey.CASCADE,
                    onUpdate = ForeignKey.CASCADE),
        @ForeignKey(entity = suppliers.class,
                    parentColumns = "supplier_id",
                    childColumns = "sid",
                    onDelete = ForeignKey.CASCADE,
                    onUpdate = ForeignKey.CASCADE),
        })
public class supplies {
    @ColumnInfo( name = "pid")
    private int product_id; @NotNull

    @ColumnInfo( name = "sid")
    private int supplier_id; @NotNull

    @ColumnInfo(name = "supply_date")
    private String date; @NotNull

    @ColumnInfo(name = "quantity")
    private int qnt;

    public int getQnt() {
        return qnt;
    }

    public void setQnt(int qnt) {
        this.qnt = qnt;
    }

    public int getProduct_id() {return product_id;}

    public void setProduct_id(int product_id) {
        this.product_id = product_id;
    }

    public int getSupplier_id() {
        return supplier_id;
    }

    public void setSupplier_id(int supplier_id) {
        this.supplier_id = supplier_id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }


}

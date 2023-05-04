package com.example.eshop_v2;


import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import org.jetbrains.annotations.NotNull;

@Entity
public class suppliers {

    @ColumnInfo(name = "supplier_id")
    @PrimaryKey
    private int id; @NotNull


    @ColumnInfo (name = "supplier_name")

    private String name;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

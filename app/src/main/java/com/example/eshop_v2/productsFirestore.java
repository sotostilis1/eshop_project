package com.example.eshop_v2;

public class productsFirestore {
    private int product_id;
    private int quantity;

    public productsFirestore(int product_id, int quantity, double price) {
        this.product_id = product_id;
        this.quantity = quantity;
        this.price = price;
    }

    public productsFirestore() {
    }

    public int getProduct_id() {
        return product_id;
    }

    public void setProduct_id(int product_id) {
        this.product_id = product_id;
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

    private double price;
}

package com.example.eshop_v2;

import com.google.firebase.firestore.DocumentReference;

public class Sales {
    private DocumentReference cid;
    private String date;

    public DocumentReference getCid() {
        return cid;
    }

    public Sales(DocumentReference cid, String date) {
        this.cid = cid;
        this.date = date;
    }

    public Sales() {
    }

    public void setCid(DocumentReference cid) {
        this.cid = cid;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}

package com.example.item;

public class SpinnerJenis {
    String id, nama, kode;

    public SpinnerJenis() {

        this.id="";
        this.nama="";
        this.kode="";

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNama(){
        return nama;
    }

    public void setNama(String nama){
        this.nama = nama;
    }

    public String getKode(){
        return kode;
    }

    public void setKode(String kode) {
        this.kode = kode;
    }

    @Override
    public String toString () {
        return id;
    }}
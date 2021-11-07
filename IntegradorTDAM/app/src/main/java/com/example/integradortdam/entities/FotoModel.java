package com.example.integradortdam.entities;


public class FotoModel {



    public FotoModel(){}

    public FotoModel( int imagen1) {
        //this.name = name;
        this.imagen1 = imagen1;
        //this.imagen2 = imagen2;
        //this.imagen3 = imagen3;
    }

    private String title;
    private int imagen1;

    private String id;


    public String getTitle() { return title; }
    public void setTitle(String title) {
        this.title = title;
    }

    public int getImagen1() { return imagen1; }
    public void setImagen1(int imagen1) {
        this.imagen1 = imagen1;
    }

    public String getId() { return id; }
    public void setId(String id) {
        this.id = id;
    }



}
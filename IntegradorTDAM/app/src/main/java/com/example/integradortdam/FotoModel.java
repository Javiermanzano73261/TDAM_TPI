package com.example.integradortdam;


import android.media.Image;
import android.widget.ImageView;

public class FotoModel {





    public FotoModel( int imagen1) {
        //this.name = name;
        this.imagen1 = imagen1;
        //this.imagen2 = imagen2;
        //this.imagen3 = imagen3;
    }

    private String name;
    private int imagen1;
    private int imagen2;
    private int imagen3;


    public String getName() { return name; }
    public void setName(String name) {
        this.name = name;
    }

    public int getImagen1() { return imagen1; }
    public void setImagen1(int imagen1) {
        this.imagen1 = imagen1;
    }

    public int getImagen2() { return imagen2; }
    public void setImagen2(int imagen2) {
        this.imagen2 = imagen2;
    }

    public int getImagen3() { return imagen3; }
    public void setImagen3(int imagen3) {
        this.imagen3 = imagen3;
    }

}
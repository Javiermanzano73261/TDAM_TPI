package com.example.integradortdam;

import java.io.Serializable;

public class AlbumModel implements Serializable {



    public AlbumModel() {
    }

    public AlbumModel(String name, int tamanio, int imagen1, int imagen2, int imagen3, int imagen4) {
        this.name = name;
        this.tamanio = tamanio;
        this.imagen1 = imagen1;
        this.imagen2 = imagen2;
        this.imagen3 = imagen3;
        this.imagen4 = imagen4;
    }

    private String name;
    private int tamanio;
    private int imagen1;
    private int imagen2;
    private int imagen3;
    private int imagen4;

    public Integer getTamanio() {
        return tamanio;
    }
    public void setTamanio(int tamanio) {
        this.tamanio = tamanio;
    }

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

    public int getImagen4() { return imagen4; }
    public void setImagen4(int imagen4) {
        this.imagen4 = imagen4;
    }
}

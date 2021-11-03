package com.example.integradortdam.entities;

import java.io.Serializable;
import java.util.ArrayList;

public class AlbumModel implements Serializable {



    public AlbumModel() {
    }

    public AlbumModel(String name, int tamanio, int imagen1, int imagen2, int imagen3, int imagen4) {
        this.title = name;
        this.total = tamanio;
        this.imagen1 = imagen1;
        this.imagen2 = imagen2;
        this.imagen3 = imagen3;
        this.imagen4 = imagen4;
    }

    private int imagen1;
    private int imagen2;
    private int imagen3;
    private int imagen4;

    private long id;
    private String primary;
    private String owner;
    private String ownername;
    private ArrayList photo;
    private int page;
    private int per_page;
    private int pages;
    private String title;
    private int total;
    private String stat;


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

    public long getId() { return id; }
    public void setId(long id) { this.id = id; }

    public String getPrimary() { return primary; }
    public void setPrimary(String primary) { this.primary = primary; }

    public String getOwner() { return owner; }
    public void setOwner(String owner) { this.owner = owner; }

    public String getOwnername() { return ownername; }
    public void setOwnername(String ownername) { this.ownername = ownername; }

    public ArrayList getPhoto() { return photo; }
    public void setPhoto(ArrayList photo) { this.photo = photo; }

    public int getPage() { return page; }
    public void setPage(int page) { this.page = page; }

    public int getPer_page() {return per_page; }
    public void setPer_page(int per_page) { this.per_page = per_page; }

    public int getPages() { return pages; }
    public void setPages(int pages) { this.pages = pages; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public int getTotal() { return total; }
    public void setTotal(int total) { this.total = total; }

    public String getStat() { return stat; }
    public void setStat(String stat) { this.stat = stat; }
}

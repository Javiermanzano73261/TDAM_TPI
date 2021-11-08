package com.example.integradortdam.entities;


import android.graphics.Bitmap;
import java.io.Serializable;
import java.util.ArrayList;

public class FotoModel implements Serializable {

    public FotoModel(){}

    public FotoModel( int imagen1) {
        //this.name = name;
        this.imagen1 = imagen1;
        //this.imagen2 = imagen2;
        //this.imagen3 = imagen3;
    }

    private String title;
    private int imagen1;
    private Bitmap imagen;
    private ArrayList<ComentarioModel> comentarios;
    private String id;


    private String server;
    private String secret;
    private String webUrl;
    private String imageUrl;



    public String getTitle() { return title; }
    public void setTitle(String title) {
        this.title = title;
    }

    public int getImagen1() { return imagen1; }
    public void setImagen1(int imagen1) { this.imagen1 = imagen1; }

    public Bitmap getImagen() { return imagen; }
    public void setImagen(Bitmap imagen) { this.imagen = imagen; }

    public String getId() { return id; }
    public void setId(String id) {
        this.id = id;
    }

    public String getServer() { return server; }
    public void setServer(String server) { this.server = server; }

    public String getSecret() { return secret; }
    public void setSecret(String secret) {
        this.secret = secret;
    }

    public String getWebUrl() { return webUrl; }
    public void setWebUrl(String url) {
        this.webUrl = url;
    }

    public String getImageUrl() { return imageUrl; }
    public void setImageUrl(String url) {
        this.imageUrl = url;
    }

    public ArrayList<ComentarioModel> getComentarios() { return comentarios; }
    public void setComentarios(ArrayList<ComentarioModel> comentarios) { this.comentarios = comentarios; }



}
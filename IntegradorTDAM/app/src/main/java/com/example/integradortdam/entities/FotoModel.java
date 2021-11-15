package com.example.integradortdam.entities;


import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import java.io.Serializable;

import static androidx.room.ForeignKey.CASCADE;



@Entity(tableName = "foto_table", foreignKeys = @ForeignKey(entity = AlbumModel.class,
        parentColumns = "id",
        childColumns = "albumId",
        onDelete = CASCADE))
public class FotoModel implements Serializable {

    public FotoModel(){}

    public FotoModel( int imagen1) {
        this.imagen1 = imagen1;
    }

    private String title;
    private int imagen1;
    @PrimaryKey
    @NonNull
    private String id;
    private String albumId;


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

    @NonNull
    public String getId() { return id; }
    public void setId(@NonNull String id) {
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

    public String getAlbumId() { return albumId; }
    public void setAlbumId(String albumId) { this.albumId = albumId; }

}
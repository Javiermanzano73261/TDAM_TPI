package com.example.integradortdam.entities;


import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "album_table")
public class AlbumModel implements Serializable {



    public AlbumModel() {
    }

    private int imagen1;
    private int imagen2;
    private int imagen3;
    private int imagen4;

    @PrimaryKey
    @NonNull
    private String id;
    private long primary;
    private String owner;
    private String username;
    private int date_create;
    private String title;
    private String ownername;
    private int count_photos;


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

    @NonNull
    public String getId() { return id; }
    public void setId(@NonNull String id) { this.id = id; }

    public long getPrimary() { return primary; }
    public void setPrimary(long primary) { this.primary = primary; }

    public String getOwner() { return owner; }
    public void setOwner(String owner) { this.owner = owner; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public int getCount_photos() { return count_photos; }
    public void setCount_photos(int count_photos) { this.count_photos = count_photos; }

    public int getDate_create() { return date_create; }
    public void setDate_create(int date_create) { this.date_create = date_create; }

    public String getOwnername() { return ownername; }
    public void setOwnername(String ownername) { this.ownername = ownername; }

}

package com.example.integradortdam.entities;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "comentario_table")
public class ComentarioModel implements Serializable {

    private String _content;
    private String realname;

    @PrimaryKey
    @NonNull
    private String id;


    public String get_content() { return _content; }
    public void set_content(String _content) { this._content = _content; }

    public String getRealname() { return realname; }
    public void setRealname(String realname) { this.realname = realname; }

    @NonNull
    public String getId() { return id; }
    public void setId(@NonNull String id) { this.id = id; }



}

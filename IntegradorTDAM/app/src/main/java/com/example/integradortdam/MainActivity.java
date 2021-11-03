package com.example.integradortdam;

import android.app.Activity;
import android.os.Bundle;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import android.view.View;
import android.content.Intent;
import android.widget.Button;

public class MainActivity extends Activity {

    private RecyclerView reyclerViewAlbum;
    private RecyclerView.Adapter mAdapter;
    private Button bt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        reyclerViewAlbum = (RecyclerView) findViewById(R.id.reyclerViewAlbum);

        reyclerViewAlbum.setHasFixedSize(true);

        reyclerViewAlbum.setLayoutManager(new LinearLayoutManager(this));
        //reyclerViewUser.setLayoutManager(new GridLayoutManager(this, 3));

        mAdapter = new AlbumAdapter(getAlbums());
        reyclerViewAlbum.setAdapter(mAdapter);



    }


    public List<AlbumModel> getAlbums() {

        List<AlbumModel> albumModels = new ArrayList<>();
        albumModels.add(new AlbumModel("Paisajes", 50, R.drawable.imagen1, R.drawable.imagen2, R.drawable.imagen3, R.drawable.imagen4));
        albumModels.add(new AlbumModel("Japón", 153, R.drawable.imagen2, R.drawable.imagen2, R.drawable.imagen2, R.drawable.imagen2));
        albumModels.add(new AlbumModel("Zuiza", 26, R.drawable.imagen3, R.drawable.imagen4, R.drawable.imagen3, R.drawable.imagen4));
        albumModels.add(new AlbumModel("Sierras Chicas", 11, R.drawable.imagen4, R.drawable.imagen4, R.drawable.imagen4, R.drawable.imagen4));
        albumModels.add(new AlbumModel("Eslovenia", 5, R.drawable.imagen3, R.drawable.imagen3, R.drawable.imagen1, R.drawable.imagen4));

        for(int i = 1; i < 150; i++) {
            albumModels.add(new AlbumModel("Paisajes " + i, i + 2*i, R.drawable.imagen1, R.drawable.imagen2, R.drawable.imagen3, R.drawable.imagen4));
        }

        return albumModels;
    }



}
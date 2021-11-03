package com.example.integradortdam;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;

import java.util.ArrayList;
import java.util.List;

public class SecondActivity extends AppCompatActivity  {

    private RecyclerView reyclerViewFotos;
    private RecyclerView.Adapter mAdapter;
    private AlbumModel itemDetail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        reyclerViewFotos = (RecyclerView) findViewById(R.id.reyclerViewFotos);

        reyclerViewFotos.setHasFixedSize(true);

        //reyclerViewFotos.setLayoutManager(new LinearLayoutManager(this));
        reyclerViewFotos.setLayoutManager(new GridLayoutManager(this, 3));

        mAdapter = new FotoAdapter(getFotos());
        reyclerViewFotos.setAdapter(mAdapter);

        //initValues();
    }

    private void initValues(){
        itemDetail = (AlbumModel) getIntent().getExtras().getSerializable("ItemDetail");

    }



    public List<FotoModel> getFotos() {

        List<FotoModel> fotoModels = new ArrayList<>();
        fotoModels.add(new FotoModel(R.drawable.imagen4));

        for(int i = 1; i < 15; i++) {
            fotoModels.add(new FotoModel(R.drawable.imagen2));
        //    fotoModels.add(new FotoModel("Fotos #", R.drawable.imagen2, R.drawable.imagen3, R.drawable.imagen4));
        }

        return fotoModels;
    }


    



}
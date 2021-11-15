package com.example.integradortdam;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.example.integradortdam.entities.ComentarioModel;
import com.example.integradortdam.entities.FotoModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;


public class ThirdActivity extends AppCompatActivity {

    private FotoModel item;
    private ArrayList<ComentarioModel> comentarios;
    private ImageView imagen;
    private TextView cantidad;
    private RecyclerView reyclerViewComentarios;
    private ComentarioAdapter mAdapter;
    private AppRepository mRepository;
    private FloatingActionButton btnURL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mRepository = new AppRepository(this.getApplication());
        setContentView(R.layout.activity_third);

        recibirDatos();
        imagen = (ImageView) findViewById(R.id.imagenId);
        cantidad = (TextView) findViewById(R.id.txtCantComentarios);
        btnURL = (FloatingActionButton) findViewById(R.id.BtnURL);

        loadImage();

        reyclerViewComentarios = (RecyclerView) findViewById(R.id.reyclerViewComentarios);
        reyclerViewComentarios.setHasFixedSize(true);
        reyclerViewComentarios.setLayoutManager(new LinearLayoutManager(this));

        mAdapter = new ComentarioAdapter(comentarios);
        reyclerViewComentarios.setAdapter(mAdapter);

        if(comentarios!=null){
        String st = String.valueOf(comentarios.size());
        cantidad.setText("(" + st + ")");}

        btnURL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri link = Uri.parse(item.getWebUrl());

                Intent i = new Intent(Intent.ACTION_SENDTO);
                i.setData(Uri.parse("mailto:"));
                i.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.subjectEmail));
                String text = getString(R.string.textEmail) + " "+ link;
                i.putExtra(Intent.EXTRA_TEXT, text);

                startActivity(i);
            }
        });
    }

    private void recibirDatos(){
        Bundle extras = getIntent().getExtras();
        item = (FotoModel) extras.getSerializable("FotoClick");
        try {
            comentarios = (ArrayList<ComentarioModel>) mRepository.getComentariosDeFotoDB(item.getId());
        }
        catch (Exception e) { e.printStackTrace();}
    }

    private void loadImage() {
        ImageLoader imageLoader = MyApplication.getImageLoader();

        String url = item.getImageUrl();
        imageLoader.get(url, new ImageLoader.ImageListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }

            @Override
            public void onResponse(ImageLoader.ImageContainer response, boolean arg1) {
                imagen.setImageBitmap(response.getBitmap());
            }
        });
    }

    public void actualizarComentarios(List<ComentarioModel> comments){
        if(comments.size() != 0) {
            mAdapter.setComentarioModelList(comments);
            mAdapter.notifyDataSetChanged();

            String st = String.valueOf(comments.size());
            cantidad.setText("(" + st + ")");
        }
    }




}
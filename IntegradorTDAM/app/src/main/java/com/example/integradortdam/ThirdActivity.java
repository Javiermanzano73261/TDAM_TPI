package com.example.integradortdam;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.example.integradortdam.entities.FotoModel;


public class ThirdActivity extends AppCompatActivity {

    private FotoModel item;
    private ImageView imagen;
    private TextView cantidad;
    private RecyclerView reyclerViewComentarios;
    private RecyclerView.Adapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third);

        recibirDatos();
        imagen = (ImageView) findViewById(R.id.imagenId);
        cantidad = (TextView) findViewById(R.id.txtCantComentarios);

        loadImage();
        //getApiComent();

        reyclerViewComentarios = (RecyclerView) findViewById(R.id.reyclerViewComentarios);
        reyclerViewComentarios.setHasFixedSize(true);

        reyclerViewComentarios.setLayoutManager(new LinearLayoutManager(this));

        cargarComentarios();


    }

    private void recibirDatos(){
        Bundle extras = getIntent().getExtras();
        item = (FotoModel) extras.getSerializable("FotoClick");
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

    private void cargarComentarios(){
        if(item.getComentarios().size() != 0) {
            mAdapter = new ComentarioAdapter(item.getComentarios());
            reyclerViewComentarios.setAdapter(mAdapter);
            String st = String.valueOf(item.getComentarios().size());
            cantidad.setText("(" + st + ")");
        }
    }
/*
    private void getApiComent() {

        String url = "https://www.flickr.com/services/rest/?method=flickr.photos.comments.getList&api_key=9c3a294665a3de8e2d3bcc06f6679760&photo_id="+item.getId()+"&format=json&nojsoncallback=1";

        JsonObjectRequest stringRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // Display the first 500 characters of the response string.
                        try {

                            JSONObject obj = response.getJSONObject("comments");
                            Log.d("Prueba", response.toString());

                            JSONArray jsonarray = obj.optJSONArray("comment");
                            //Log.d("Comentarios", jsonarray.toString());

                            ArrayList<ComentarioModel> coments = new ArrayList<ComentarioModel>();
                            for(int i=0;i<jsonarray.length();i++){
                                JSONObject object = jsonarray.getJSONObject(i);
                                ComentarioModel comentario = new ComentarioModel();

                                comentario.setRealname(object.optString("realname"));
                                comentario.set_content(object.optString("_content"));
                                coments.add(comentario);
                            }

                            item.setComentarios(coments);
                            cargarComentarios();


                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }
        );// Add the request to the RequestQueue.
        MyApplication.getSharedQueue().add(stringRequest);
        //return ps;
    }*/



}
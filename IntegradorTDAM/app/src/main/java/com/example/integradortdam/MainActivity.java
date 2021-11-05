package com.example.integradortdam;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.example.integradortdam.entities.AlbumModel;
import com.example.integradortdam.entities.PhotoSetsModel;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends Activity {

    private RecyclerView reyclerViewAlbum;
    private RecyclerView.Adapter mAdapter;
    private Button bt;
    private TextView text;
    private Gson gson;

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


        text = (TextView) findViewById(R.id.txtTitulo);

        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setDateFormat("M/d/yy hh:mm a");
        gson = gsonBuilder.create();
        getApiData();

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

    private void getApiData() {

        String url = "https://www.flickr.com/services/rest/?method=flickr.photosets.getList&api_key=051da5c34d0ba38d8aad21537bf093d5&user_id=193998612%40N06&format=json&nojsoncallback=1&auth_token=72157720821031410-e9e194b5d794641a&api_sig=835cdd0eb449cbc42ecabb0b1291b77a";
        //text.setText("");
        JsonObjectRequest stringRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // Display the first 500 characters of the response string.
                        text.setText("Response: " + response);
                        //showText();

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                text.setText("Error: " + error.getMessage());
                //showText();
            }
        }
        );// Add the request to the RequestQueue.
        MyApplication.getSharedQueue().add(stringRequest);
    }




//ACÁ se
    private void getApiData3() {
        String url = "https://www.flickr.com/services/rest/?method=flickr.photosets.getList&api_key=051da5c34d0ba38d8aad21537bf093d5&user_id=193998612%40N06&format=json&nojsoncallback=1&auth_token=72157720821031410-e9e194b5d794641a&api_sig=835cdd0eb449cbc42ecabb0b1291b77a";
        StringRequest request = new StringRequest(Request.Method.GET, url, onPhotoSetsLoaded, onPhotoSetsError);
        MyApplication.getSharedQueue().add(request);
    }

    private final Response.Listener<String> onPhotoSetsLoaded = new Response.Listener<String>() {
        @Override
        public void onResponse(String response) {
            List<PhotoSetsModel> photosets = Arrays.asList(gson.fromJson(response, PhotoSetsModel[].class));
            StringBuilder builder = new StringBuilder("post size: " + photosets.size() + " posts loaded.\n");
            for (PhotoSetsModel photoset : photosets) {
                builder.append("photoset: " + photoset.getTotal() + ": " + photoset.getPhotoset() + "\n");
            }
            text.setText(builder.toString());
            //showText();
        }
    };

    private final Response.ErrorListener onPhotoSetsError = new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {
            //text.setText("Error: " + error.getMessage());
            //showText();
        }
    };





}
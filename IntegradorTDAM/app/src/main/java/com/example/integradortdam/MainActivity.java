package com.example.integradortdam;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.integradortdam.entities.AlbumModel;
import com.example.integradortdam.entities.FotoModel;
import com.example.integradortdam.entities.PhotoSetsModel;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity {

    private RecyclerView reyclerViewAlbum;
    private RecyclerView.Adapter mAdapter;
    private TextView text;
    private PhotoSetsModel ps = new PhotoSetsModel();
    private int control = 0;
    private int completos = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getApiData();

        reyclerViewAlbum = (RecyclerView) findViewById(R.id.reyclerViewAlbum);
        reyclerViewAlbum.setHasFixedSize(true);
        reyclerViewAlbum.setLayoutManager(new LinearLayoutManager(this));
        //reyclerViewUser.setLayoutManager(new GridLayoutManager(this, 3));

        text = (TextView) findViewById(R.id.txtTitulo);

    }



    public List<AlbumModel> getAlbumsMock() {

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

        String url = "https://www.flickr.com/services/rest/?method=flickr.photosets.getList&api_key=7ae04b066fe12540605c2f10ba426a6b&user_id=193998612%40N06&format=json&nojsoncallback=1";
        //text.setText("");
        JsonObjectRequest stringRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // Display the first 500 characters of the response string.
                        //text.setText(response.toString());
                        try {

                            JSONObject obj = response.getJSONObject("photosets");

                            ps.setPage(obj.optInt("page"));
                            ps.setPages(obj.optInt("pages"));
                            ps.setPerpage(obj.optInt("perpage"));
                            Log.d("Prueba", response.toString());
                            ps.setTotal(obj.getInt("total"));

                            JSONArray jsonarray = obj.optJSONArray("photoset");
                            Log.d("Almbum", jsonarray.toString());
                            control = jsonarray.length();

                            List<AlbumModel> sets = new ArrayList<AlbumModel>();
                            for(int i=0;i<jsonarray.length();i++){
                                JSONObject object = jsonarray.getJSONObject(i);
                                AlbumModel album = new AlbumModel();
                                album.setId(object.optString("id"));
                                album.setPrimary(object.optLong("primary"));
                                album.setOwner(object.optString("owner"));
                                album.setUsername(object.optString("username"));
                                album.setDate_create(object.optInt("date_create"));
                                JSONObject title = object.optJSONObject("title");
                                album.setTitle(title.optString("_content"));
                                album.setCount_photos(object.optInt("count_photos"));

                                album.setPhoto(getApiPhotos(album.getId(), album.getOwner()));
                                sets.add(album);

                            }
                            ps.setPhotoset(sets);


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
    }

    private ArrayList<FotoModel> getApiPhotos(String albumID, String ownerID){
        ArrayList<FotoModel> fotos = new ArrayList<>();
        String url = "https://www.flickr.com/services/rest/?method=flickr.photosets.getPhotos&api_key=7ae04b066fe12540605c2f10ba426a6b&photoset_id="+albumID+"&user_id="+ownerID+"&format=json&nojsoncallback=1";
        JsonObjectRequest stringRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            Log.d("URL", url);
                            Log.d("JSON Photo", response.toString());
                            JSONObject obj = response.getJSONObject("photoset");
                            Log.d("Photo set", obj.toString());
                            JSONArray jsonarray = obj.optJSONArray("photo");
                            Log.d("Photo List", jsonarray.toString());

                            for(int i=0;i<jsonarray.length();i++){
                                JSONObject object = jsonarray.getJSONObject(i);
                                FotoModel foto = new FotoModel();
                                foto.setId(object.optString("id"));
                                foto.setTitle(object.optString("title"));
                                foto.setServer(object.optString("server"));
                                foto.setSecret(object.optString("secret"));
                                foto.setWebUrl("https://www.flickr.com/photos/"+ownerID+"/"+foto.getId());
                                foto.setImageUrl("https://live.staticflickr.com/"+foto.getServer()+"/"+foto.getId()+"_"+foto.getSecret()+"_w.jpg");
                                //foto.setImagen(loadImage(foto.getImageUrl()));
                                fotos.add(foto);
                            }
                            completos += 1;
                            if (completos == control){actualizarUI();}

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }


                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                text.setText("Error: " + error.getMessage());

            }
        }
        );// Add the request to the RequestQueue.
        MyApplication.getSharedQueue().add(stringRequest);
        return fotos;
    }

    private void actualizarUI(){
        //if(ps != null) {
            mAdapter = new AlbumAdapter(ps.getPhotoset());
            reyclerViewAlbum.setAdapter(mAdapter);
        //}
    }



}
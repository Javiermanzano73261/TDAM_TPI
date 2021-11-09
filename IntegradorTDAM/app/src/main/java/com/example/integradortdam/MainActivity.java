package com.example.integradortdam;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.integradortdam.entities.AlbumModel;
import com.example.integradortdam.entities.ComentarioModel;
import com.example.integradortdam.entities.FotoModel;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class MainActivity extends Activity {

    private RecyclerView reyclerViewAlbum;
    private RecyclerView.Adapter mAdapter;
    private TextView text;
    private ArrayList<AlbumModel> sets = new ArrayList<AlbumModel>();
    private int control = 0;
    private int completos = 0;
    private Boolean cargaCompleta = Boolean.FALSE;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getApiData();

        reyclerViewAlbum = (RecyclerView) findViewById(R.id.reyclerViewAlbum);
        reyclerViewAlbum.setHasFixedSize(true);
        reyclerViewAlbum.setLayoutManager(new LinearLayoutManager(this));
        //reyclerViewUser.setLayoutManager(new GridLayoutManager(this, 3));

        cargarMenuOpciones();



    }

    private void cargarMenuOpciones(){
        ArrayList<String> opciones = new ArrayList<>();
        opciones.add("Ordenar A-Z");
        opciones.add("Ordenar más nuevo primero");
        opciones.add("Ordenar más antiguo primero");

        ArrayAdapter adp = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, opciones);

        //ImageView icon = findViewById(R.id.imageOrdenar);
        Spinner mSpinner= findViewById(R.id.spinnerOrdenar);
        mSpinner.setAdapter(adp);

        mSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String opcion = (String)  mSpinner.getAdapter().getItem(position);

                if(cargaCompleta){
                    if(opcion == "Ordenar A-Z"){
                        actualizarUI(ordenarXAZ());
                    }
                    else if(opcion == "Ordenar más nuevo primero"){
                        actualizarUI(sets);
                    }
                    else if(opcion == "Ordenar más antiguo primero"){
                        actualizarUI(ordenarXantiguedad());
                    }
                    Toast.makeText(MainActivity.this, "Seleccionaste "+ opcion, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                Toast.makeText(MainActivity.this, "No hay selección de filtro", Toast.LENGTH_SHORT).show();
            }
        });
    }


   private ArrayList<AlbumModel> ordenarXAZ(){
       ArrayList<AlbumModel> list = (ArrayList<AlbumModel>) sets.clone();
       Collections.sort(list, new Comparator<AlbumModel>() {
           @Override
           public int compare(AlbumModel obj1, AlbumModel obj2) {
               return obj1.getTitle().compareTo(obj2.getTitle());
           }
       });
       return list;
   }

    private ArrayList<AlbumModel> ordenarXantiguedad(){
        ArrayList<AlbumModel> list = (ArrayList<AlbumModel>) sets.clone();
        Collections.reverse(list);
        return list;
    }


    private void getApiData() {

        String url = "https://www.flickr.com/services/rest/?method=flickr.photosets.getList&api_key=9c3a294665a3de8e2d3bcc06f6679760&user_id=193998612%40N06&format=json&nojsoncallback=1";
        //text.setText("");
        JsonObjectRequest stringRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // Display the first 500 characters of the response string.
                        //text.setText(response.toString());
                        try {

                            JSONObject obj = response.getJSONObject("photosets");
                            Log.d("Prueba", response.toString());

                            JSONArray jsonarray = obj.optJSONArray("photoset");
                            Log.d("Almbum", jsonarray.toString());
                            control = jsonarray.length();

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
                        } catch (Exception e) {
                            e.printStackTrace();
                            Toast.makeText(MainActivity.this, "Error al actualizar albums", Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, "Error al actualizar albums", Toast.LENGTH_SHORT).show();
            }
        }
        );// Add the request to the RequestQueue.
        MyApplication.getSharedQueue().add(stringRequest);
    }

    private ArrayList<FotoModel> getApiPhotos(String albumID, String ownerID){
        ArrayList<FotoModel> fotos = new ArrayList<>();
        String url = "https://www.flickr.com/services/rest/?method=flickr.photosets.getPhotos&api_key=9c3a294665a3de8e2d3bcc06f6679760&photoset_id="+albumID+"&user_id="+ownerID+"&format=json&nojsoncallback=1";
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
                                foto.setComentarios(getApiComent(foto));
                                fotos.add(foto);
                            }
                            completos += 1;
                            if (completos == control){
                                cargaCompleta = Boolean.TRUE;
                                actualizarUI(sets);
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                            Toast.makeText(MainActivity.this, "Error al actualizar fotos", Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, "Error al actualizar fotos", Toast.LENGTH_SHORT).show();

            }
        }
        );// Add the request to the RequestQueue.
        MyApplication.getSharedQueue().add(stringRequest);
        return fotos;
    }

    private ArrayList<ComentarioModel> getApiComent(FotoModel foto) {
        ArrayList<ComentarioModel> coments = new ArrayList<ComentarioModel>();

        String url = "https://www.flickr.com/services/rest/?method=flickr.photos.comments.getList&api_key=9c3a294665a3de8e2d3bcc06f6679760&photo_id="+foto.getId()+"&format=json&nojsoncallback=1";
        Log.d("Url comentario", url);
        JsonObjectRequest stringRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // Display the first 500 characters of the response string.
                        try {
                            JSONObject obj = response.getJSONObject("comments");
                            Log.d("Prueba", response.toString());

                            JSONArray jsonarray = obj.optJSONArray("comment");

                            for(int i=0;i<jsonarray.length();i++){
                                JSONObject object = jsonarray.getJSONObject(i);
                                ComentarioModel comentario = new ComentarioModel();

                                comentario.setRealname(object.optString("realname"));
                                comentario.set_content(object.optString("_content"));
                                comentario.setId(object.optString("id"));
                                coments.add(comentario);
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                            Toast.makeText(MainActivity.this, "Error al actualizar comentarios", Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, "Error al actualizar comentarios", Toast.LENGTH_SHORT).show();
            }
        }
        );// Add the request to the RequestQueue.
        MyApplication.getSharedQueue().add(stringRequest);
        return coments;
    }

    private void actualizarUI(ArrayList<AlbumModel> sets){
        mAdapter = new AlbumAdapter(sets);
        reyclerViewAlbum.setAdapter(mAdapter);
    }



}
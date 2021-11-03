package com.example.integradortdam;

import android.app.Activity;
import android.os.Bundle;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import android.provider.ContactsContract;
import android.widget.Button;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.integradortdam.entities.AlbumModel;
import com.example.integradortdam.entities.PhotoSetsModel;

import org.json.JSONException;
import org.json.JSONObject;

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



    private void getApiData() {
        List<String> datos = new ArrayList<String>();
        List<AlbumModel> albumModels = new ArrayList<>();
        String url = "b41e94f33287aa681f0849f51762&user_id=193998612%40N06&format=json&nojsoncallback=1&auth_token=72157720820952642-2ada7b3fef1706af&api_sig=c94a95ba84b74b851ed96452f6a51c17";
        //text.setText("");
        JsonObjectRequest stringRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        if(response.length() > 0){
                            for(int i=0; i < response.length(); i++){
                                try {
                                    JSONObject obj = response.getJSONObject(String.valueOf(i));

                                    datos.add((String) obj.get("photoset"));
                                    PhotoSetsModel ps = new PhotoSetsModel();
                                    ps.setCancreate((Integer) obj.get("cancreate"));
                                    ps.setPage((Integer) obj.get("page"));
                                    ps.setPages((Integer) obj.get("pages"));
                                    ps.setPerpage((Integer) obj.get("perpage"));
                                    ps.setTotal((Integer) obj.get("total"));
                                    ps.setPhotoset((ArrayList) obj.get("photoset"));
                                    ps.setStat((String) obj.get("stat"));

                                }
                                catch (JSONException e){
                                    e.printStackTrace();
                                }
                            }

                        }
                        // Display the first 500 characters of the response string.
                        //text.setText((CharSequence) response);
                        //showText();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //text.setText("Error: " + error.getMessage());
                //showText();
            }
        }
        );// Add the request to the RequestQueue.
        MyApplication.getSharedQueue().add(stringRequest);

    }





}
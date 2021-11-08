package com.example.integradortdam;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.integradortdam.entities.AlbumModel;
import com.example.integradortdam.entities.FotoModel;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class SecondActivity extends AppCompatActivity  {

    private RecyclerView reyclerViewFotos;
    private RecyclerView.Adapter mAdapter;
    private TextView text;
    private AlbumModel item;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        recibirDatos();

        text = (TextView) findViewById(R.id.txtTitulo);
        text.setText(item.getTitle());

        reyclerViewFotos = (RecyclerView) findViewById(R.id.reyclerViewFotos);
        reyclerViewFotos.setHasFixedSize(true);

        //reyclerViewFotos.setLayoutManager(new LinearLayoutManager(this));
        reyclerViewFotos.setLayoutManager(new GridLayoutManager(this, 3));

        mAdapter = new FotoAdapter(item.getPhoto());
        reyclerViewFotos.setAdapter(mAdapter);

    }

    private void recibirDatos(){
        Bundle extras = getIntent().getExtras();
        item = (AlbumModel) extras.getSerializable("AlbumClick");
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

    private void getApiData() {

        String url = "https://www.flickr.com/services/rest/?method=flickr.photosets.getPhotos&api_key=2b7ab41e94f33287aa681f0849f51762&photoset_id=72157720113238557&user_id=193998612%40N06&format=json&nojsoncallback=1&auth_token=72157720820952642-2ada7b3fef1706af&api_sig=a725a5c965af93eed6abfda5ce9d6813";
        text.setText("");
        JsonObjectRequest stringRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        if(response.length() > 0){
                            for(int i=0; i < response.length(); i++){
                                try {
                                    JSONObject obj = response.getJSONObject(String.valueOf(i));
                                    Album a = new Album();
                                    a.setId((long) obj.get("id"));
                                    a.setPrimary((String) obj.get("primary"));
                                    a.setOwner((String) obj.get("owner"));
                                    a.setOwnername((String) obj.get("ownername"));
                                    a.setPhoto((ArrayList) obj.get("photo"));
                                    a.setPage((Integer) obj.get("page"));
                                    a.setPer_page((Integer) obj.get("per_page"));
                                    a.setPages((Integer) obj.get("pages"));
                                    a.setTitle((String) obj.get("title"));
                                    a.setTotal((Integer) obj.get("total"));
                                    a.setStat((String) obj.get("stat"));
                                }
                                catch (JSONException e){
                                    e.printStackTrace();
                                }
                            }

                        }
                        // Display the first 500 characters of the response string.
                        text.setText((CharSequence) response);
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
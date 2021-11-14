package com.example.integradortdam;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.integradortdam.entities.AlbumModel;
import com.example.integradortdam.entities.FotoModel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class SecondActivity extends AppCompatActivity  {

    private RecyclerView reyclerViewFotos;
    private FotoAdapter mAdapter;
    private TextView text;
    private AlbumModel item;
    private ArrayList<FotoModel> fotos;
    private AppRepository mRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mRepository = new AppRepository(this.getApplication());
        mRepository.setSecondActivity(this);
        setContentView(R.layout.activity_second);

        recibirDatos();

        text = (TextView) findViewById(R.id.txtTitulo);
        text.setText(item.getTitle());

        reyclerViewFotos = (RecyclerView) findViewById(R.id.reyclerViewFotos);
        reyclerViewFotos.setHasFixedSize(true);

        //reyclerViewFotos.setLayoutManager(new LinearLayoutManager(this));
        reyclerViewFotos.setLayoutManager(new GridLayoutManager(this, 3));

        mAdapter = new FotoAdapter(fotos, mRepository);
        reyclerViewFotos.setAdapter(mAdapter);

        //actualizarUI(fotos);
        cargarMenuOpciones();

    }

    public void actualizarUI(ArrayList<FotoModel> fotos){
        mAdapter.setFotoModelList(fotos);
        mAdapter.notifyDataSetChanged();
    }

    private void recibirDatos(){
        Bundle extras = getIntent().getExtras();
        item = (AlbumModel) extras.getSerializable("AlbumClick");

        try { fotos = (ArrayList<FotoModel>) mRepository.fotosDeAlbum(item.getId(), item.getOwner()); }
        catch (Exception e) { e.printStackTrace(); }
    }

    private ArrayList<FotoModel> ordenarXAZ(){
        ArrayList<FotoModel> list = (ArrayList<FotoModel>) fotos.clone();
        Collections.sort(list, new Comparator<FotoModel>() {
            @Override
            public int compare(FotoModel obj1, FotoModel obj2) {
                return obj1.getTitle().compareTo(obj2.getTitle());
            }
        });
        return list;
    }

    private ArrayList<FotoModel> ordenarXantiguedad(){
        ArrayList<FotoModel> list = (ArrayList<FotoModel>) fotos.clone();
        Collections.reverse(list);
        return list;
    }

    private void cargarMenuOpciones(){
        ArrayList<String> opciones = new ArrayList<>();
        opciones.add(getString(R.string.orderbyOld));
        opciones.add(getString(R.string.orderbyNew));
        opciones.add(getString(R.string.orderbyAZ));

        ArrayAdapter adp = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, opciones);

        //ImageView icon = findViewById(R.id.imageOrdenar);
        Spinner mSpinner= findViewById(R.id.spinnerOrdenar);
        mSpinner.setAdapter(adp);

        mSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String opcion = (String)  mSpinner.getAdapter().getItem(position);

                if(opcion == getString(R.string.orderbyAZ)){
                    actualizarUI(ordenarXAZ());
                }
                else if(opcion == getString(R.string.orderbyNew)){
                    actualizarUI(ordenarXantiguedad());
                }
                else if(opcion == getString(R.string.orderbyOld)){
                    actualizarUI(fotos);
                }
                Toast.makeText(SecondActivity.this, getString(R.string.toast) + opcion, Toast.LENGTH_SHORT).show();
                }


            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                Toast.makeText(SecondActivity.this, getString(R.string.errorToast), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void setFotos(ArrayList<FotoModel> fotos) { this.fotos = fotos; }


/*
    public List<FotoModel> getFotos() {

        List<FotoModel> fotoModels = new ArrayList<>();
        fotoModels.add(new FotoModel(R.drawable.imagen4));

        for(int i = 1; i < 15; i++) {
            fotoModels.add(new FotoModel(R.drawable.imagen2));
        //    fotoModels.add(new FotoModel("Fotos #", R.drawable.imagen2, R.drawable.imagen3, R.drawable.imagen4));
        }

        return fotoModels;
    }
 */

    /*
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
                                    AlbumModel a = new AlbumModel();
                                    a.setId((String) obj.get("id"));
                                    a.setPrimary((long) obj.get("primary"));
                                    a.setOwner((String) obj.get("owner"));
                                    a.setOwnername((String) obj.get("ownername"));
                                    a.setPhoto((ArrayList) obj.get("photo"));
                                    a.setTitle((String) obj.get("title"));
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
    */



}
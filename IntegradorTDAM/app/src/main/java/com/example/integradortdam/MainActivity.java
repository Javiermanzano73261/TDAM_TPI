package com.example.integradortdam;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.integradortdam.entities.AlbumModel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class MainActivity extends Activity {

    private RecyclerView reyclerViewAlbum;
    private RecyclerView.Adapter mAdapter;
    private TextView text;
    private ArrayList<AlbumModel> sets;
    private int control = 0;
    private int completos = 0;
    private boolean cargaCompleta = false;
    private AppRepository mRepository;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.sets = new ArrayList<AlbumModel>();
        this.mRepository = new AppRepository(this.getApplication(), this);
        //mRepository.setActivity(this);
        setContentView(R.layout.activity_main);

        //getApiData();

        reyclerViewAlbum = (RecyclerView) findViewById(R.id.reyclerViewAlbum);
        reyclerViewAlbum.setHasFixedSize(true);
        reyclerViewAlbum.setLayoutManager(new LinearLayoutManager(this));
        //reyclerViewUser.setLayoutManager(new GridLayoutManager(this, 3));

        cargarMenuOpciones();
        actualizarUI(sets);


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
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                Toast.makeText(MainActivity.this, "No hay selección de filtro", Toast.LENGTH_SHORT).show();
            }
        });
    }


   private ArrayList<AlbumModel> ordenarXAZ(){
       ArrayList<AlbumModel> list = (ArrayList<AlbumModel>) this.sets.clone();
       Collections.sort(list, new Comparator<AlbumModel>() {
           @Override
           public int compare(AlbumModel obj1, AlbumModel obj2) {
               return obj1.getTitle().compareTo(obj2.getTitle());
           }
       });
       return list;
   }

    private ArrayList<AlbumModel> ordenarXantiguedad(){
        ArrayList<AlbumModel> list = (ArrayList<AlbumModel>) this.sets.clone();
        Collections.reverse(list);
        return list;
    }

    public void setAlbumsBD(ArrayList<AlbumModel> sets){
        this.sets = sets;
        cargaCompleta = true;
    }

    public void setAlbums(ArrayList<AlbumModel> sets){
        this.sets = sets;
        actualizarUI(sets);
        cargaCompleta = true;
    }


    private void actualizarUI(ArrayList<AlbumModel> sets){
        mAdapter = new AlbumAdapter(sets, mRepository);
        reyclerViewAlbum.setAdapter(mAdapter);
    }



}
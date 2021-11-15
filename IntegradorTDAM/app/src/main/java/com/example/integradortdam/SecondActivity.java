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

public class SecondActivity extends AppCompatActivity {

    private RecyclerView reyclerViewFotos;
    private FotoAdapter mAdapter;
    private TextView text;
    private AlbumModel item;
    private ArrayList<FotoModel> fotos;
    private AppRepository mRepository;
    private boolean menuCargado = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mRepository = new AppRepository(this.getApplication());
        setContentView(R.layout.activity_second);

        recibirDatos();

        text = (TextView) findViewById(R.id.txtTitulo);
        text.setText(item.getTitle());

        reyclerViewFotos = (RecyclerView) findViewById(R.id.reyclerViewFotos);
        reyclerViewFotos.setHasFixedSize(true);
        //reyclerViewFotos.setLayoutManager(new LinearLayoutManager(this));
        reyclerViewFotos.setLayoutManager(new GridLayoutManager(this, 3));

        mAdapter = new FotoAdapter(ordenarXantiguedad(), mRepository);
        reyclerViewFotos.setAdapter(mAdapter);

        cargarMenuOpciones();
    }

    public void actualizarUI(ArrayList<FotoModel> fotos) {
        mAdapter.setFotoModelList(fotos);
        mAdapter.notifyDataSetChanged();
    }

    private void recibirDatos() {
        Bundle extras = getIntent().getExtras();
        item = (AlbumModel) extras.getSerializable("AlbumClick");

        try {
            fotos = (ArrayList<FotoModel>) mRepository.getFotosDeAlbumDB(item.getId());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private ArrayList<FotoModel> ordenarXAZ() {
        ArrayList<FotoModel> list = (ArrayList<FotoModel>) fotos.clone();
        Collections.sort(list, new Comparator<FotoModel>() {
            @Override
            public int compare(FotoModel obj1, FotoModel obj2) {
                return obj1.getTitle().compareTo(obj2.getTitle());
            }
        });
        return list;
    }

    private ArrayList<FotoModel> ordenarXantiguedad() {
        ArrayList<FotoModel> list = (ArrayList<FotoModel>) fotos.clone();
        Collections.reverse(list);
        return list;
    }

    private void cargarMenuOpciones() {
        ArrayList<String> opciones = new ArrayList<>();
        opciones.add(getString(R.string.orderbyNew));
        opciones.add(getString(R.string.orderbyOld));
        opciones.add(getString(R.string.orderbyAZ));

        ArrayAdapter adp = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, opciones);

        Spinner mSpinner = findViewById(R.id.spinnerOrdenar);
        mSpinner.setAdapter(adp);

        mSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String opcion = (String) mSpinner.getAdapter().getItem(position);

                if (opcion == getString(R.string.orderbyAZ)) {
                    actualizarUI(ordenarXAZ());
                } else if (opcion == getString(R.string.orderbyNew)) {
                    actualizarUI(ordenarXantiguedad());
                } else if (opcion == getString(R.string.orderbyOld)) {
                    actualizarUI(fotos);
                }
                if (menuCargado) {
                    Toast.makeText(SecondActivity.this, getString(R.string.selectionMessage) + opcion, Toast.LENGTH_SHORT).show();
                }
                menuCargado = true;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                Toast.makeText(SecondActivity.this, getString(R.string.errorSelectionMessage), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
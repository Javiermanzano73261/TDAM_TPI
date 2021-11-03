package com.example.integradortdam;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class ThirdActivity extends AppCompatActivity {
    private FotoModel itemDetail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third);
    }

    private void initValues(){
        itemDetail = (FotoModel) getIntent().getExtras().getSerializable("ItemDetail2");

    }

}
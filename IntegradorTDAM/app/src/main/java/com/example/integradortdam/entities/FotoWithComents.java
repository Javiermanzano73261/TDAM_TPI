package com.example.integradortdam.entities;

import androidx.room.Embedded;
import androidx.room.Relation;

import java.util.ArrayList;
public class FotoWithComents {

        @Embedded
        public FotoModel foto;
        @Relation(
                parentColumn = "fotoId",
                entityColumn = "fotoCreatorId"
        )
        public ArrayList<ComentarioModel> comentarios;


}

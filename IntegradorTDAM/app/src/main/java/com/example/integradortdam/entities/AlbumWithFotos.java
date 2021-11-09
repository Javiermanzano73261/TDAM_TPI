package com.example.integradortdam.entities;

import androidx.room.Embedded;
import androidx.room.Relation;
import java.util.ArrayList;

public class AlbumWithFotos {
        @Embedded public AlbumModel album;
        @Relation(
                parentColumn = "albumId",
                entityColumn = "albumCreatorId"
        )
        public ArrayList<FotoModel> fotos;

}

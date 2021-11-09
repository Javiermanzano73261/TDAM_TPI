package com.example.integradortdam;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.integradortdam.entities.ComentarioModel;

import java.util.List;

@Dao
public interface ComentarioDAO {
    // LiveData is a data holder class that can be observed within a given lifecycle.
    // Always holds/caches latest version of data. Notifies its active observers when the
    // data has changed. Since we are getting all the contents of the database,
    // we are notified whenever any of the database contents have changed.
    @Query("SELECT * FROM comentario_table")
    LiveData<List<ComentarioModel>> getComentarios();

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(ComentarioModel comentario);

    @Query("DELETE FROM comentario_table")
    void deleteAll();

    @Delete
    void deleteComentario(ComentarioModel comentario);

    @Update(onConflict = OnConflictStrategy.IGNORE)
        void updateComentario(ComentarioModel comentario);
}

package com.example.integradortdam;

import androidx.lifecycle.LiveData;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Dao;
import androidx.room.Update;

import com.example.integradortdam.entities.AlbumModel;
import com.example.integradortdam.entities.FotoModel;

import java.util.List;

@Dao
public interface FotoDAO {
    // LiveData is a data holder class that can be observed within a given lifecycle.
    // Always holds/caches latest version of data. Notifies its active observers when the
    // data has changed. Since we are getting all the contents of the database,
    // we are notified whenever any of the database contents have changed.
    @Query("SELECT * FROM foto_table")
    LiveData<List<AlbumModel>> getFotos();

    @Query("SELECT * FROM foto_table ORDER BY title ASC")
    LiveData<List<AlbumModel>> getAlphabetizedFotos();

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(FotoModel foto);

    @Query("DELETE FROM foto_table")
    void deleteAll();

    @Delete
    void deleteFoto(FotoModel foto);

    @Update(onConflict = OnConflictStrategy.IGNORE)
    void updateFoto(FotoModel foto);
}

package com.example.integradortdam;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.integradortdam.entities.AlbumModel;

import java.util.List;

@Dao
public interface AlbumDAO {

    // LiveData is a data holder class that can be observed within a given lifecycle.
    // Always holds/caches latest version of data. Notifies its active observers when the
    // data has changed. Since we are getting all the contents of the database,
    // we are notified whenever any of the database contents have changed.
    @Query("SELECT * FROM album_table")
    LiveData<List<AlbumModel>> getAlbums();

    @Query("SELECT * FROM album_table ORDER BY title ASC")
    LiveData<List<AlbumModel>> getAlphabetizedAlbums();


    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(AlbumModel album);

    @Query("DELETE FROM album_table")
    void deleteAll();

    @Delete
    void deleteAlbum(AlbumModel album);

    @Update(onConflict = OnConflictStrategy.IGNORE)
    void updateAlbum(AlbumModel album);
}

package com.example.integradortdam;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.integradortdam.entities.AlbumModel;
import com.example.integradortdam.entities.ComentarioModel;
import com.example.integradortdam.entities.FotoModel;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


@Database(entities = {AlbumModel.class, FotoModel.class, ComentarioModel.class}, version = 1, exportSchema = false)
abstract class AppRoomDatabase extends RoomDatabase{

        abstract AlbumDAO albumDao();
        abstract FotoDAO fotoDao();
        abstract ComentarioDAO comentarioDao();

        // marking the instance as volatile to ensure atomic access to the variable
        private static volatile AppRoomDatabase INSTANCE;
        private static final int NUMBER_OF_THREADS = 4;
        static final ExecutorService databaseWriteExecutor =
                Executors.newFixedThreadPool(NUMBER_OF_THREADS);

        static AppRoomDatabase getDatabase(final Context context) {
            if (INSTANCE == null) {
                synchronized (AppRoomDatabase.class) {
                    if (INSTANCE == null) {
                        INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                AppRoomDatabase.class, "tdam_database")
                                .addCallback(sRoomDatabaseCallback)
                                .build();
                    }
                }
            }
            return INSTANCE;
        }

        /**
         * Override the onCreate method to populate the database.
         * For this sample, we clear the database every time it is created.
         */
        private static RoomDatabase.Callback sRoomDatabaseCallback = new RoomDatabase.Callback() {
            @Override
            public void onCreate(@NonNull SupportSQLiteDatabase db) {
                super.onCreate(db);

                databaseWriteExecutor.execute(() -> {
                    // Populate the database in the background.
                    // If you want to start with more words, just add them.
                    AlbumDAO dao = INSTANCE.albumDao();
                    dao.deleteAll();


                });
            }
        };
    }


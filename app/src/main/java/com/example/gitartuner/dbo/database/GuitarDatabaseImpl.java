package com.example.gitartuner.dbo.database;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.room.Room;
import androidx.sqlite.db.SupportSQLiteOpenHelper;

import com.example.gitartuner.dbo.GuitarDao;
import com.example.gitartuner.dbo.GuitarTuningDao;
import com.example.gitartuner.dbo.NoteDao;

public class GuitarDatabaseImpl extends GuitarDatabase {
    private static GuitarDatabase INSTANCE;

    public static GuitarDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context, GuitarDatabase.class, "guitar_database")
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return INSTANCE;
    }


    @NonNull
    @Override
    protected SupportSQLiteOpenHelper createOpenHelper(DatabaseConfiguration config) {
        return null;
    }

    @NonNull
    @Override
    protected InvalidationTracker createInvalidationTracker() {
        return null;
    }

    @Override
    public void clearAllTables() {

    }

    @Override
    public GuitarDao getGuitarDao() {
        return null;
    }

    @Override
    public NoteDao getNoteDao() {
        return null;
    }

    @Override
    public GuitarTuningDao getGuitarTuningDao() {
        return null;
    }
}

package com.example.gitartuner.dbo;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteOpenHelper;

public class GuitarDatabaseImpl extends GuitarDatabase  {
    private static GuitarDatabase INSTANCE;

    public static GuitarDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context, GuitarDatabase.class, "guitar_database")
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
}

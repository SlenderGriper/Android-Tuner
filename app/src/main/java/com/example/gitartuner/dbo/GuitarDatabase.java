package com.example.gitartuner.dbo;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.gitartuner.dto.GuitarDto;

@Database(entities = {GuitarDto.class}, version = 1)
public abstract class GuitarDatabase extends RoomDatabase {
    public abstract GuitarDao getGuitarDao();
}
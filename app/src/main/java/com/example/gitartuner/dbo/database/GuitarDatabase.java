package com.example.gitartuner.dbo.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.gitartuner.dbo.GuitarDao;
import com.example.gitartuner.dbo.GuitarTuningDao;
import com.example.gitartuner.dbo.NoteDao;
import com.example.gitartuner.dto.GuitarDto;
import com.example.gitartuner.dto.GuitarTuningDto;
import com.example.gitartuner.dto.NoteDto;
import com.example.gitartuner.dto.NoteStorage;

@Database(entities = {GuitarDto.class, NoteDto.class, NoteStorage.class}, version = 4)
public abstract class GuitarDatabase extends RoomDatabase {
    public abstract GuitarDao getGuitarDao();
    public abstract NoteDao getNoteDao();
    public abstract GuitarTuningDao getGuitarTuningDao();

}
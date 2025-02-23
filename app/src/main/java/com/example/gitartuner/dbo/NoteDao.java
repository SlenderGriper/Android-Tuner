package com.example.gitartuner.dbo;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.gitartuner.dto.NoteDto;

import java.util.List;
import java.util.UUID;

@Dao
public interface NoteDao {

    @Insert
    void insert(NoteDto noteDto);

    @Update
    void update(NoteDto noteDto);

}
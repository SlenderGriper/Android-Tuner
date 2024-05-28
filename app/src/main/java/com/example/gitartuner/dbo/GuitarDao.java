package com.example.gitartuner.dbo;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.gitartuner.dto.GuitarDto;

import java.util.List;

@Dao
public interface GuitarDao {
    @Query("SELECT * FROM Guitar WHERE id = :id")
    GuitarDto getById(int id);
    @Insert(entity = GuitarDto.class)
    void insertGuitar(GuitarDto guitar);

    @Update
    void updateGuitar(GuitarDto guitar);

    @Delete
    void deleteGuitar(GuitarDto guitar);

    @Query("SELECT * FROM Guitar")
    List<GuitarDto> getAllGuitars();
}
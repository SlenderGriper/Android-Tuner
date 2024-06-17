package com.example.gitartuner.dbo;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import com.example.gitartuner.dto.NoteDto;
import com.example.gitartuner.dto.NoteStorage;

import java.util.List;
import java.util.UUID;

@Dao
public interface GuitarTuningDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertNote(NoteStorage note);

    @Delete
    void deleteNote(NoteStorage note);

    // Метод для получения всех нот
    @Query("SELECT * FROM GuitarTuning WHERE length = :length")
    List<NoteStorage> getAllNotes(int length);

    // Методы для работы со связями между NoteStorage и NoteDto

    @Query("SELECT * FROM Note where note_id = :userId")
    List<NoteDto> getAllNotesWithDetails(UUID userId);


}

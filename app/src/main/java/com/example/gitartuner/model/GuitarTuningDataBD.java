package com.example.gitartuner.model;

import com.example.gitartuner.dbo.GuitarTuningDao;
import com.example.gitartuner.dbo.NoteDao;
import com.example.gitartuner.dto.GuitarTuningDto;
import com.example.gitartuner.dto.NoteDto;
import com.example.gitartuner.dto.NoteStorage;
import com.example.gitartuner.model.inteface.NoteStorageTransfer;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class GuitarTuningDataBD {
    private NoteDao noteDao;
    private GuitarTuningDao guitarDao;
    ExecutorService executorService;
    NoteStorageTransfer transfer;

    public GuitarTuningDataBD(NoteDao noteDao, GuitarTuningDao guitarDao,NoteStorageTransfer transfer) {
        this.noteDao = noteDao;
        this.guitarDao = guitarDao;
         executorService = Executors.newCachedThreadPool();
         this.transfer=transfer;
    }
    public void getData(int length){

        executorService.execute(() -> {
            List<NoteStorage> guitarTuning;
            guitarTuning = guitarDao.getAllNotes(length);
            for (NoteStorage note:guitarTuning
            ) {
                NoteDto[] item=guitarDao.getAllNotesWithDetails(note.id).toArray(new NoteDto[]{});
                note.setNoteDto(item);
            }
            transfer.sendNoteStorage(guitarTuning);
        });
    }
    public void saveData(NoteStorage item){
        executorService.execute(() -> {
            guitarDao.insertNote(item);
            for (int i = 0; i < item.getLength(); i++) {
                NoteDto note = item.getNoteDto(i);
                note.note_id = item.id;
                noteDao.insert(note);
            }

        });
    }
    public void deleteData(NoteStorage item){
        executorService.execute(() -> {
            guitarDao.deleteNote(item);


        });
    }



}

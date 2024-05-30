package com.example.gitartuner.model.inteface;

import com.example.gitartuner.dto.NoteStorage;

import java.util.List;

public interface NoteStorageTransfer {
    void sendNoteStorage(List<NoteStorage> noteStorage);

}

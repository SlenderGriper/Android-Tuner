package com.example.gitartuner.controller;

import android.view.View;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gitartuner.R;
import com.example.gitartuner.dbo.GuitarTuningDao;
import com.example.gitartuner.dbo.NoteDao;
import com.example.gitartuner.dto.NoteStorage;
import com.example.gitartuner.model.GuitarTuningDataBD;
import com.example.gitartuner.model.NoteCalculator;
import com.example.gitartuner.model.adapter.NoteAdapter;
import com.example.gitartuner.model.adapter.NoteSpinnerAdapter;
import com.example.gitartuner.model.inteface.NoteStorageTransfer;

import java.util.List;

public class NoteDialogController implements NoteStorageTransfer {
    private View view;
    private NoteAdapter adapter;
    private NoteStorage itemList;
    private Spinner saveTune;
    private NoteSpinnerAdapter spinnerAdapter;
    private List<NoteStorage> noteStorage;

    public NoteDialogController(View view ) {
        this.view = view;

        saveTune=view.findViewById(R.id.saveTune);

    }
public void setAdapter(NoteStorage itemList,NoteAdapter adapter){
    this.itemList = itemList;
    this.adapter=adapter;
}
    private GuitarTuningDataBD controllerBD;
    public void setControllerBD(NoteDao noteDao, GuitarTuningDao guitarDao){
        controllerBD=new GuitarTuningDataBD(noteDao,guitarDao,this);
        controllerBD.getData(itemList.getLength());
    }

    public void sendNoteStorage(List<NoteStorage> noteStorage){

        this.noteStorage=noteStorage;
        spinnerAdapter = new NoteSpinnerAdapter(view.getContext(),noteStorage);
        saveTune.setAdapter(spinnerAdapter);
    }



    public void stringNote(Spinner tuning,Spinner note){
        int typesId=tuning.getSelectedItemPosition();
        int firstNote=note.getSelectedItemPosition();
        NoteCalculator noteCalculator=new NoteCalculator();

        NoteStorage newItemList= noteCalculator.getStringNote(typesId,firstNote,itemList.getLength());
        itemList.update(newItemList);
        adapter.notifyDataSetChanged();
    }
    public void delete(){
        int id=saveTune.getSelectedItemPosition();
        if(id==-1)return;
        NoteStorage item=spinnerAdapter.getItem(id);
        controllerBD.deleteData(item);
        noteStorage.remove(item);

        spinnerAdapter.notifyDataSetChanged();
    }
    public void load(){
        int id=saveTune.getSelectedItemPosition();
        if(id==-1)return;
        NoteStorage item=spinnerAdapter.getItem(id);
        itemList.update(item);
        adapter.notifyDataSetChanged();
    }
    public void save(){
        TextView name= view.findViewById(R.id.name);
        String nameText=name.getText().toString();
        if(nameText.isEmpty())return;
        NoteStorage item=new NoteStorage(itemList);
        item.setName(nameText);
        controllerBD.saveData(item);
        noteStorage.add(item);

        spinnerAdapter.notifyDataSetChanged();
        name.setText("");
    }
}

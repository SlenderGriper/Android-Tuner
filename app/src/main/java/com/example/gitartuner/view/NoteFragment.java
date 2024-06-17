package com.example.gitartuner.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentResultListener;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gitartuner.R;
import com.example.gitartuner.controller.NoteDialogController;
import com.example.gitartuner.dbo.GuitarTuningDao;
import com.example.gitartuner.dbo.NoteDao;
import com.example.gitartuner.dbo.database.GuitarDatabase;
import com.example.gitartuner.dbo.database.GuitarDatabaseImpl;
import com.example.gitartuner.dto.NoteStorage;
import com.example.gitartuner.model.adapter.NoteAdapter;

public class NoteFragment extends Fragment {

    private NoteStorage itemList;

    private NoteDialogController controller;
private int length;
    public NoteFragment(){
        super(R.layout.note_dialog);
        length=6;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState){
        length=getArguments().getInt("key");
        itemList = new NoteStorage(length);
        controller=new NoteDialogController(view);

        NoteAdapter adapter = new NoteAdapter(itemList);

        LinearLayoutManager layoutManager = new LinearLayoutManager(view.getContext());
        layoutManager.setStackFromEnd(true);

        RecyclerView recyclerView = view.findViewById(R.id.gitarList);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(layoutManager);
        controller.setAdapter(itemList,adapter);

        Spinner note = view.findViewById(R.id.note);
        Spinner tuning = view.findViewById(R.id.tuning);
        tuning.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                controller.stringNote(tuning,note);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });


        note.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                controller.stringNote(tuning,note);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });

        ImageButton back =view.findViewById(R.id.back);
        back.setOnClickListener(v -> {
            Bundle bundle = new Bundle();
            bundle.putSerializable("note",  getData());
            getParentFragmentManager().setFragmentResult("noteKey", bundle);


            getParentFragmentManager().popBackStack("main", FragmentManager.POP_BACK_STACK_INCLUSIVE);
        });


        GuitarDatabase db= GuitarDatabaseImpl.getInstance(view.getContext());
        GuitarTuningDao guitarDao = db.getGuitarTuningDao();
        NoteDao noteDao=db.getNoteDao();
        controller.setControllerBD(noteDao,guitarDao);



        ImageButton delete= view.findViewById(R.id.delete);
        delete.setOnClickListener(v -> {
            controller.delete();
        });
        ImageButton load= view.findViewById(R.id.load);
        load.setOnClickListener(v -> {
            controller.load();
        });


        ImageButton save= view.findViewById(R.id.save);
        save.setOnClickListener(v -> {
            controller.save();
        });
    }

    public NoteStorage getData() {
        return itemList;
    }
}

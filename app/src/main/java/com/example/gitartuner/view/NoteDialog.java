//package com.example.gitartuner.view;
//
//import android.view.View;
//import android.widget.AdapterView;
//import android.widget.ImageButton;
//import android.widget.Spinner;
//
//import androidx.recyclerview.widget.LinearLayoutManager;
//import androidx.recyclerview.widget.RecyclerView;
//
//import com.example.gitartuner.R;
//import com.example.gitartuner.controller.NoteDialogController;
//import com.example.gitartuner.dbo.GuitarTuningDao;
//import com.example.gitartuner.dbo.NoteDao;
//import com.example.gitartuner.dbo.database.GuitarDatabase;
//import com.example.gitartuner.dbo.database.GuitarDatabaseImpl;
//import com.example.gitartuner.dto.NoteStorage;
//import com.example.gitartuner.model.adapter.NoteAdapter;
//
//public class NoteDialog {
//    private View view;
//
//    private NoteStorage itemList;
//
//    NoteDialogController controller;
//
//    public NoteDialog(View view) {
//        this.view = view;
//
//
//    }
//
//    public void initial(int length) {
//
//        itemList = new NoteStorage(length);
//
//        controller=new NoteDialogController(view);
//
//        NoteAdapter  adapter = new NoteAdapter(itemList);
//
//        LinearLayoutManager layoutManager = new LinearLayoutManager(view.getContext());
//        layoutManager.setStackFromEnd(true);
//
//        RecyclerView recyclerView = view.findViewById(R.id.gitarList);
//        recyclerView.setAdapter(adapter);
//        recyclerView.setLayoutManager(layoutManager);
//        controller.setAdapter(itemList,adapter);
//
//        Spinner note = view.findViewById(R.id.note);
//        Spinner tuning = view.findViewById(R.id.tuning);
//        tuning.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
//                controller.stringNote(tuning,note);
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parentView) {
//                // your code here
//            }
//
//        });
//
//
//        note.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
//                controller.stringNote(tuning,note);
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parentView) {
//                // your code here
//            }
//
//        });
//
//
//
//        GuitarDatabase db= GuitarDatabaseImpl.getInstance(view.getContext());
//        GuitarTuningDao guitarDao = db.getGuitarTuningDao();
//        NoteDao noteDao=db.getNoteDao();
//        controller.setControllerBD(noteDao,guitarDao);
//
//
//
//        ImageButton delete= view.findViewById(R.id.delete);
//        delete.setOnClickListener(v -> {
//            controller.delete();
//        });
//        ImageButton load= view.findViewById(R.id.load);
//         load.setOnClickListener(v -> {
//             controller.load();
//         });
//
//
//        ImageButton save= view.findViewById(R.id.save);
//         save.setOnClickListener(v -> {
//             controller.save();
//         });
//    }
//    public NoteStorage getData() {
//        return itemList;
//    }
//
//
//}

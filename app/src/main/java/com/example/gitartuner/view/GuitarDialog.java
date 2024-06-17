//package com.example.gitartuner.view;
//
//import android.view.View;
//import android.widget.Button;
//import android.widget.EditText;
//
//import androidx.recyclerview.widget.LinearLayoutManager;
//import androidx.recyclerview.widget.RecyclerView;
//
//import com.example.gitartuner.R;
//import com.example.gitartuner.controller.GitarDialogController;
//import com.example.gitartuner.dbo.GuitarDao;
//import com.example.gitartuner.dbo.database.GuitarDatabase;
//import com.example.gitartuner.dbo.database.GuitarDatabaseImpl;
//import com.example.gitartuner.dto.GuitarDto;
//import com.example.gitartuner.model.adapter.GitarAdapter;
//
//import java.util.ArrayList;
//import java.util.List;
//
//public class GuitarDialog {
//
//
//
//    private RecyclerView recyclerView;
//    private Button addGitar;
//    private View view;
//    GuitarDatabase db;
//    GuitarDao guitarDao;
//    GitarDialogController controller;
//    public GuitarDialog(View view){
//        this.view =view;
//        recyclerView = view.findViewById(R.id.gitarList);
//        controller=new GitarDialogController();
//        db=GuitarDatabaseImpl.getInstance(view.getContext());
//        guitarDao = db.getGuitarDao();
//        controller.setDao(guitarDao);
//
//
//
//
//
//    }
//    public void Initial(){
//
//        List<GuitarDto> itemList = new ArrayList<>();
//        GitarAdapter adapter = new GitarAdapter(itemList,guitarDao);
//
//        LinearLayoutManager layoutManager = new LinearLayoutManager(view.getContext());
//        layoutManager.setStackFromEnd(true);
//
//        recyclerView.setAdapter(adapter);
//        recyclerView.setLayoutManager(layoutManager);
//        controller.setAdapter(adapter,itemList);
//
//        addGitar=view.findViewById(R.id.addGitar);
//        EditText name=view.findViewById(R.id.nameTextEdit);
//        EditText stringCount=view.findViewById(R.id.stringCountTextEdit);
//        addGitar.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                controller.addGitar(name,stringCount);
//
//            }
//        });
//    }
//
//
//    public  GuitarDto getData(){
//
//        return controller.getData();
//    }
//
//}

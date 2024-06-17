package com.example.gitartuner.view;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gitartuner.R;
import com.example.gitartuner.controller.GitarDialogController;
import com.example.gitartuner.dbo.GuitarDao;
import com.example.gitartuner.dbo.database.GuitarDatabase;
import com.example.gitartuner.dbo.database.GuitarDatabaseImpl;
import com.example.gitartuner.dto.GuitarDto;
import com.example.gitartuner.model.adapter.GitarAdapter;

import java.util.ArrayList;
import java.util.List;

public class GuitarFragment extends Fragment {

    public GuitarFragment(){
        super(R.layout.gitar_dialog);

    }

    private Button addGitar;
    private GitarDialogController controller;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState){


        controller=new GitarDialogController(getContext());
        RecyclerView recyclerView=view.findViewById(R.id.gitarList);
        controller.setRecyclerView(recyclerView,getActivity());
        addGitar=view.findViewById(R.id.addGitar);
        EditText name=view.findViewById(R.id.nameTextEdit);
        EditText stringCount=view.findViewById(R.id.stringCountTextEdit);
        addGitar.setOnClickListener(v -> controller.addGitar(name,stringCount));

        ImageButton back =view.findViewById(R.id.back);
        back.setOnClickListener(v -> {
            GuitarDto guitarDto = getData();
            if (guitarDto != null){
                Bundle bundle = new Bundle();
                bundle.putSerializable("guitar",  guitarDto);
                getParentFragmentManager().setFragmentResult("guitarKey", bundle);
            }
            getParentFragmentManager().popBackStack("main", FragmentManager.POP_BACK_STACK_INCLUSIVE);
        });


    }


    public  GuitarDto getData(){

        return controller.getData();
    }
}

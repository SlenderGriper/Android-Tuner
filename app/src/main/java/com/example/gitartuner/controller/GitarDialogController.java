package com.example.gitartuner.controller;

import android.app.Activity;
import android.content.Context;
import android.widget.EditText;

import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gitartuner.R;
import com.example.gitartuner.dbo.GuitarDao;
import com.example.gitartuner.dbo.database.GuitarDatabase;
import com.example.gitartuner.dbo.database.GuitarDatabaseImpl;
import com.example.gitartuner.dto.GuitarDto;
import com.example.gitartuner.model.GuitarDataBD;
import com.example.gitartuner.model.adapter.GitarAdapter;
import com.example.gitartuner.model.inteface.AdapterUpdate;
import com.example.gitartuner.model.inteface.CalibrationOpener;
import com.example.gitartuner.view.CalibrationFragment;
import com.example.gitartuner.view.NoteFragment;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class GitarDialogController implements AdapterUpdate, CalibrationOpener {
    private GitarAdapter adapter;
    private List<GuitarDto> itemList ;
    private GuitarDataBD controllerBD;
    private Context context;

    private   Activity activity;

    public  GitarDialogController(Context context) {
        this.context = context;
    }

    public void addGitar(EditText name, EditText stringCount){
        String countText=stringCount.getText().toString();
        String guitarText=name.getText().toString();
        GuitarDto item=new GuitarDto(auntificatorGuitar(guitarText),auntificatorCount(countText));
        itemList.add(item);
        adapter.notifyItemInserted(itemList.size() - 1);
        controllerBD.AddData(item);
    }

    public  GuitarDto getData(){
        int selected=adapter.getSelected();
        if(selected==-1)return null;
        return itemList.get(selected);
    }
        public void setRecyclerView(RecyclerView recyclerView, Activity activity){
            this.activity=activity;
            GuitarDatabase db= GuitarDatabaseImpl.getInstance(context);

            GuitarDao guitarDao = db.getGuitarDao();

            controllerBD=new GuitarDataBD(guitarDao);
            itemList = new ArrayList<>();
            adapter = new GitarAdapter(itemList,guitarDao);
            adapter.setOpener(this);

            controllerBD.getData(itemList,this);

            LinearLayoutManager layoutManager = new LinearLayoutManager(context);
            layoutManager.setStackFromEnd(true);

            recyclerView.setAdapter(adapter);
            recyclerView.setLayoutManager(layoutManager);


    }
    public void adapterUpdate(){

        activity.runOnUiThread(new Runnable() {

            @Override
            public void run() {
                adapter.notifyDataSetChanged();
            }
        });

    }

    private String auntificatorGuitar(String guitarText){
        if(guitarText.isEmpty())return "Guitar";
        return  guitarText;
    }
    private int auntificatorCount(String countText){
        if(countText.isEmpty())return 6;

        int count=Integer.parseInt(countText);
        if(count<3)count=3;
        else if(count>12)return 12;
        return count;
    }

    @Override
    public void startFragment(int id) {
        FragmentManager fragmentManager = ((FragmentActivity)activity).getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        fragmentTransaction.replace(R.id.fragment_container_view, new CalibrationFragment());
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }
}

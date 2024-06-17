package com.example.gitartuner.model.adapter;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gitartuner.R;
import com.example.gitartuner.dbo.GuitarDao;
import com.example.gitartuner.dbo.database.GuitarDatabase;
import com.example.gitartuner.dto.GuitarDto;
import com.example.gitartuner.model.inteface.CalibrationOpener;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class GitarAdapter extends RecyclerView.Adapter<GitarAdapter.ViewHolder> {

    private List<GuitarDto> itemList;
    private int selected;
    private CalibrationOpener opener;
    GuitarDao guitarDao;


    ExecutorService executorService = Executors.newSingleThreadExecutor();
    public GitarAdapter(List<GuitarDto> items,GuitarDao guitarDao) {
        itemList = items;
        this.guitarDao = guitarDao;
        selected=-1;
    }

    public void setOpener(CalibrationOpener opener) {
        this.opener = opener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_gitar, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        String item = itemList.get(position).name + " ("+ itemList.get(position).stringsCount+")";
        holder.textView.setText(item);
        if(selected==position) holder.layout.setBackgroundColor(Color.GRAY);
        else holder.layout.setBackgroundColor(Color.WHITE);
        holder.selectButton.setOnClickListener(v -> {
            int oldSelected=selected;

            selected=position;
            notifyItemChanged(oldSelected);
            notifyItemChanged(position);
        });
        holder.deleteButton.setOnClickListener(v -> {
            if(position==selected)selected=-1;

            int newPosition=position;
            if(itemList.size()==0)return;
            if(position>=itemList.size())newPosition=itemList.size()-1;

            GuitarDto item1 =itemList.get(newPosition);
            itemList.remove(newPosition);
            notifyItemRemoved(position);


            executorService.execute(() -> {
                guitarDao.deleteGuitar(item1);
            });
        });
        holder.calibrationButton.setOnClickListener(v -> {
            if(opener!=null)
                opener.startFragment(itemList.get(position).id);

        });
    }

    public int getSelected(){return selected;}
    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView textView;
        ImageView deleteButton;
        LinearLayout layout;

        ImageView selectButton;
        ImageView calibrationButton;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            layout=itemView.findViewById(R.id.layout);
            calibrationButton=itemView.findViewById(R.id.CalibrationButton);
            selectButton=itemView.findViewById(R.id.selectButton);
            textView = itemView.findViewById(R.id.gitarTemplate);
            deleteButton = itemView.findViewById(R.id.deleteButton);

        }
    }
}
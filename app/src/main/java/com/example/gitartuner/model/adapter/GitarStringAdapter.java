package com.example.gitartuner.model.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.gitartuner.R;

import java.util.ArrayList;

public class GitarStringAdapter extends ArrayAdapter<String> {
    private Context context;
    private ArrayList<String> itemList;
    public GitarStringAdapter(@NonNull Context context, ArrayList<String> itemList) {
        super(context,0, itemList);

        this.context=context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.list_item_layout, parent, false);
        }

        TextView bookingNameTextView = convertView.findViewById(R.id. stringCount);
        TextView inDateTextView = convertView.findViewById(R.id.stringChose);

        String item = itemList.get(position);

        bookingNameTextView.setText("1");
        inDateTextView.setText(item);

        return convertView;
    }
}

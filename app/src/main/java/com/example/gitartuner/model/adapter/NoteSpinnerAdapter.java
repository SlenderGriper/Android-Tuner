package com.example.gitartuner.model.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.gitartuner.R;
import com.example.gitartuner.dto.NoteStorage;

import java.util.List;

public class NoteSpinnerAdapter extends BaseAdapter {
    private List<NoteStorage> noteList;

    public NoteSpinnerAdapter(Context context, List<NoteStorage> list) {
        this.noteList = list;
    }
    @Override
    public int getCount() {
        return noteList.size();
    }
    @Override
    public NoteStorage getItem(int position) {
        return noteList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public int getViewTypeCount() {
        return 1;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        NoteStorage note = getItem(position);

        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            convertView = inflater.inflate(R.layout.spinner_item, parent, false);
        }

        TextView textView = convertView.findViewById(R.id.spinner_text);
        textView.setText(note.getName());

        return convertView;
    }
}
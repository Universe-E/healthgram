package com.example.gp.Adapter;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/**
 * NotificationRecycleView Adapter Class
 * The class to handle with data adapter and refresh on layout
 * Author: Xingchen Zhang
 * Date: 2024-05-06
 */

public class NotificationRecycleViewAdapter  extends RecyclerView.Adapter<NoteRecyclerViewAdapter.ViewHolder> {



    @NonNull
    @Override
    public NoteRecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull NoteRecyclerViewAdapter.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }
}

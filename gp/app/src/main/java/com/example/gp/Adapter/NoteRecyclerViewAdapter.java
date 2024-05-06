package com.example.gp.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.gp.R;
import com.example.gp.data.UserData;

import java.util.List;

public class NoteRecyclerViewAdapter extends RecyclerView.Adapter<NoteRecyclerViewAdapter.ViewHolder> {

    private List<UserData.Note> notes;

    public NoteRecyclerViewAdapter(List<UserData.Note> notes) {
        this.notes = notes;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View noteView = inflater.inflate(R.layout.content_note, parent, false);
        return new ViewHolder(noteView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        UserData.Note note = notes.get(position);
        holder.bind(note);
        holder.itemView.setOnClickListener(v -> {
            if (listener != null) {
                listener.onItemClick(note);
            }
        });
    }

    public void updateNotes(List<UserData.Note> newNotes) {
        notes = newNotes;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return notes.size();
    }

    private OnItemClickListener listener;

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public interface OnItemClickListener {
        void onItemClick(UserData.Note note);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageView;
        private TextView nameView;
        private TextView descriptionView;

        public ViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.image);
            nameView = itemView.findViewById(R.id.name);
            descriptionView = itemView.findViewById(R.id.description);
        }

        public void bind(UserData.Note note) {
            nameView.setText(note.name);
            descriptionView.setText(note.description);
            if (note.image != null) {
                imageView.setImageBitmap(note.image);
            }
        }
    }
}

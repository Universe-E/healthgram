package com.example.gp.Adapter;

import android.content.Context;
import android.view.ViewGroup;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.gp.R;
import com.example.gp.data.UserData;

import java.util.List;

public class NoteRecyclerViewAdapter extends RecyclerView.Adapter<NoteRecyclerViewAdapter.ViewHolder> {

    private List<UserData.Note> values;

    public NoteRecyclerViewAdapter(List<UserData.Note> values) {
        this.values = values;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View view = inflater.inflate(R.layout.content_note, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // Get the data model based on position
        UserData.Note item = values.get(position);

        // Set item views based on the views and data model
        holder.nameView.setText(item.name);
        holder.descriptionView.setText(item.description);

        if (item.image != null) {
            holder.imageView.setImageBitmap(item.image);
        }
    }

    @Override
    public int getItemCount() {
        return values.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView nameView;
        TextView descriptionView;

        public ViewHolder (View itemView) {
            super(itemView);
            // ViewHolder should contain references to all of the views, so they can be accessed easily
            imageView = itemView.findViewById(R.id.image);
            nameView = itemView.findViewById(R.id.name);
            descriptionView = itemView.findViewById(R.id.description);
        }
    }
}

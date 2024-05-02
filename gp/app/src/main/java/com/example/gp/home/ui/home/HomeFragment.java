package com.example.gp.home.ui.home;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gp.R;
import com.example.gp.data.Note;
import com.example.gp.data.NotesAdapter;
import com.example.gp.databinding.FragmentHomeBinding;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.content_main, container, false); // 确保引用正确的布局文件

        List<Note> notes = new ArrayList<>();
        notes.add(new Note("Note 1", "Description 1", R.drawable.ic_launcher_background));
        notes.add(new Note("Note 2", "Description XXXX", R.drawable.ic_launcher_background));

        RecyclerView recyclerView = view.findViewById(R.id.item_list); // 确保 ID 正确
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(new NotesAdapter(notes)); // 确保适配器已正确设置

        return view;
    }



    private void performSearch(String query) {
        // Implement search logic here:
        // For example, update a list or query a database or a API
        Log.d("SearchQuery", "Searching for: " + query);
    }
}
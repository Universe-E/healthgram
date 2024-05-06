package com.example.gp.home.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gp.R;
import com.example.gp.Activity_note_detail;
import com.example.gp.data.UserData;
import com.example.gp.data.UserData.Note;
import com.example.gp.Adapter.NoteRecyclerViewAdapter;
import com.google.android.material.search.SearchView;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    private NoteRecyclerViewAdapter adapter;
    private SearchView searchView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        // Initialize SearchView
        View searchLayout = view.findViewById(R.id.search_layout);
        searchView = searchLayout.findViewById(R.id.search_view);

        // Initialize RecyclerView
        RecyclerView recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new NoteRecyclerViewAdapter(new ArrayList<>());
        recyclerView.setAdapter(adapter);

        // Set click listener to navigate to detail page
        adapter.setOnItemClickListener(note -> openDetailActivity(note));

        // Add initial data
        addInitialNotes();

        // Observe notes in UserData
        UserData.notes().observe(getViewLifecycleOwner(), notes -> {
            adapter.updateNotes(notes); // Update adapter with new data
        });

        // Setup SearchView listener
        setupSearchViewListener();

        return view;
    }

    // Set up SearchView listener
    private void setupSearchViewListener() {
        // 使用 TextWatcher 监听输入框的内容变化
        searchView.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                performSearch(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });
    }


    // Search function to filter notes based on query
    private void performSearch(String query) {
        List<Note> filteredNotes = new ArrayList<>();
        for (Note note : UserData.getNotes()) {
            if (note.name.toLowerCase().contains(query.toLowerCase()) || note.description.toLowerCase().contains(query.toLowerCase())) {
                filteredNotes.add(note);
            }
        }
        adapter.updateNotes(filteredNotes);  // 更新适配器数据并刷新 RecyclerView
    }

    // Add some example data
    private void addInitialNotes() {
        UserData.addNote(new Note("1", "Europe Cup Champions", "Sport News"));
        UserData.addNote(new Note("2", "Game", "WOW Game Strategy"));
        UserData.addNote(new Note("3", "WTF NO.375", "COC"));
        UserData.addNote(new Note("4", "Australia news", "Canberra parliament"));
    }

    // Launch detail activity
    private void openDetailActivity(Note note) {
        Intent intent = new Intent(getContext(), Activity_note_detail.class);
        intent.putExtra("title", note.name);
        intent.putExtra("description", note.description);
        intent.putExtra("imageResId", note.imageName);
        startActivity(intent);
    }
}


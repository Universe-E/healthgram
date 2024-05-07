package com.example.gp.home.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gp.Activity_note_detail;
import com.example.gp.Adapter.NoteRecyclerViewAdapter;
import com.example.gp.R;
import com.example.gp.data.UserData;
import com.example.gp.data.UserData.Note;
import com.example.gp.interaction.NewPostActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.search.SearchView;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    private NoteRecyclerViewAdapter adapter;
    private SearchView searchView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        initializeSearchView(view);
        initializeRecyclerView(view);
        addInitialNotes();
        observeNotes();
        setupAddNoteButton(view);

        return view;
    }

    private void initializeSearchView(View view) {
        View searchLayout = view.findViewById(R.id.search_layout);
        searchView = searchLayout.findViewById(R.id.search_view);
        searchView.getEditText().addTextChangedListener(new SearchViewTextWatcher());
    }

    private void initializeRecyclerView(View view) {
        RecyclerView recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new NoteRecyclerViewAdapter(new ArrayList<>());
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(this::openDetailActivity);
    }

    private void addInitialNotes() {
        UserData.addNote(new Note("1", "Europe Cup Champions", "Sport News"));
        UserData.addNote(new Note("2", "Game", "WOW Game Strategy"));
        UserData.addNote(new Note("3", "WTF NO.375", "COC"));
        UserData.addNote(new Note("4", "Australia news", "Canberra parliament"));
    }

    private void observeNotes() {
        UserData.notes().observe(getViewLifecycleOwner(), notes -> {
            adapter.updateNotes(notes);
        });
    }

    private void setupAddNoteButton(View view) {
        FloatingActionButton fabAddNote = view.findViewById(R.id.fab_add_note);
        fabAddNote.setOnClickListener(v -> openAddNoteActivity());
    }

    private void performSearch(String query) {
        List<Note> filteredNotes = new ArrayList<>();
        for (Note note : UserData.getNotes()) {
            if (note.name.toLowerCase().contains(query.toLowerCase()) ||
                    note.description.toLowerCase().contains(query.toLowerCase())) {
                filteredNotes.add(note);
            }
        }
        adapter.updateNotes(filteredNotes);
    }

    private void openDetailActivity(Note note) {
        Intent intent = new Intent(getContext(), Activity_note_detail.class);
        intent.putExtra("title", note.name);
        intent.putExtra("description", note.description);
        intent.putExtra("imageResId", note.imageName);
        startActivity(intent);
    }

    private void openAddNoteActivity() {
        Intent intent = new Intent(getContext(), NewPostActivity.class);
        startActivity(intent);
    }

    private class SearchViewTextWatcher implements android.text.TextWatcher {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            performSearch(s.toString());
        }

        @Override
        public void afterTextChanged(android.text.Editable s) {
        }
    }
}
package com.example.gp.home.ui.home;


import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gp.R;
import com.example.gp.data.Note;
import com.example.gp.data.NotesAdapter;
import com.example.gp.databinding.FragmentHomeBinding;
import com.example.gp.data.NotesAdapter;
import com.google.android.material.search.SearchBar;
import com.example.gp.SearchActivity;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    private  NotesAdapter adapter;
    private SearchBar searchBar;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false); // 确保引用正确的布局文件
        // 在包含的 activity_search.xml 中找到 SearchBar
        View searchLayout = view.findViewById(R.id.search_layout);
        searchBar = searchLayout.findViewById(R.id.search_bar);

        // 检查 searchBar 是否成功找到
        if (searchBar != null) {
            Log.d("DashboardFragment", "成功加载 SearchBar");
            searchBar.setOnClickListener(v -> openSearchActivity());
        } else {
            Log.e("DashboardFragment", "未找到 SearchBar");
        }
//        // 初始化笔记列表content_note
//        List<Note> notes = new ArrayList<>();
//        notes.add(new Note("Note 1", "Description 1", R.drawable.ic_launcher_background));
//        notes.add(new Note("Note 2", "Description XXXX", R.drawable.ic_launcher_background));
//
//        // 初始化 RecyclerView
//        RecyclerView recyclerView = view.findViewById(R.id.recycler_view);
//        if (recyclerView == null) {
//            Log.e("HomeFragment", "RecyclerView not found");
//        }
//
//        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
//        adapter = new NotesAdapter(notes);
//        recyclerView.setAdapter(adapter);
//
//        // 初始化 SearchView
//        SearchView searchView = view.findViewById(R.id.search_view);
//        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
//            @Override
//            public boolean onQueryTextSubmit(String query) {
//                performSearch(query);
//                return false;
//            }
//
//            @Override
//            public boolean onQueryTextChange(String newText) {
////                adapter.getFilter().filter(newText); // 确保适配器有实现过滤功能
//                return false;
//            }
//        });

        return view;
    }

    private void openSearchActivity() {

    }


    private void performSearch(String query) {
        // Implement search logic here:
        // For example, update a list or query a database or a API
        Log.d("SearchQuery", "Searching for: " + query);
    }
}
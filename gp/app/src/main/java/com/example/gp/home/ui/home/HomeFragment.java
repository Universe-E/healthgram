package com.example.gp.home.ui.home;// HomeFragment.java
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.util.Log;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.gp.R;
import com.example.gp.data.Activity_note_detail;
import com.example.gp.data.UserData;
import com.example.gp.data.UserData.Note;
import com.example.gp.Adapter.NoteRecyclerViewAdapter;
import com.google.android.material.search.SearchBar;

import java.util.ArrayList;

public class HomeFragment extends Fragment {

    private NoteRecyclerViewAdapter adapter;
    private SearchBar searchBar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // 加载 fragment_home 布局
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        // 初始化 SearchBar
        View searchLayout = view.findViewById(R.id.search_layout);
        searchBar = searchLayout.findViewById(R.id.search_bar);

        // 检查 searchBar 是否成功找到
        if (searchBar != null) {
            Log.d("HomeFragment", "成功加载 SearchBar");
            searchBar.setOnClickListener(v -> openSearchActivity());
        } else {
            Log.e("HomeFragment", "未找到 SearchBar");
        }

        // 初始化 RecyclerView
        RecyclerView recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // 设置适配器并初始化为空列表
        adapter = new NoteRecyclerViewAdapter(new ArrayList<>());
        recyclerView.setAdapter(adapter);

        // 设置点击监听器来跳转到详情页面
        adapter.setOnItemClickListener(note -> openDetailActivity(note));

        // 在此处直接添加一些数据
        addInitialNotes();

        // 观察 UserData 中的笔记
        UserData.notes().observe(getViewLifecycleOwner(), notes -> {
            adapter.values = notes;
            adapter.notifyDataSetChanged();
        });

        return view;
    }

    // add some example data
    private void addInitialNotes() {
        UserData.addNote(new Note("1", "Note 1", "Sport News"));
        UserData.addNote(new Note("2", "Note 2", "WOW Game Strategy"));
        UserData.addNote(new Note("3", "Note 3", "Description XXXX"));
        UserData.addNote(new Note("4", "Note 4", "Description YYYY"));
    }

    private void openSearchActivity() {
        // 实现打开 SearchActivity 的逻辑
    }

    private void openDetailActivity(Note note) {
        Intent intent = new Intent(getContext(), Activity_note_detail.class);
        intent.putExtra("title", note.name);
        intent.putExtra("description", note.description);
        // 假设图片是资源 ID
        intent.putExtra("imageResId", note.imageName);
        startActivity(intent);
    }
}

package com.example.gp.home.ui.Friend;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import com.example.gp.R;
import com.example.gp.SearchActivity;
import com.google.android.material.search.SearchBar;

/**
 * 登录成功跳转的主界面
 * 显示:头像 好友列表
 */
public class DashboardFragment extends Fragment {

    private SearchBar searchBar;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        // 加载 fragment_dashboard.xml 布局
        View view = inflater.inflate(R.layout.fragment_dashboard, container, false);

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

        return view;
    }

    private void openSearchActivity() {
        Intent searchIntent = new Intent(getContext(), SearchActivity.class);
        if (searchBar != null) {
            String initialQuery = searchBar.getText().toString(); // 从搜索栏获取文本
            searchIntent.putExtra("QUERY", initialQuery);
        }
        startActivity(searchIntent);
    }
}

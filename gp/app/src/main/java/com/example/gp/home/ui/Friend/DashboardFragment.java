package com.example.gp.home.ui.Friend;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.EditText;
import android.widget.ImageButton;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.gp.R;
import com.example.gp.databinding.FragmentDashboardBinding;
/*
* 登录成功跳转的主界面
* 显示:头像 好友列表
*
* */
public class DashboardFragment extends Fragment {

    private FragmentDashboardBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.fragment_dashboard, container, false);

        EditText searchBox = view.findViewById(R.id.search_box);
        ImageButton searchButton = view.findViewById(R.id.search_button);
//        DashboardViewModel dashboardViewModel =
//                new ViewModelProvider(this).get(DashboardViewModel.class);
//
//        binding = FragmentDashboardBinding.inflate(inflater, container, false);
//        View root = binding.getRoot();
//
//        final TextView textView = binding.textDashboard;
//        dashboardViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
//        return root;
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String searchText = searchBox.getText().toString().trim();
                performSearch(searchText);
            }
        });

        return view;
    }

    private void performSearch(String query) {
        // TODO: Implement search logic here:
        // For example, update a list or query a database or a API
        Log.d("SearchQuery", "Searching for: " + query);
    }
}
package com.example.gp.data.databaseTest;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;

import com.example.gp.Items.Post;
import com.example.gp.data.Database;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.example.gp.R;
import com.example.gp.databinding.ActivityDatabaseTestBinding;

public class DatabaseTestActivity extends AppCompatActivity {
    private static final String TAG = "DatabaseTestActivity";

    private ActivityDatabaseTestBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityDatabaseTestBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Toolbar toolbar = binding.toolbar;
        setSupportActionBar(toolbar);
        CollapsingToolbarLayout toolBarLayout = binding.toolbarLayout;
        toolBarLayout.setTitle(getTitle());

        FloatingActionButton fab = binding.fab;
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ImageView imageView = binding.imageView;
                imageView.setImageResource(R.mipmap.sample_avatar_1);
                Bitmap bitmap = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
                Post post = new Post("post content", "post title", bitmap, true);
                Database.savePostData(post, DatabaseTestActivity.this, "savePostData");
            }
        });
    }

    public void saveImage(String uuid) {
        Log.d(TAG, "uuid: " + uuid);
    }
}
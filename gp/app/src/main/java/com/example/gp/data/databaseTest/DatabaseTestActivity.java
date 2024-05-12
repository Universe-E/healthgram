package com.example.gp.data.databaseTest;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
import android.widget.Button;
import android.widget.ImageView;

import com.example.gp.R;
import com.example.gp.databinding.ActivityDatabaseTestBinding;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

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
                imageView.setVisibility(View.INVISIBLE);
                imageView.setImageResource(R.mipmap.sample_avatar_1);
                Bitmap bitmap = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
                Post post = new Post("post content", "post title", bitmap, true);
                Database.savePostData(post, DatabaseTestActivity.this, "saveImage");
            }
        });

        Button button = binding.button3;
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String fileName = "06202bd3-b103-4f2e-920e-170173398c5e.jpg";
                StorageReference storageRef = FirebaseStorage.getInstance().getReference();
                StorageReference islandRef = storageRef.child("image/" + fileName);
                final long ONE_MEGABYTE = 1024 * 1024;
                islandRef.getBytes(ONE_MEGABYTE).addOnSuccessListener(bytes -> {
                    Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                    ImageView imageView = binding.imageView;
                    imageView.setImageBitmap(bitmap);
                }).addOnFailureListener(exception -> {
                    // Handle any errors
                    Log.e(TAG, "Error: " + exception);
                });
            }
        });
    }

    public void saveImage(boolean isSuccessful, Post post) {
        ImageView imageView = binding.imageView;
        imageView.setVisibility(View.VISIBLE);
        Log.d(TAG, "uuid: " + post.getImgUUID());
    }


}
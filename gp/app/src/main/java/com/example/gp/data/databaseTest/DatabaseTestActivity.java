package com.example.gp.data.databaseTest;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;

import com.example.gp.Items.Friend;
import com.example.gp.Items.Post;
import com.example.gp.data.Database;
import com.example.gp.data.database.PostDB;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.gp.R;
import com.example.gp.databinding.ActivityDatabaseTestBinding;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.SetOptions;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.List;
import java.util.Map;

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

        Button button2 = binding.button2;
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseFirestore db = FirebaseFirestore.getInstance();
                db.collection("users")
                        .whereEqualTo("username", "tset")
                        .get()
                        .addOnCompleteListener(task -> {
                            if (task.isSuccessful()) {
                                Log.d(TAG, String.valueOf(task.getResult().isEmpty()));
                            }
                        });
            }
        });

        Button button4 = binding.button4;
        button4.setOnClickListener(listener -> {
            Database.getUserPost(null, 0, DatabaseTestActivity.this, "getUserPost");
        });

        Button button5 = binding.button5;
        button5.setOnClickListener(listener -> {
            Friend friend = new Friend("3", "Samuel", 2);
            FirebaseFirestore db = FirebaseFirestore.getInstance();
            db.collection("Test1")
                    .document("1")
                    .set(Map.of("friendMap", Map.of(friend.getId(), friend)));
        });

        Button button6 = binding.button6;
        button6.setOnClickListener(listener -> {
            Database.likePost("2XCQErLKawEjQaR0vCrv", null, null);
        });
    }

    public void getUserPost(boolean isSuccessful, Object object) {
        Log.d(TAG, "getUserPost: " + isSuccessful);
        if (!isSuccessful) {
            Log.e(TAG, "getUserPost: " + object);
            return;
        }
        List<Post> posts = (List<Post>) object;
        Log.d(TAG, "posts: " + posts);
    }

    public void getFollowList(boolean isSuccessful, Object object) {
        List<Friend> friends = (List<Friend>) object;
        Log.d(TAG, "friends: " + friends);
    }

    public void saveImage(boolean isSuccessful, Post post) {
        ImageView imageView = binding.imageView;
        imageView.setVisibility(View.VISIBLE);
        Log.d(TAG, "uuid: " + post.getImgUUID());
    }

    public void testSearch(boolean isSuccessful, Object object) {
        Log.d(TAG, "testSearch: " + isSuccessful);
        if (isSuccessful) {
            Log.d(TAG, "object: " + object);
            QuerySnapshot querySnapshot = (QuerySnapshot) object;
            Log.d(TAG, "querySnapshot: " + querySnapshot);
            List<DocumentSnapshot> documentSnapshots = querySnapshot.getDocuments();
            Log.d(TAG, "documentSnapshots: " + documentSnapshots);
        }
    }

}
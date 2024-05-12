package com.example.gp.data.database;

import android.graphics.Bitmap;

import com.example.gp.Items.Post;
import com.example.gp.Utils.MethodUtil;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.security.spec.PSSParameterSpec;
import java.util.UUID;

public class FileDB {
    private final String TAG = "database.fileDB";
    private static FileDB instance;
    private static FirebaseStorage storage;

    private FileDB() {
        storage = FirebaseStorage.getInstance();
    }

    private static FileDB getInstance() {
        if (instance == null) {
            instance = new FileDB();
        }
        return instance;
    }

    public static void saveImage(Post post, Object object, String methodName) {
        FileDB fileDB = getInstance();
        StorageReference storageReference = storage.getReference();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        Bitmap bitmap = post.getImg();

        if (bitmap == null) {
            post.setImgUUID(null);
            PostDB.savePost(true, post, object, methodName);
            return;
        }

        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] data = baos.toByteArray();

        String uuid = fileDB.getUUID();
        post.setImgUUID(uuid);

        UploadTask uploadTask = storageReference.child("image/" + uuid + ".jpg").putBytes(data);
        uploadTask.addOnFailureListener(e -> {
            PostDB.savePost(false, e.getMessage(), object, methodName);
        }).addOnSuccessListener(taskSnapshot -> {
            PostDB.savePost(true, post, object, methodName);
        });
    }

    private String getUUID() {
        return UUID.randomUUID().toString();
    }
}

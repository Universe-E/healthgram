package com.example.gp.data.database;

import android.graphics.Bitmap;

import com.example.gp.Utils.MethodUtil;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
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

    public static void saveImage(Bitmap bitmap, Object object, String methodName, Object object1, String methodName1) {
        FileDB fileDB = getInstance();
        StorageReference storageReference = storage.getReference();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] data = baos.toByteArray();

        String uuid = fileDB.getUUID();

        UploadTask uploadTask = storageReference.child("image/" + uuid + ".jpg").putBytes(data);
        uploadTask.addOnFailureListener(e -> {
            MethodUtil.invokeMethod(object, methodName, false, e.getMessage());
        }).addOnSuccessListener(taskSnapshot -> {
            MethodUtil.invokeMethod(object, methodName, true, object1, methodName1, uuid);
        });
    }

    private String getUUID() {
        return UUID.randomUUID().toString();
    }
}

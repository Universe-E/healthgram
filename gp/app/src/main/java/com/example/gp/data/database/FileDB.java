package com.example.gp.data.database;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.example.gp.Items.Post;
import com.example.gp.Utils.MethodUtil;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.util.List;
import java.util.UUID;

/**
 * A class to handle file operations in Firebase Storage.
 */
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

    public static void getImage(Post post, Object object, String methodName) {
        if (post.getImgUUID() == null) {
            MethodUtil.invokeMethod(object, methodName, true, post);
            return;
        }
        StorageReference storageReference = storage.getReference();
        StorageReference islandRef = storageReference.child("image/" + post.getImgUUID() + ".jpg");

        final long ONE_MEGABYTE = 1024 * 1024;
        islandRef.getBytes(ONE_MEGABYTE)
                .addOnSuccessListener(bytes -> {
                    Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                    post.setImg(bitmap);
                    MethodUtil.invokeMethod(object, methodName, true, post);
                })
                .addOnFailureListener(exception -> {
                    // Handle any errors
                    MethodUtil.invokeMethod(object, methodName, false, exception.getMessage());
                });
    }

    /**
     * Get images from firebase storage
     *
     * @param posts
     * @param position
     * @param object
     * @param methodName
     */
    public static void getImages(List<Post> posts, int position, Object object, String methodName) {
        FileDB fileDB = getInstance();
        StorageReference storageReference = storage.getReference();

        // No more posts to put image in
        if (position >= posts.size()) {
            MethodUtil.invokeMethod(object, methodName, true, posts);
            return;
        }

        // get image from firebase storage by uuid
        String uuid = posts.get(position).getImgUUID();
        if (uuid == null) {
            getImages(posts, position + 1, object, methodName);
            return;
        }

        // Download image from firebase storage
        StorageReference islandRef = storageReference.child("image/" + uuid + ".jpg");
        islandRef.getBytes(1024 * 1024 * 5)
                .addOnSuccessListener(bytes -> {
                    Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                    posts.get(position).setImg(bitmap);
                    getImages(posts, position + 1, object, methodName);
                })
                .addOnFailureListener(exception -> {
                    MethodUtil.invokeMethod(object, methodName, false, exception.getMessage());
                });
    }

    /**
     * Save image to firebase storage
     *
     * @param post       the post object
     * @param object     the object that calls this method
     * @param methodName the method name that calls this method
     */
    public static void newSaveImage(Post post, Object object, String methodName) {
        FileDB fileDB = getInstance();
        StorageReference storageReference = storage.getReference();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        Bitmap bitmap = post.getImg();

        // No image to save
        if (bitmap == null) {
            post.setImgUUID(null);
            PostDB.savePost(true, post, object, methodName);
            return;
        }

        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] data = baos.toByteArray();

        // Get uuid
        String uuid = fileDB.getUUID();
        post.setImgUUID(uuid);

        // Upload image to firebase storage and name it by uuid
        UploadTask uploadTask = storageReference
                .child("image/" + uuid + ".jpg")
                .putBytes(data);
        uploadTask.addOnFailureListener(e -> {
            PostDB.savePost(false, e.getMessage(), object, methodName);
        }).addOnSuccessListener(taskSnapshot -> {
            PostDB.savePost(true, post, object, methodName);
        });
    }

    /**
     * Get a random uuid
     *
     * @return
     */
    private String getUUID() {
        return UUID.randomUUID().toString();
    }
}

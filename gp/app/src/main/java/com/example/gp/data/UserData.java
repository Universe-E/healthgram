package com.example.gp.data;

import android.graphics.Bitmap;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

// a singleton to hold User data
public class UserData {

    private static final String TAG = "userData";

    // signed in status
    // Mutable variable used inside the data layer
    private static MutableLiveData<Boolean> _isSignedIn = new MutableLiveData<>(false);

    // Notes
    // the notes used inside the data layer
    private static MutableLiveData<ArrayList<Note>> _notes = new MutableLiveData<>(new ArrayList<Note>());

    private static <T> void notifyObserver(MutableLiveData<T> liveData) {
        liveData.setValue(liveData.getValue());
    }
    public static void notifyObserver() {
        notifyObserver(_notes);
    }

    private static MutableLiveData<String> _username = new MutableLiveData<>();

    private static MutableLiveData<String> _email = new MutableLiveData<>();

    private static MutableLiveData<String> _userId = new MutableLiveData<>();

    /*********** API ************/

    // Immutable variables sent to UI layer
    public static LiveData<Boolean> isSignedIn = _isSignedIn;

    public static LiveData<String> username = _username;

    public static LiveData<String> email = _email;

    public static LiveData<String> userId = _userId;

    // Set sign in status
    public static void setSignedIn (Boolean newValue) {
        // use postValue() to make the assignation on the main (UI) thread
        _isSignedIn.postValue(newValue);
    }

    // Set username
    public static void setUsername (String newValue) {
        _username.postValue(newValue);
    }

    // Set email
    public static void setEmail (String newValue) {
        _email.postValue(newValue);
    }

    // Set userId
    public static void setUserId (String newValue) {
        _userId.postValue(newValue);
    }

    // An immutable variable sent to UI layer
    public static LiveData<ArrayList<Note>> notes() {
        return _notes;
    }

    // Used by upper layer
    public static void addNote(Note note) {
        List<Note> notes = _notes.getValue();
        if (notes != null) {
            notes.add(note);
            notifyObserver();
        } else {
            Log.e(TAG, "addNote: note collection is null!");
        }
    }

    public static Note deleteNote(int at) {
        List<Note> notes = _notes.getValue();
        if (notes != null && at >= 0 && at < notes.size()) {
            Note note = notes.remove(at);
            notifyObserver();
            return note;
        }
        return null;
    }

    public static List<Note> getNotes() {
        List<Note> notes = _notes.getValue();
        if (notes != null) {
            return Collections.unmodifiableList(notes); // 返回不可修改的列表
        } else {
            return Collections.emptyList(); // 返回空列表
        }
    }

    /*********** END of API ************/

    // Design pattern observer
    // a note data class
    public static class Note implements Parcelable {
        public String id;
        public String name;
        public String description;
        public String imageName;
        public Bitmap image;

        // 构造函数
        public Note(String id, String name, String description) {
            this.id = id;
            this.name = name;
            this.description = description;
        }

        // Parcelable 构造函数
        protected Note(Parcel in) {
            id = in.readString();
            name = in.readString();
            description = in.readString();
            imageName = in.readString();
            // 由于 Bitmap 复杂，可以省略读取图片数据
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(id);
            dest.writeString(name);
            dest.writeString(description);
            dest.writeString(imageName);
            // 由于 Bitmap 复杂，可以省略写入图片数据
        }

        @Override
        public int describeContents() {
            return 0;
        }

        public static final Creator<Note> CREATOR = new Creator<Note>() {
            @Override
            public Note createFromParcel(Parcel in) {
                return new Note(in);
            }

            @Override
            public Note[] newArray(int size) {
                return new Note[size];
            }
        };
    }
}

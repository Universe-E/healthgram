package com.example.gp.data;

import android.graphics.Bitmap;
import android.util.Log;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.ArrayList;
import java.util.List;

// a singleton to hold user data
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

    /*********** API ************/

    // An immutable variable sent to UI layer
    public static LiveData<Boolean> isSignedIn = _isSignedIn;

    // Set sign in status
    public static void setSignedIn (Boolean newValue) {
        // use postValue() to make the assignation on the main (UI) thread
        _isSignedIn.postValue(newValue);
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

    /*********** END of API ************/

    // a note data class
    public static class Note {
        public String id;
        public String name;
        public String description;
        public String imageName;
        public Bitmap image;

        public Note(String id, String name, String description) {
            this.id = id;
            this.name = name;
            this.description = description;
        }

        @Override
        public String toString() {
            return name;
        }
    }
}

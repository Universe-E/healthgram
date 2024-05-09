package com.example.gp.home.ui.Friend;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class FriendlistViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public FriendlistViewModel() {
        mText = new MutableLiveData<>();
    }

    public LiveData<String> getText() {
        return mText;
    }
}
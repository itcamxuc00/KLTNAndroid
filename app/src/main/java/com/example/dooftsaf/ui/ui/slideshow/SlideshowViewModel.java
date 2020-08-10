package com.example.dooftsaf.ui.ui.slideshow;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class SlideshowViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public SlideshowViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("Hiện tại không có đơn hàng nào!");
    }

    public LiveData<String> getText() {
        return mText;
    }
}
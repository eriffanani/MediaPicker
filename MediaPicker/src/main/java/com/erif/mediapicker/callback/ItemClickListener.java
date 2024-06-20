package com.erif.mediapicker.callback;

import androidx.annotation.NonNull;

import com.erif.mediapicker.model.ItemMedia;

public interface ItemClickListener {
    public void onClick(@NonNull ItemMedia item);
}
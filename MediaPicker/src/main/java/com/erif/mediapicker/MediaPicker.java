package com.erif.mediapicker;

import android.content.Context;
import android.content.Intent;

import androidx.activity.result.ActivityResultLauncher;
import androidx.annotation.ColorRes;
import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;

import com.erif.mediapicker.ui.audio.AudioPickerActivity;
import com.erif.mediapicker.ui.files.FilePickerActivity;
import com.erif.mediapicker.ui.gallery.GalleryPickerActivity;
import com.erif.mediapicker.ui.images.ImagePickerActivity;
import com.erif.mediapicker.ui.videos.VideoPickerActivity;

public class MediaPicker {

    public static final int GALLERY = 0;
    public static final int IMAGES = 1;
    public static final int VIDEOS = 2;
    public static final int FILES = 3;
    public static final int AUDIO = 4;

    private final Context context;
    private int type = GALLERY;

    private boolean useFolder = false;
    private boolean multipleSelection = false;

    // Status Bar
    private int statusBarColor = 0;
    private boolean lightStatusBar = false;

    // Toolbar
    private String toolbarTitle;
    private int toolbarNav;

    public MediaPicker(@NonNull Context context) {
        this.context = context;
    }

    public MediaPicker(@NonNull Context context, int type) {
        this.context = context;
        this.type = type;
    }

    public void statusBar(@ColorRes int color, boolean light) {
        this.statusBarColor = color;
        this.lightStatusBar = light;
    }

    public void toolbar(String title) {
        this.toolbarTitle = title;
    }

    public void toolbar(String title, @DrawableRes int icon) {
        this.toolbarTitle = title;
        this.toolbarNav = icon;
    }

    public void setType(int type) {
        this.type = type;
    }

    public MediaPicker useFolder(boolean useFolder) {
        this.useFolder = useFolder;
        return this;
    }

    public MediaPicker multipleSelection(boolean multipleSelection) {
        this.multipleSelection = multipleSelection;
        return this;
    }

    public void launch(ActivityResultLauncher<Intent> result) {
        Class<?> destination = switch (type) {
            case AUDIO -> AudioPickerActivity.class;
            case FILES -> FilePickerActivity.class;
            case VIDEOS -> VideoPickerActivity.class;
            case IMAGES -> ImagePickerActivity.class;
            default -> GalleryPickerActivity.class;
        };
        Intent intent = new Intent(context, destination);
        intent.putExtra("statusBarColor", statusBarColor);
        intent.putExtra("statusBarLight", lightStatusBar);
        intent.putExtra("toolbarTitle", toolbarTitle);
        intent.putExtra("toolbarNav", toolbarNav);
        result.launch(intent);
    }

}

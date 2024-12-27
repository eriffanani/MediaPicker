package com.erif.mediapicker.ui.images;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.view.WindowInsetsControllerCompat;

import com.erif.mediapicker.databinding.ActImagePickerBinding;
import com.google.android.material.appbar.MaterialToolbar;

public class ImagePickerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        var window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        var windowInsetController = new WindowInsetsControllerCompat(window, window.getDecorView());

        var binding = ActImagePickerBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        MaterialToolbar toolbar = binding.toolbar;

        var bundle = getIntent().getExtras();
        if (bundle != null) {
            // Title
            String toolbarTitle = bundle.getString("toolbarTitle", "Choose Media");
            toolbar.setTitle(toolbarTitle);
            // Navigation Icon
            int toolbarNav = bundle.getInt("toolbarNav", 0);
            if (toolbarNav != 0)
                toolbar.setNavigationIcon(toolbarNav);
            int statusBarColor = bundle.getInt("statusBarColor", 0);
            window.setStatusBarColor(ContextCompat.getColor(this, statusBarColor));

            boolean lightStatusBar = bundle.getBoolean("statusBarLight", false);
            windowInsetController.setAppearanceLightStatusBars(lightStatusBar);
            windowInsetController.setAppearanceLightNavigationBars(lightStatusBar);
        }
        setSupportActionBar(toolbar);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
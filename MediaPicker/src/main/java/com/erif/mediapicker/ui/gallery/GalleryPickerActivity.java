package com.erif.mediapicker.ui.gallery;

import android.content.res.Resources;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.erif.mediapicker.R;

public class GalleryPickerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_gallery_picker);
        ActionBar actionBar = getSupportActionBar();

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            String toolbarTitle = bundle.getString("toolbarTitle", "Choose Media");
            int toolbarNav = bundle.getInt("toolbarNav", 0);
            if (actionBar != null) {
                actionBar.setTitle(toolbarTitle);
                Toast.makeText(this, "Nav: "+toolbarNav, Toast.LENGTH_SHORT).show();
                if (toolbarNav != 0) {
                    actionBar.setDisplayHomeAsUpEnabled(true);
                    actionBar.setHomeAsUpIndicator(toolbarNav);
                }


                int titleId = Resources.getSystem().getIdentifier("action_bar_title", "id", "android");
                TextView yourTextView = findViewById(titleId);
                //yourTextView.setTextColor(ContextCompat.getColor(this, R.color.black));
                //yourTextView.setTypeface(face);
            }
        }
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
package com.erif.pickerdemo

import android.os.Bundle
import android.widget.Button
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.erif.mediapicker.MediaPicker

class MainActivity : AppCompatActivity() {

    private lateinit var mediaPicker: MediaPicker

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mediaPicker = MediaPicker(this, MediaPicker.IMAGES)
        mediaPicker.statusBar(R.color.teal_200, true)
        mediaPicker.toolbar("Pilih Media", R.drawable.ic_back)

        val btn: Button = findViewById(R.id.btn)
        btn.setOnClickListener {
            mediaPicker
                .launch(launcher)
        }
    }

    private val launcher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        val code = result.resultCode
        if (code == RESULT_OK) {
            result.data?.let { data ->

            }
        }
    }

}
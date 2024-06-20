package com.erif.pickerdemo

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.database.Cursor
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.provider.Settings
import android.util.Log
import android.widget.Button
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.loader.content.CursorLoader
import com.erif.mediapicker.MediaPicker
import com.erif.pickerdemo.R
import java.io.File
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {

    private val projection = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
        arrayOf(
            MediaStore.Files.FileColumns.VOLUME_NAME,
            MediaStore.Files.FileColumns.RELATIVE_PATH,
            MediaStore.Files.FileColumns.DISPLAY_NAME,
            MediaStore.Files.FileColumns.DATE_MODIFIED,
            MediaStore.Files.FileColumns.BUCKET_DISPLAY_NAME
        )
    } else {
        arrayOf(
            MediaStore.Files.FileColumns.DATA,
            MediaStore.Files.FileColumns.DATE_MODIFIED
        )
    }
    private val sort = "${MediaStore.Files.FileColumns.DATE_MODIFIED} DESC"
    private val selectionAllMedia = "${MediaStore.Files.FileColumns.MEDIA_TYPE}=" +
            "${MediaStore.Files.FileColumns.MEDIA_TYPE_IMAGE} OR " +
            "${MediaStore.Files.FileColumns.MEDIA_TYPE}=" +
            "${MediaStore.Files.FileColumns.MEDIA_TYPE_VIDEO}"

    private val selectionMedia = "(${MediaStore.Files.FileColumns.MEDIA_TYPE} = ? OR " +
            "${MediaStore.Files.FileColumns.MEDIA_TYPE} = ?) AND " +
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                "${MediaStore.Files.FileColumns.BUCKET_ID} = ?"
            } else {
                "${MediaStore.Files.FileColumns.DATA} like ?"
            }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btn: Button = findViewById(R.id.btn)
        btn.setOnClickListener {
            checkPermission()
        }

    }

    private fun checkPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            if (Environment.isExternalStorageManager()) {
                continueStep()
            } else {
                val intentPermission = Intent().apply {
                    action = Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION
                    data = Uri.fromParts("package", packageName, null)
                }
                requestPermissionApiR.launch(intentPermission)
            }
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) 
                == PackageManager.PERMISSION_GRANTED
            ) {
                continueStep()
            } else {
                requestPermissionApiO.launch(Manifest.permission.WRITE_EXTERNAL_STORAGE)
            }
        } else {
            continueStep()
        }
    }

    private fun allowedStoragePermission(): Boolean {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            Environment.isExternalStorageManager()
        } else {
            val result =
                ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
            val result1 =
                ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
            result == PackageManager.PERMISSION_GRANTED && result1 == PackageManager.PERMISSION_GRANTED
        }
    }

    private val requestPermissionApiR = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult())
    {
        if (allowedStoragePermission()) {
            continueStep()
        } else {
            // Denied
        }
    }

    private val requestPermissionApiO = registerForActivityResult(
        ActivityResultContracts.RequestPermission())
    { isGranted ->
        if (isGranted) {
            continueStep()
        }
    }

    private fun cursorMediaByPath(sourcePath: String): Cursor? {
        val queryUri = MediaStore.Files.getContentUri("external")
        val cursorLoader = CursorLoader(
            this, queryUri, projection, selectionMedia,
            arrayOf(
                "${MediaStore.Files.FileColumns.MEDIA_TYPE_IMAGE}",
                "${MediaStore.Files.FileColumns.MEDIA_TYPE_VIDEO}",
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                    "$sourcePath"
                } else {
                    "%$sourcePath%"
                }
            ), sort
        )
        return cursorLoader.loadInBackground()
    }


    private fun readExternalStorage(cursorMedia: Cursor?) {
        debug("reading...")

        cursorMedia?.use { cursor ->
            var num = 0
            var idx = 0
            var currentDate: String? = null
            val sdf = SimpleDateFormat("MMMM yyyy", Locale.getDefault())
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                val volumeIdx = cursor.getColumnIndexOrThrow(projection[0])
                val pathIdx = cursor.getColumnIndexOrThrow(projection[1])
                val displayNameIdx = cursor.getColumnIndexOrThrow(projection[2])
                val dateIdx = cursor.getColumnIndexOrThrow(projection[3])
                val bucketNameIdx = cursor.getColumnIndexOrThrow(projection[4])

                val volumes = MediaStore.getExternalVolumeNames(this)
                val storages = ContextCompat.getExternalFilesDirs(this, null)
                val mStorages: MutableList<String> = ArrayList()
                storages.forEach { storage ->
                    mStorages.add(storage.absolutePath.split("/Android")[0])
                }

                /*val volume = cursor.getString(volumeIdx)
                val path = cursor.getString(pathIdx)
                val name = cursor.getString(displayNameIdx)
                val date = cursor.getString(dateIdx)
                val index = volumes.indexOf(volume)
                val storageName = mStorages[index]
                val finalPath = "$storageName/$path$name"*/
                debug("Cursor count: "+ cursor.count)

                while (cursor.moveToNext()) {
                    num+=1
                    val volume = cursor.getString(volumeIdx)
                    val path = cursor.getString(pathIdx)
                    val name = cursor.getString(displayNameIdx)
                    val date = cursor.getString(dateIdx)
                    val index = volumes.indexOf(volume)
                    val storageName = mStorages[index]
                    val finalPath = "$storageName/$path$name"
                    val file = File(finalPath)
                    if (file.exists()) {
                        debug("Iki nemu")
                    }
                    val mDate = date.toLong() * 1000L
                    val newDate = Date(mDate)
                    currentDate = sdf.format(newDate)
                    idx+=1
                }
            }
            cursor.close()
        }
    }

    private fun continueStep() {
        val cursor = cursorMediaByPath("-1617409521")
        readExternalStorage(cursor)
    }

    private fun debug(message: String?) {
        Log.d("GalleryPicker", message ?: "Empty message")
    }

    private val aaa = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {

    }

}
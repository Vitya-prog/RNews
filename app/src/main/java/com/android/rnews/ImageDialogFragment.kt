package com.android.rnews

import android.Manifest
import android.content.ContentValues
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.DialogFragment
import com.squareup.picasso.Picasso
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileOutputStream

class ImageDialogFragment(private val imageUrl:String):DialogFragment() {

    private lateinit var requestPermissionLauncher: ActivityResultLauncher<String>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.image_dialog, container, false)
        val imageView: ImageView = view.findViewById(R.id.imageView)
        val saveButton: Button = view.findViewById(R.id.button_save)
        Picasso.get().load(imageUrl).error(R.drawable.ic_image_e).into(imageView)
         requestPermissionLauncher = registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted->
            if (isGranted == true){
                saveImageBelowQ(imageView)
            }
        }
        saveButton.setOnClickListener {
           if(Build.VERSION.SDK_INT <= 29){
                requestPermissionLauncher.launch(Manifest.permission.WRITE_EXTERNAL_STORAGE)
           } else {
                saveImage(imageView)
           }
        }
        return view
    }

    private fun saveImageBelowQ(imageView: ImageView) {
        val bitmap = (imageView.drawable as BitmapDrawable).bitmap
        val file = File(
            Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
            "Image_${System.currentTimeMillis()}.jpg"
        )
        val outStream = FileOutputStream(file)
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outStream)
        outStream.flush()
        outStream.close()
        Toast.makeText(context, "Image Saved!", Toast.LENGTH_SHORT).show()
    }

    private fun saveImage(imageView: ImageView) {
        CoroutineScope(Dispatchers.IO).launch {
            val bitmap = (imageView.drawable as BitmapDrawable).bitmap
            val contentValues = ContentValues().apply {
                put(MediaStore.MediaColumns.DISPLAY_NAME, "Image_${System.currentTimeMillis()}")
                put(MediaStore.MediaColumns.MIME_TYPE, "image/jpeg")
                put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_PICTURES)
                put(MediaStore.Images.Media.IS_PENDING, 1)
            }

            val uri = requireContext().contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)
            requireContext().contentResolver.openOutputStream(uri!!).use { outputStream ->
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream!!)
            }

            contentValues.clear()
            contentValues.put(MediaStore.Images.Media.IS_PENDING, 0)
            requireContext().contentResolver.update(uri, contentValues, null, null)
            withContext(Dispatchers.Main){
                Toast.makeText(context,"Image Saved!",Toast.LENGTH_SHORT).show()
            }
        }
    }
}
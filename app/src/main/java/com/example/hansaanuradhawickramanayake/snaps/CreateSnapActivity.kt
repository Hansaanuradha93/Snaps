package com.example.hansaanuradhawickramanayake.snaps

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import com.google.firebase.storage.FirebaseStorage
import kotlinx.android.synthetic.main.activity_create_snap.*
import java.util.jar.Manifest
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.UploadTask
import com.google.android.gms.tasks.OnSuccessListener
import android.support.annotation.NonNull
import com.google.android.gms.tasks.OnFailureListener
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.widget.Toast
import java.io.ByteArrayOutputStream
import java.util.*


class CreateSnapActivity : AppCompatActivity() {

    private var mStorageRef: StorageReference? = null
    val imageName = UUID.randomUUID().toString() + ".jpg"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_snap)

        title = "Create Snap"

        mStorageRef = FirebaseStorage.getInstance().getReference();
    }

    fun chooseImageClicked(view: View){


        if (checkSelfPermission(android.Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){

            requestPermissions(arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE), 1)
        } else {

            getPhoto()
        }

    }

    fun nextClicked(view: View){

// Get the data from an ImageView as bytes
        createSnapImageView.isDrawingCacheEnabled = true
        createSnapImageView.buildDrawingCache()
        val bitmap = (createSnapImageView.drawable as BitmapDrawable).bitmap
        val baos = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos)
        val data = baos.toByteArray()

        var mImageRef = mStorageRef?.child("Images")

        val uploadTask = mImageRef?.child(imageName)?.putBytes(data)
        uploadTask?.addOnFailureListener(OnFailureListener {
            // Handle unsuccessful uploads
            Toast.makeText(this@CreateSnapActivity, "Upload Failed", Toast.LENGTH_SHORT).show()

        })?.addOnSuccessListener(OnSuccessListener<UploadTask.TaskSnapshot> {taskSnapshot ->
            // taskSnapshot.getMetadata() contains file metadata such as size, content-type, etc.


            val downloadUrl = taskSnapshot.downloadUrl
            Log.i("Url", downloadUrl.toString())

            // ...
        })
    }

    fun getPhoto(){

        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(intent, 1)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == 1){

            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED){

                getPhoto()

            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        val selectedImage = data!!.data

        if (requestCode == 1 && resultCode == Activity.RESULT_OK && data != null){

            try {
                val bitmap = MediaStore.Images.Media.getBitmap(this.contentResolver, selectedImage)
                createSnapImageView.setImageBitmap(bitmap)

            } catch (e: Exception){

                e.printStackTrace()
            }

        }
    }
}

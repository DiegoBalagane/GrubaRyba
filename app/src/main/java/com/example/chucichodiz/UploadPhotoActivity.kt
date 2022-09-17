package com.example.chucichodiz

import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import java.io.IOException
import java.util.*

class UploadPhotoActivity : AppCompatActivity() {

    lateinit var selectBnt: Button
    lateinit var uploadBnt: Button
    lateinit var defaultImageBnt: Button
    lateinit var imageView: ImageView
    private val PICK_IMAGE_REQUEST = 71
    lateinit var storage: FirebaseStorage
    lateinit var storageReference: StorageReference
    private var filePath: Uri? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_upload_photo)

        selectBnt = findViewById(R.id.select_image_button)
        uploadBnt = findViewById(R.id.upload_photo_button)
        imageView = findViewById(R.id.profile_image)
        defaultImageBnt = findViewById(R.id.default_image_button)


        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();

        defaultImageBnt.setOnClickListener{
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }

        selectBnt.setOnClickListener {
            selectImage()
        }

        uploadBnt.setOnClickListener {
            uploadImage()

        }

        val actionBar = supportActionBar // helpful var
        actionBar!!.title = "Ustaw zdjęcie" // changing title of activity
        actionBar.setDisplayHomeAsUpEnabled(true) // displaying back button

    }

    private fun addImageToDB(imageName: String) {
        val database = FirebaseDatabase.getInstance().getReference("users")
        val map = mapOf<String, String>(
            "photo" to  imageName
        )
        val userID = FirebaseAuth.getInstance().currentUser!!.uid

        database.child(userID).updateChildren(map)

        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()

    }


    private fun uploadImage() {
        if(filePath != null){

            val userID = FirebaseAuth.getInstance().currentUser!!.uid



                val ref = storageReference?.child("myImages/IMAGE_" + userID)
                val uploadTask = ref?.putFile(filePath!!)
                addImageToDB("IMAGE_" + userID)



        }else{
            Toast.makeText(this, "Please Upload an Image", Toast.LENGTH_SHORT).show()
        }
    }

    private fun selectImage() {
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK) {
            if(data == null || data.data == null){
                return
            }

            filePath = data.data
            try {
                imageView.setImageURI(filePath)
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

}
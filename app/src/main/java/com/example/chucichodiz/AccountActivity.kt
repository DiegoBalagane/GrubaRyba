package com.example.chucichodiz

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.service.notification.NotificationListenerService
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.widget.*
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AlertDialog
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import java.io.File
import java.io.IOException

class AccountActivity : AppCompatActivity() {

    lateinit var toogle : ActionBarDrawerToggle
    lateinit var  drawerLayout: DrawerLayout

    lateinit var editDataBnt: Button
    lateinit var EdtitImageBnt: Button
    lateinit var changePassBnt: Button
    lateinit var deleteAccountBnt: Button
    lateinit var displaNname: TextView

    lateinit var storage: FirebaseStorage
    lateinit var storageReference: StorageReference
    lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_account)


        val actionBar = supportActionBar
        actionBar!!.title = "Moje konto"
        actionBar.setDisplayHomeAsUpEnabled(true) // displaying back button

        auth = FirebaseAuth.getInstance()

        setHeaderData()

        drawerLayout = findViewById(R.id.drawerLayout)
        val navView : NavigationView = findViewById(R.id.navView)
        toogle = ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close)
        drawerLayout.addDrawerListener(toogle)
        toogle.syncState()


        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        navView.setNavigationItemSelectedListener {
            val account = Intent(this, AccountActivity::class.java)
            val home = Intent(this, MainActivity::class.java)
            val friends = Intent(this, FriendsActivity::class.java)
            val mess = Intent(this, MessActivity::class.java)
            val atlas = Intent(this, AtlasActivity::class.java)
            val tutorial = Intent(this, TutorialActivity::class.java)
            val map = Intent(this, MapActivity::class.java)
            val calc = Intent(this, CalcActivity::class.java)
            val meme = Intent(this, MemeActivity::class.java)
            val ranking = Intent(this, RankingActivity::class.java)
            val i = Intent(this,UserlistActivity::class.java)

            it.isChecked = true
            when(it.itemId){
                R.id.nav_account ->   startActivity(account)
                R.id.nav_home ->   startActivity(home)
                // R.id.nav_friends ->   startActivity(friends)
                R.id.nav_mess ->  startActivity(mess)
                R.id.nav_logout -> logout()
                R.id.nav_atlas ->  startActivity(atlas)
                R.id.nav_tutorial ->   startActivity(tutorial)
                R.id.nav_mapa ->   startActivity(map)
                R.id.nav_calc ->   startActivity(i)
                R.id.nav_meme ->   startActivity(meme)
                R.id.nav_ranking ->   startActivity(ranking)

            }




            true
        }


        editDataBnt = findViewById(R.id.edit_data_button)
        editDataBnt.setOnClickListener {
            editDataDialog()
        }

        changePassBnt = findViewById(R.id.edit_password_button)
        changePassBnt.setOnClickListener {
           val intent = Intent(this, NewPassActivity::class.java)
           startActivity(intent)

        }

        deleteAccountBnt = findViewById(R.id.deleate_account_button)
        deleteAccountBnt.setOnClickListener {
            deleteAccount()
        }


    }

    private fun deleteAccount() {
        val builder = AlertDialog.Builder(this)
        val inflater: LayoutInflater = layoutInflater
        val dialogLayout = inflater.inflate(R.layout.delete_user_layout, null)
        val intent = Intent(this, LoginActivity::class.java)
        with(builder){

            setPositiveButton("Tak, chcę"){dialog, with->
                          val user = Firebase.auth.currentUser!!

                          user.delete()
                                  .addOnCompleteListener { task ->
                                      if (task.isSuccessful) {
                                            Toast.makeText(this@AccountActivity, "Konto zostało usuniete!", Toast.LENGTH_SHORT).show()
                                            startActivity(intent)
                                            finish()


                                      }
            }                     }
            setNegativeButton("Nie, nie chcę. Anuluj"){dialog, with ->

            }

            setView(dialogLayout)
            show()
        }



    }


    private fun editDataDialog() {
        //displaNname = findViewById(R.id.name)
        val builder = AlertDialog.Builder(this)
        val inflater: LayoutInflater = layoutInflater
        val dialogLayout = inflater.inflate(R.layout.edit_data_layout, null)
        val editTextName = dialogLayout.findViewById<EditText>(R.id.input_name_to_edit)
        val editTextLastName = dialogLayout.findViewById<EditText>(R.id.input_lastname_to_edit)

        with(builder){

            setPositiveButton("Zapisz"){dialog, with->
               if( editTextName.text.toString() != "") {
                   val newName = editTextName.text.toString()
                   val database = FirebaseDatabase.getInstance().getReference("users")
                   val map = mapOf<String, String>(
                       "name" to  newName
                   )
                   val userID = FirebaseAuth.getInstance().currentUser!!.uid

                   database.child(userID).updateChildren(map)


               }
                if( editTextLastName.text.toString() != "") {
                   val newLastName = editTextLastName.text.toString()
                   val database = FirebaseDatabase.getInstance().getReference("users")
                   val map = mapOf<String, String>(
                       "lastname" to  newLastName
                   )
                   val userID = FirebaseAuth.getInstance().currentUser!!.uid

                   database.child(userID).updateChildren(map)

                   }
                setHeaderData()

            }
            setNegativeButton("Anuluj"){dialog, with ->

            }

            setView(dialogLayout)
            show()
        }
    }

    private fun logout() {
        FirebaseAuth.getInstance().signOut()


        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)

        Toast.makeText(this@AccountActivity, "Zostałeś wylogowany!", Toast.LENGTH_SHORT).show()

        finish()

    }

    private fun setHeaderData() {


        val navigationView: NavigationView = findViewById(R.id.navView)
        val headerView: View = navigationView.getHeaderView(0)
        val nameHeader: TextView = headerView.findViewById(R.id.username)
        val emailHeader: TextView = headerView.findViewById(R.id.useremail)
        val displayName: TextView = findViewById(R.id.name)
        val displayEmail: TextView = findViewById(R.id.displayEmail)


        val userID = FirebaseAuth.getInstance().currentUser!!.uid
        val db = FirebaseDatabase.getInstance().getReference("users")
        db.child(userID).get().addOnSuccessListener {
            if(it.exists()){
                val navName = it.child("name").value
                val navLastname = it.child("lastname").value
                val navEmail = it.child("emial").value
                val fullname = navName.toString() + " " + navLastname.toString()
                nameHeader.setText(fullname)
                displayName.setText(fullname)
                emailHeader.setText(navEmail.toString())
                displayEmail.setText(navEmail.toString())
            }
        }

        setImage()

    }


    private fun setImage() {
        storage = FirebaseStorage.getInstance();
        val userID = FirebaseAuth.getInstance().currentUser!!.uid
        storageReference = storage.getReference().child("/myImages/IMAGE_" + userID);

        try {
            val localFile: File = File.createTempFile("image", "png")
            storageReference.getFile(localFile)
                .addOnSuccessListener {

                    val bitmap: Bitmap = BitmapFactory.decodeFile(localFile.absolutePath)
                    findViewById<ImageView>(R.id.profile_photo).setImageBitmap(bitmap)
                    findViewById<ImageView>(R.id.actual_image).setImageBitmap(bitmap)
                }
        }catch (e: IOException){
            e.printStackTrace()
        }
    }



    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        if(toogle.onOptionsItemSelected(item)){
            true
        }
        return super.onOptionsItemSelected(item)
    }


    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }


}
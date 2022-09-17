
package com.example.chucichodiz

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import java.io.File
import java.io.IOException

class FriendsActivity : AppCompatActivity() {

    lateinit var toogle : ActionBarDrawerToggle
    lateinit var  drawerLayout: DrawerLayout
    lateinit var storage: FirebaseStorage
    lateinit var storageReference: StorageReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_friends)
        val actionBar = supportActionBar
        actionBar!!.title = "Znajomi"
        actionBar.setDisplayHomeAsUpEnabled(true) // displaying back button


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
                R.id.nav_calc ->   startActivity(calc)
                R.id.nav_meme ->   startActivity(meme)
                R.id.nav_ranking ->   startActivity(ranking)




            }
            true
        }





    }

    private fun logout() {
        FirebaseAuth.getInstance().signOut()


        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)

        Toast.makeText(this@FriendsActivity, "Zostałeś wylogowany!", Toast.LENGTH_SHORT).show()

        finish()

    }

    private fun setHeaderData() {


        val navigationView: NavigationView = findViewById(R.id.navView)
        val headerView: View = navigationView.getHeaderView(0)
        val nameHeader: TextView = headerView.findViewById(R.id.username)
        val emailHeader: TextView = headerView.findViewById(R.id.useremail)


        val userID = FirebaseAuth.getInstance().currentUser!!.uid
        val db = FirebaseDatabase.getInstance().getReference("users")
        db.child(userID).get().addOnSuccessListener {
            if(it.exists()){
                val navName = it.child("name").value
                val navLastname = it.child("lastname").value
                val navEmail = it.child("emial").value
                val fullname = navName.toString() + " " + navLastname.toString()
                nameHeader.setText(fullname)
                emailHeader.setText(navEmail.toString())
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
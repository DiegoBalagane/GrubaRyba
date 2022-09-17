package com.example.chucichodiz

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class AtlasActivity : AppCompatActivity() {
    private lateinit var fishButton: Button
    private lateinit var baitButton: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_atlas)
        fishButton = findViewById(R.id.ButtonFish)
        baitButton = findViewById(R.id.ButtonBait)

        val fish = Intent(this, FishActivity::class.java)
        val bait = Intent(this, BaitActivity::class.java)

        fishButton.setOnClickListener{
            startActivity(fish)
        }
        baitButton.setOnClickListener{
            startActivity(bait)
        }

        val actionBar = supportActionBar // helpful var
        actionBar!!.title = "Atlas ryb i przynęt" // changing title of activity
        actionBar.setDisplayHomeAsUpEnabled(true) // displaying back button
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}
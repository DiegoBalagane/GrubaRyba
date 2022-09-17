package com.example.chucichodiz

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class MemeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_meme)

        val actionBar = supportActionBar // helpful var
        actionBar!!.title = "Kącik humorystyczny" // changing title of activity
        actionBar.setDisplayHomeAsUpEnabled(true) // displaying back button
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}
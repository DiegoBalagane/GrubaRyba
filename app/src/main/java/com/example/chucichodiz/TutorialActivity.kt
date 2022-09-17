package com.example.chucichodiz

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class TutorialActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tutorial)

        val actionBar = supportActionBar // helpful var
        actionBar!!.title = "Poradnik" // changing title of activity
        actionBar.setDisplayHomeAsUpEnabled(true) // displaying back button
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}
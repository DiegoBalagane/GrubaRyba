package com.example.chucichodiz

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class TutorialActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_meme)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}
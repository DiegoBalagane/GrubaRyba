package com.example.chucichodiz

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView

class MemeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        lateinit var btnSelect: Button
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_meme)

        val actionBar = supportActionBar // helpful var
        actionBar!!.title = "Kącik humorystyczny" // changing title of activity
        actionBar.setDisplayHomeAsUpEnabled(true) // displaying back button

        btnSelect = findViewById(R.id.btnChoose)

        btnSelect.setOnClickListener{
            setMeme()
        }

        setMeme()
    }

    private fun setMeme() {
        findViewById<ImageView>(R.id.ivMeme).setImageResource(returnPicture(getNumber()))
    }

    private fun getNumber(): Int {
        return (1..7).random()
    }

    private fun returnPicture(number: Int): Int {
        return when (number) {
            1 -> {
                 R.drawable.meme1
            }
            2 -> {
                R.drawable.meme2
            }
            3 -> {
                R.drawable.meme3
            }
            4 -> {
                R.drawable.meme4
            }
            5 -> {
                R.drawable.meme5
            }
            6 -> {
                R.drawable.meme6
            }
            else -> {
                R.drawable.meme7
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}
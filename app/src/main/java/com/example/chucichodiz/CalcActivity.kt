package com.example.chucichodiz

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class CalcActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_calc)

        val actionBar = supportActionBar // helpful var
        actionBar!!.title = "Kalkulator" // changing title of activity
        actionBar.setDisplayHomeAsUpEnabled(true) // displaying back button
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}
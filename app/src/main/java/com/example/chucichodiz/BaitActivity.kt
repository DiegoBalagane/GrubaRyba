package com.example.chucichodiz

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class BaitActivity : AppCompatActivity() {
    lateinit var rvBaits: RecyclerView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bait)

        rvBaits = findViewById(R.id.rvBaits)

        val baits = createBaits()
        rvBaits.adapter = BaitAdapter(this, baits)
        rvBaits.layoutManager = LinearLayoutManager(this)

        val actionBar = supportActionBar // helpful var
        actionBar!!.title = "Atlas przynęt" // changing title of activity
        actionBar.setDisplayHomeAsUpEnabled(true) // displaying back button
    }

    private fun createBaits(): List<Bait> {
        val baits = mutableListOf<Bait>()
        baits.add(Bait("Białe robaki", R.drawable.bialyrobak))
        baits.add(Bait("Błystka", R.drawable.blystka))
        baits.add(Bait("Chleb", R.drawable.chleb))
        baits.add(Bait("Czerwone Robaki", R.drawable.czerwonyrobak))
        baits.add(Bait("Kukurydza", R.drawable.kukurydza))
        baits.add(Bait("Twister", R.drawable.twister))
        baits.add(Bait("Wobler", R.drawable.wobler))
        return baits
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}
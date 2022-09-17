package com.example.chucichodiz

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class FishActivity : AppCompatActivity() {
    lateinit var rvFishes: RecyclerView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fish)

        rvFishes = findViewById(R.id.rvFishes)

        val fishes = createFishes()
        rvFishes.adapter = FishAdapter(this, fishes)
        rvFishes.layoutManager = LinearLayoutManager(this)

        val actionBar = supportActionBar // helpful var
        actionBar!!.title = "Atlas ryb" // changing title of activity
        actionBar.setDisplayHomeAsUpEnabled(true) // displaying back button
    }

    private fun createFishes(): List<Fish> {
        val fishes = mutableListOf<Fish>()
        fishes.add(Fish("Karaś", R.drawable.karas))
        fishes.add(Fish("Karp", R.drawable.karp))
        fishes.add(Fish("Leszcz", R.drawable.leszcz))
        fishes.add(Fish("Lin", R.drawable.lin))
        fishes.add(Fish("Okoń", R.drawable.okon))
        fishes.add(Fish("Sum", R.drawable.sum))
        fishes.add(Fish("Szczupak", R.drawable.szczupak))
        fishes.add(Fish("Ukleja", R.drawable.ukleja))
        return fishes
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}
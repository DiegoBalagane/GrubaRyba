package com.example.chucichodiz

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import java.util.*
import kotlin.collections.ArrayList

class MessActivity : AppCompatActivity() {

    private lateinit var userList: ArrayList<User>
    private lateinit var adapter: UserAdapter
    private lateinit var dbref : DatabaseReference
    private lateinit var userRecyclerview : RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mess)

        userList = ArrayList()
        adapter = UserAdapter(this,userList)
        userRecyclerview = findViewById(R.id.userRecyclerView)
        userRecyclerview.layoutManager = LinearLayoutManager(this)
        userRecyclerview.adapter = adapter

        getUserData()

        val actionBar = supportActionBar // helpful var
        actionBar!!.title = "Wiadomości" // changing title of activity
        actionBar.setDisplayHomeAsUpEnabled(true) // displaying back button
    }
    private fun getUserData() {

        dbref= FirebaseDatabase.getInstance().getReference()
        val userID = FirebaseAuth.getInstance().currentUser!!.uid
        //val textViewrank: TextView = findViewById(R.id.textViewrank)
        // top3query.addChildEventListener(object : ChildEventListener
        // dbref = FirebaseDatabase.getInstance().getReference("users")

        dbref.child("users").addValueEventListener(object : ValueEventListener {

            override fun onDataChange(snapshot: DataSnapshot) {

                userList.clear()

                    for (userSnapshot in snapshot.children){


                        val user = userSnapshot.getValue(User::class.java)
                        if(userID != user?.userID)
                            userList.add(user!!)


                    }

                   adapter.notifyDataSetChanged()


                }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }




        })

    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}
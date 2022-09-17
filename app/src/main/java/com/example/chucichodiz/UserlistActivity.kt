package com.example.chucichodiz

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
//import com.example.myapplication.User
import com.google.firebase.database.*
import java.util.*
import kotlin.collections.ArrayList

class UserlistActivity : AppCompatActivity() {

    private lateinit var dbref : DatabaseReference
    private lateinit var userRecyclerview : RecyclerView
    private lateinit var userArrayList : ArrayList<User>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_list)
        val actionBar = supportActionBar
        actionBar!!.title = "Ranking wędkarzy"
        actionBar.setDisplayHomeAsUpEnabled(true) // displaying back button
        userRecyclerview = findViewById(R.id.userList)
        userRecyclerview.layoutManager = LinearLayoutManager(this)
        userRecyclerview.setHasFixedSize(true)

        userArrayList = arrayListOf<User>()
        getUserData()

    }

    private fun getUserData() {

         dbref= FirebaseDatabase.getInstance().getReference("users")
        var top3query = dbref.orderByChild("counterFish").limitToLast(2)
        //val textViewrank: TextView = findViewById(R.id.textViewrank)
       // top3query.addChildEventListener(object : ChildEventListener
               // dbref = FirebaseDatabase.getInstance().getReference("users")

        top3query.addValueEventListener(object : ValueEventListener{

            override fun onDataChange(snapshot: DataSnapshot) {

                if (snapshot.exists()){

                    for (userSnapshot in snapshot.children){


                        val user = userSnapshot.getValue(User::class.java)
                        userArrayList.add(user!!)
                        Collections.reverse(userArrayList)

                    }

                    userRecyclerview.adapter = MyAdapter(userArrayList)


                }

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
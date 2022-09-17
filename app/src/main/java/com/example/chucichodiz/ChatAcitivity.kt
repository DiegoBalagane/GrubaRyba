package com.example.chucichodiz

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.ImageView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class ChatAcitivity : AppCompatActivity() {
    private lateinit var chatRecyclerView: RecyclerView
    private lateinit var messageBox: EditText
    private lateinit var sendButton: ImageView
    private lateinit var messageAdapter: MessageAdapter
    private lateinit var messageList: ArrayList<Message>
    private lateinit var dbref: DatabaseReference

    var receiverRoom: String? = null
    var senderRoom: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat_acitivity)
        chatRecyclerView = findViewById(R.id.charRecyclerView)
        messageBox = findViewById(R.id.messageBox)
        sendButton = findViewById(R.id.sentButton)


        val name = intent.getStringExtra("name")
        val receiverUID = intent.getStringExtra("userID")
        val senderUID = FirebaseAuth.getInstance().currentUser?.uid

        dbref = FirebaseDatabase.getInstance().getReference()

        senderRoom = receiverUID + senderUID
        receiverRoom = senderUID + receiverUID

        supportActionBar?.title = name
        messageList = ArrayList()
        messageAdapter = MessageAdapter(this, messageList)
        chatRecyclerView.layoutManager = LinearLayoutManager(this)
        chatRecyclerView.adapter= messageAdapter
        dbref.child("chats").child(senderRoom!!).child("messages").addValueEventListener(object: ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                messageList.clear()

                for(postSnapshot in snapshot.children){
                    val message = postSnapshot.getValue(Message::class.java)
                    messageList.add(message!!)
                }
                messageAdapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {

            }

        })
        sendButton.setOnClickListener {
            val message = messageBox.text.toString()
            val messageObject = Message(message,senderUID)

            dbref.child("chats").child(senderRoom!!).child("messages").push().setValue(messageObject).addOnSuccessListener {
                dbref.child("chats").child(receiverRoom!!).child("messages").push().setValue(messageObject)
            }
            messageBox.setText("")
        }

        val actionBar = supportActionBar // helpful var
        actionBar!!.title = "Chat" // changing title of activity
        actionBar.setDisplayHomeAsUpEnabled(true) // displaying back button

    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}
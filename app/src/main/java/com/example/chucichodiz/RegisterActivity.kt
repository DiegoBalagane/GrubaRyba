package com.example.chucichodiz

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
//import com.example.myapplication.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class RegisterActivity : AppCompatActivity() {

    lateinit var name: EditText
    lateinit var email: EditText
    lateinit var lastname: EditText
    lateinit var password: EditText
    lateinit var regButton: Button
    lateinit var loginButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        regButton = findViewById(R.id.registerButton)
        email = findViewById(R.id.inputEmail)
        name = findViewById(R.id.inputName)
        lastname = findViewById(R.id.inputLastName)
        password = findViewById(R.id.inputPass)
        loginButton = findViewById(R.id.loginButton)

        loginButton.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }

        regButton.setOnClickListener {


            validateForm()

        }
        val actionBar = supportActionBar // helpful var
        actionBar!!.title = "Rejestracja" // changing title of activity
        actionBar.setDisplayHomeAsUpEnabled(true) // displaying back button

    }

    private fun saveUser() {
        val Uemail = email.text.toString().trim()
        val Uname = name.text.toString().trim()
        val Ulastname = lastname.text.toString().trim()
        val Upass = password.text.toString().trim()



        val ref = FirebaseDatabase.getInstance().getReference("users")

        val userID = FirebaseAuth.getInstance().currentUser!!.uid
        val user = User(userID,Uemail, Uname, Ulastname, Upass, "fish.png",0)

        ref.child(userID).setValue(user).addOnCompleteListener {
          //  Toast.makeText(applicationContext, "git", Toast.LENGTH_SHORT).show()
        }

        val intent = Intent(this, UploadPhotoActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun validateForm() {

        if (email.text.isEmpty()) {
            Toast.makeText(this, "Proszę podać Email!", Toast.LENGTH_SHORT).show()

        } else if (name.text.isEmpty()) {
            Toast.makeText(this, "Proszę podać imie!", Toast.LENGTH_SHORT).show()

        } else if (lastname.text.isEmpty()) {
            Toast.makeText(this, "Proszę podać nazwisko!", Toast.LENGTH_SHORT).show()

        } else if (password.text.isEmpty()) {
            Toast.makeText(this, "Proszę podać hasło!", Toast.LENGTH_SHORT).show()

        } else {
            creteUser()

        }

    }


    private fun creteUser() {
        FirebaseAuth.getInstance().createUserWithEmailAndPassword(
            email.text.toString().trim(),
            password.text.toString().trim()
        )
            .addOnCompleteListener() { task ->
                if (task.isSuccessful) {
                    // val firebaseUser: FirebaseUser = task.result!!.user!!

                    Toast.makeText(
                        this@RegisterActivity,
                        "Konto zostało utworzone!",
                        Toast.LENGTH_SHORT
                    ).show()

                    saveUser()


                } else {

                    Toast.makeText(
                        this@RegisterActivity,
                        task.exception!!.message.toString(),
                        Toast.LENGTH_SHORT
                    ).show()


                }
            }

    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}
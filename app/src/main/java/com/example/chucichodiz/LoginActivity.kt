package com.example.chucichodiz

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class LoginActivity : AppCompatActivity() {
    lateinit var email: EditText
    lateinit var password: EditText
    lateinit var loginButton: Button
    lateinit var registerButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        email = findViewById(R.id.inputEmail)
        password = findViewById(R.id.inputPass)

        loginButton = findViewById(R.id.loginButton)
        registerButton = findViewById(R.id.registerButton)

        registerButton.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }

        loginButton.setOnClickListener {
            validateForm()
        }

        val actionBar = supportActionBar // helpful var
        actionBar!!.title = "Logowanie" // changing title of activity
        actionBar.setDisplayHomeAsUpEnabled(true) // displaying back button

    }

    private fun loginUser() {
        FirebaseAuth.getInstance().signInWithEmailAndPassword(email.text.toString(), password.text.toString())
            .addOnCompleteListener() { task ->
                if (task.isSuccessful) {
                    Toast.makeText(this@LoginActivity, "logowanie pomyślne", Toast.LENGTH_SHORT)
                        .show()

                    val intent = Intent(this, MainActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    intent.putExtra("userID", FirebaseAuth.getInstance().currentUser!!.uid)
                    intent.putExtra("emial", email.text.toString().trim())
                    startActivity(intent)
                    finish()
                }else{
                    Toast.makeText(this@LoginActivity, task.exception!!.message.toString(), Toast.LENGTH_SHORT)
                        .show()
                }
            }
    }


    private fun validateForm() {

        if (email.text.isEmpty()) {
            Toast.makeText(this, "Proszę podać Email!", Toast.LENGTH_SHORT).show()

        } else if (password.text.isEmpty()) {
            Toast.makeText(this, "Proszę podać hasło!", Toast.LENGTH_SHORT).show()

        } else {
            loginUser()

        }

    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}
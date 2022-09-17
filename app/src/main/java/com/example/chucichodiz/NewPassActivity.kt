package com.example.chucichodiz

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth

class NewPassActivity : AppCompatActivity() {

    lateinit var oldPas: EditText
    lateinit var newPas: EditText
    lateinit var confNewPas: EditText

    lateinit var cancelBnt: Button
    lateinit var confirmBnt: Button

    lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_pass)

        auth  = FirebaseAuth.getInstance()

        cancelBnt = findViewById(R.id.CancelButton)
        cancelBnt.setOnClickListener {
            val intent = Intent(this, AccountActivity::class.java)
            startActivity(intent)
        }

        confirmBnt = findViewById(R.id.confirmButton)
        confirmBnt.setOnClickListener {
            changePasswoed()
        }

        val actionBar = supportActionBar // helpful var
        actionBar!!.title = "Zmiana hasła" // changing title of activity
        actionBar.setDisplayHomeAsUpEnabled(true) // displaying back button



    }

    private fun changePasswoed() {
        oldPas = findViewById(R.id.inputOldPass)
        newPas = findViewById(R.id.inputNewPass)
        confNewPas = findViewById(R.id.inputConfPass)

        if(oldPas.text.isNotEmpty() && newPas.text.isNotEmpty() && confNewPas.text.isNotEmpty()){
            if(newPas.text.toString() == confNewPas.text.toString()){

                val user = auth.currentUser
                if(user != null && user.email != null) {
                    val credential =  EmailAuthProvider.getCredential(user.email!!, oldPas.text.toString())

                    user?.reauthenticate(credential)?.addOnCompleteListener{
                        if(it.isSuccessful){
                           // Toast.makeText(this, "Git", Toast.LENGTH_SHORT).show()
                            user?.updatePassword(newPas.text.toString()).addOnCompleteListener{ task ->
                                if(task.isSuccessful){
                                    Toast.makeText(this, "hasło zmienione", Toast.LENGTH_SHORT).show()
                                    auth.signOut()
                                    val intent = Intent(this, LoginActivity::class.java)
                                    startActivity(intent)
                                    finish()
                                }

                            }
                        }else{
                            Toast.makeText(this, "Stare hasło nie prawidłowe!", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }else{
                Toast.makeText(this, "nowe hasła nie są identyczne!", Toast.LENGTH_SHORT).show()
            }

        }else{
            Toast.makeText(this, "Wypełnij wszytskie pola!", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}
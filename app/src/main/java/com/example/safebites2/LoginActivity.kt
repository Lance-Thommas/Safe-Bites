package com.example.safebites2

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val signInText = findViewById<TextView>(R.id.signInText)
        signInText.setOnClickListener {
            val intent = Intent(this, LoginActivity2::class.java)
            startActivity(intent)
        }

        val registerButton = findViewById<Button>(R.id.registerButton)
        registerButton.setOnClickListener {
            val intent = Intent(this, RegistrationEmailConfirmation::class.java)
            startActivity(intent)
        }
    }
}

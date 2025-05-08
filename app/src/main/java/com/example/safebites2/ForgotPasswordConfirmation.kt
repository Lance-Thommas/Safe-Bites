package com.example.safebites2

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class ForgotPasswordConfirmation : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgot_password_confirmation)

        val openEmailApp = findViewById<Button>(R.id.openEmailApp)
        openEmailApp.setOnClickListener {
            val intent = Intent(this, AlmostThere::class.java)
            startActivity(intent)
        }
    }
}

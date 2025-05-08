package com.example.safebites2

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class ForgotPassword : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgot_password)

        val sendInstructionsInText = findViewById<Button>(R.id.sendInstructionsInText)
        sendInstructionsInText.setOnClickListener {
            val intent = Intent(this, ForgotPasswordConfirmation::class.java)
            startActivity(intent)
        }
    }
}

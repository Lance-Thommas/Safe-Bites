package com.example.safebites2

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class ForgotPasswordConfirmation : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgot_password_confirmation)

        val openEmailApp = findViewById<TextView>(R.id.skipCheckLater)
        openEmailApp.setOnClickListener {
            val intent = Intent(this, AlmostThere::class.java)
            startActivity(intent)
        }

        val openGmailButton: Button = findViewById(R.id.openEmailApp)

        openGmailButton.setOnClickListener {
            val intent = Intent(Intent.ACTION_SENDTO).apply {
                data = Uri.parse("mailto:") // Only email apps should handle this
                putExtra(Intent.EXTRA_EMAIL, arrayOf("")) // Optional: preset recipient
                putExtra(Intent.EXTRA_SUBJECT, "")        // Optional: preset subject
            }

            // Verify there's an app that can handle this intent
            if (intent.resolveActivity(packageManager) != null) {
                startActivity(intent)
            }


        }
    }
}

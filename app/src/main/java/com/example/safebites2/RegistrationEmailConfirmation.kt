package com.example.safebites2

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.text.HtmlCompat

class RegistrationEmailConfirmation : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration_confirmation_email)

        val textView = findViewById<TextView>(R.id.tryAnotherEmailAddress)
        val styledText = HtmlCompat.fromHtml(
            "<font color='#000000'>or</font> <font color='#579F4E'>try another email address</font>",
            HtmlCompat.FROM_HTML_MODE_LEGACY
        )
        textView.text = styledText

        findViewById<TextView>(R.id.skipConfirmLater).setOnClickListener {
            startActivity(Intent(this, HomePage::class.java))
        }

        val openGmailButton: Button = findViewById(R.id.openEmailApp)

        openGmailButton.setOnClickListener {
            val intent = Intent(Intent.ACTION_SEND).apply {
                type = "message/rfc822"
                putExtra(Intent.EXTRA_EMAIL, arrayOf(""))
                putExtra(Intent.EXTRA_SUBJECT, "")
            }

            startActivity(Intent.createChooser(intent, "Choose an Email app"))
        }
    }

}

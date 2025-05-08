package com.example.safebites2

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
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

    }
}

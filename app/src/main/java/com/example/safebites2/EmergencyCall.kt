package com.example.safebites2

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class EmergencyCall : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_emergency_call)

        findViewById<Button>(R.id.cancel_call).setOnClickListener {
            startActivity(Intent(this, HomePage::class.java))
        }

        // Emergency call button - opens phone dialer with 999
        findViewById<Button>(R.id.call999).setOnClickListener {
            val intent = Intent(Intent.ACTION_DIAL)
            intent.data = Uri.parse("tel:999")
            startActivity(intent)
        }
    }
}

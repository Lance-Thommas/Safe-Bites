package com.example.safebites2

import android.content.Intent
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

    }
}

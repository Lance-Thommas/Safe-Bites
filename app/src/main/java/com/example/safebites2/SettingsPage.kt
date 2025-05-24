package com.example.safebites2

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class SettingsPage : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_settings_page)

        findViewById<Button>(R.id.cache).setOnClickListener {
            Toast.makeText(this, "Cache cleared", Toast.LENGTH_SHORT).show()
        }

        findViewById<Button>(R.id.resetlist).setOnClickListener {
            Toast.makeText(this, "SafeBite List has been RESET", Toast.LENGTH_SHORT).show()
        }


        findViewById<Button>(R.id.export).setOnClickListener {
            Toast.makeText(this, "Profile has been Exported", Toast.LENGTH_SHORT).show()
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        findViewById<ImageView>(R.id.imageView13).setOnClickListener {
            startActivity(Intent(this, HomePage::class.java))
        }

        findViewById<Button>(R.id.logout).setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
        }




    }
}
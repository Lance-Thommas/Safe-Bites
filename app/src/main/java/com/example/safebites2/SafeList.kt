package com.example.safebites2

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class SafeList : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_safe_list)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        findViewById<ImageView>(R.id.settingsbtn).setOnClickListener {
            startActivity(Intent(this, SettingsPage::class.java))
        }

        findViewById<Button>(R.id.camera_btn).setOnClickListener {
            startActivity(Intent(this, CameraPage::class.java))
        }


        findViewById<ImageView>(R.id.homebtn).setOnClickListener {
            startActivity(Intent(this, HomePage::class.java))
        }


        val altButton = findViewById<Button>(R.id.alternative)
        val savedButton = findViewById<Button>(R.id.savedprod2)

        altButton.setOnClickListener {
            altButton.setBackgroundResource(R.drawable.logout_bg)   // highlighted state
            savedButton.setBackgroundResource(R.drawable.alternative_bg) // dimmed state
        }

        savedButton.setOnClickListener {
            savedButton.setBackgroundResource(R.drawable.logout_bg)
            altButton.setBackgroundResource(R.drawable.alternative_bg)
        }
    }
}
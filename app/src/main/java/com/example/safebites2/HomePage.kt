package com.example.safebites2

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class HomePage : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_home_page)



        // Profile picture (image)
        findViewById<ImageView>(R.id.imageView).setOnClickListener {
            startActivity(Intent(this, ProfileSetup::class.java))
        }


        // Settings (image)
        findViewById<ImageView>(R.id.settingsbtn).setOnClickListener {
            startActivity(Intent(this, SettingsPage::class.java))
        }

        // Buttons
        findViewById<Button>(R.id.packagebtn).setOnClickListener {
            startActivity(Intent(this, PackagePage::class.java))
        }

        findViewById<Button>(R.id.emergency_btn).setOnClickListener {
            startActivity(Intent(this, EmergencyCall::class.java))
        }

        findViewById<Button>(R.id.bitesbtn).setOnClickListener {
            startActivity(Intent(this, SafeList::class.java))
        }

        findViewById<Button>(R.id.alert_btn).setOnClickListener {
            startActivity(Intent(this, SafeList::class.java))
        }

        findViewById<Button>(R.id.camera_btn).setOnClickListener {
            startActivity(Intent(this, CameraPage::class.java))
        }


        }
    }


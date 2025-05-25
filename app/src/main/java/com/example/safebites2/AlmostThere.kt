package com.example.safebites2

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class AlmostThere : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_almost_there)


        findViewById<Button>(R.id.resetPasswordInText).setOnClickListener {
            startActivity(Intent(this, HomePage::class.java))
            Toast.makeText(this,"Password Changed", Toast.LENGTH_SHORT).show()
        }
    }
}

package com.example.safebites2

import android.content.Intent
import android.os.Bundle
import android.text.Html
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.safebites2.ui.theme.SafeBites2Theme

class PackagePage : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_package_page)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // HTML colored text
        val textView17 = findViewById<TextView>(R.id.textView17)
        textView17.text = Html.fromHtml(
            "Unlock powerful features that takes safety to another level with <font color='#FFC107'><b>SafeBites Pro</b></font>",
            Html.FROM_HTML_MODE_LEGACY
        )

        findViewById<TextView>(R.id.textView24).setOnClickListener {
            startActivity(Intent(this, HomePage::class.java))
        }

        // ✅ FIX: initialize the compose view
        val composeView = findViewById<ComposeView>(R.id.composeView)

        composeView.setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
        composeView.setContent {
            SafeBites2Theme {
                SlidingButton(buttonText = "Let’s Upgrade!") {
                    startActivity(Intent(this, PackagePayment::class.java))
                }
            }
        }
    }
}

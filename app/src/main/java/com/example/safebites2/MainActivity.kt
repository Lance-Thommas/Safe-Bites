package com.example.safebites2

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.PlayerView
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material3.Icon
import kotlin.math.roundToInt

class MainActivity : ComponentActivity() {
    private lateinit var player: ExoPlayer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Initialize ExoPlayer
        player = ExoPlayer.Builder(this).build()
        val playerView = findViewById<PlayerView>(R.id.playerView)
        playerView.player = player
        val mediaItem = MediaItem.fromUri("android.resource://$packageName/raw/landingpage")
        player.setMediaItem(mediaItem)
        playerView.useController = false
        player.repeatMode = ExoPlayer.REPEAT_MODE_ONE
        player.prepare()
        player.play()

        val composeView = findViewById<ComposeView>(R.id.composeView)
        composeView.setContent {
            SlidingButton(
                onTrigger = {
                    startActivity(Intent(this, LoginActivity2::class.java))
                }
            )
        }

        val registerNowText = findViewById<TextView>(R.id.registerNowText)
        registerNowText.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        player.release()
    }
}

@Composable
fun SlidingButton(
    buttonText: String = "Let's Eat", // NEW: default text
    onTrigger: () -> Unit
) {
    val context = LocalContext.current
    val maxOffsetDp = 280.dp
    val maxOffsetPx = with(LocalDensity.current) { maxOffsetDp.toPx() }
    val triggerThresholdPx = maxOffsetPx * 0.75f

    var offset by remember { mutableStateOf(0f) }
    var triggered by remember { mutableStateOf(false) }

    LaunchedEffect(triggered) {
        if (triggered) {
            kotlinx.coroutines.delay(150)
            onTrigger()
        }
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 160.dp),
        contentAlignment = Alignment.BottomCenter
    ) {
        Box(
            modifier = Modifier
                .size(width = 360.dp, height = 74.dp)
                .background(
                    brush = Brush.horizontalGradient(
                        listOf(Color(0xFFC1FF72), Color(0xFF579F4E))
                    ),
                    shape = RoundedCornerShape(50.dp)
                ),
            contentAlignment = Alignment.CenterStart
        ) {
            // Chevron Trail
            Row(
                modifier = Modifier
                    .fillMaxHeight()
                    .padding(end = 20.dp)
                    .align(Alignment.CenterEnd),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    Icons.Filled.ChevronRight,
                    contentDescription = null,
                    tint = Color.White.copy(alpha = 0.25f),
                    modifier = Modifier.size(35.dp).offset(x = 46.dp)
                )
                Icon(
                    Icons.Filled.ChevronRight,
                    contentDescription = null,
                    tint = Color.White.copy(alpha = 0.75f),
                    modifier = Modifier.size(35.dp).offset(x = 23.dp)
                )
                Icon(
                    Icons.Filled.ChevronRight,
                    contentDescription = null,
                    tint = Color(0xFFF5F5F0),
                    modifier = Modifier.size(35.dp)
                )
            }

            // Slider Knob
            Box(
                modifier = Modifier
                    .offset { IntOffset(offset.roundToInt() + 10.dp.roundToPx(), 0) }
                    .size(60.dp)
                    .background(Color(0xFF579F4E), shape = RoundedCornerShape(50.dp))
                    .pointerInput(Unit) {
                        detectHorizontalDragGestures(
                            onDragEnd = {
                                if (offset >= triggerThresholdPx) {
                                    triggered = true
                                } else {
                                    offset = 0f
                                }
                            }
                        ) { _, dragAmount ->
                            if (!triggered) {
                                offset = (offset + dragAmount).coerceIn(0f, maxOffsetPx)
                            }
                        }
                    },
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    Icons.Filled.ChevronRight,
                    contentDescription = "Chevron Right",
                    tint = Color(0xFFF5F5F0),
                    modifier = Modifier.size(35.dp)
                )
            }

            // 🟡 Custom Text Here
            Text(
                text = buttonText,
                color = Color.White,
                fontSize = 20.sp,
                modifier = Modifier.align(Alignment.Center)
            )
        }
    }
}



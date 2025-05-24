package com.example.safebites2

import android.Manifest
import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.camera.core.*
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.snackbar.Snackbar
import com.google.mlkit.vision.barcode.BarcodeScanning
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.text.TextRecognition
import com.google.mlkit.vision.text.latin.TextRecognizerOptions
import java.util.*
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

@ExperimentalGetImage
class CameraPage : AppCompatActivity() {

    private lateinit var previewView: PreviewView
    private lateinit var rootLayout: ConstraintLayout
    private lateinit var captureButton: ImageButton
    private lateinit var bottomSheet: View
    private lateinit var extractedTextView: TextView
    private lateinit var saveToAllergyBtn: Button
    private lateinit var manualInputBtn: Button
    private lateinit var switchModeButton: ImageButton
    private lateinit var modeLabel: TextView
    private var imageCapture: ImageCapture? = null
    private lateinit var cameraExecutor: ExecutorService

    private var isBarcodeMode = false

    private val userAllergens = listOf(
        "milk", "eggs", "fish", "shellfish", "tree nuts", "peanuts", "wheat", "soy", "sesame",
        "gluten", "mustard", "celery", "lupin", "sulfites", "dairy", "corn", "yeast", "mollusks",
        "nightshades", "legumes", "citrus", "tomatoes", "strawberries", "chocolate", "caffeine",
        "food dyes", "alcohol", "gelatin", "msg"
    )

    private val mockProductDB = mapOf(
        "6294001819226" to "Sugar, milk, cocoa, caffeine, nuts.",
        "987654321098" to "Milk, cultures, fruit.",
        "555555555555" to "Wheat, yeast, salt, sugar.",
        "111111111111" to "Water, orange, sugar.",
        "222222222222" to "Caffeine, sugar, flavoring."
    )

    private val permissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { granted ->
        if (granted) startCamera()
        else showSnackbar("Camera permission is required.")
    }

    @SuppressLint("ClickableViewAccessibility", "ResourceType")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_camera_page)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main21)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        previewView = findViewById(R.id.previewView51)
        rootLayout = findViewById(R.id.main21)
        captureButton = findViewById(R.id.captureButton52)
        bottomSheet = findViewById(R.id.bottomSheet55)
        extractedTextView = findViewById(R.id.extractedTextView57)
        saveToAllergyBtn = findViewById(R.id.saveToAllergyBtn58)
        manualInputBtn = findViewById(R.id.manualInputBtn57)
        switchModeButton = findViewById(R.id.switchmodebutton53)
        modeLabel = findViewById(R.id.textView54)

        findViewById<Button>(R.id.backbutton).setOnClickListener {
            startActivity(Intent(this, HomePage::class.java))
        }

        cameraExecutor = Executors.newSingleThreadExecutor()
        requestPermissionAndStartCamera()

        captureButton.setOnClickListener {
            captureButton.isEnabled = false
            captureAndAnalyzeImage()
            captureButton.postDelayed({ captureButton.isEnabled = true }, 1000)
        }

        switchModeButton.setOnClickListener {
            isBarcodeMode = !isBarcodeMode
            showSnackbar("Switched to ${if (isBarcodeMode) "Barcode" else "Text"} mode.")
            captureButton.setImageResource(if (isBarcodeMode) R.drawable.scan else R.drawable.note)
        }

        saveToAllergyBtn.setOnClickListener {
            showSnackbar("Ingredients saved to allergy list!")
        }

        manualInputBtn.setOnClickListener {
            val dialogView = layoutInflater.inflate(R.layout.manualinput, null)
            val editText = dialogView.findViewById<EditText>(R.id.manualEditText61)
            editText.setText(extractedTextView.text.toString())

            AlertDialog.Builder(this)
                .setTitle("Edit Scanned Text")
                .setView(dialogView)
                .setPositiveButton("Save") { _, _ ->
                    val editedText = editText.text.toString().trim()
                    extractedTextView.text = highlightAllergens(capitalizeWords(editedText))
                    showSnackbar("Text updated.")
                }
                .setNegativeButton("Cancel", null)
                .create()
                .show()
        }

        rootLayout.setOnTouchListener { v, event ->
            if (event.action == MotionEvent.ACTION_DOWN) {
                val location = IntArray(2)
                bottomSheet.getLocationOnScreen(location)
                val x = event.rawX
                val y = event.rawY
                if (x < location[0] || x > location[0] + bottomSheet.width ||
                    y < location[1] || y > location[1] + bottomSheet.height
                ) {
                    bottomSheet.visibility = View.GONE
                    v.performClick()
                }
            }
            true
        }
    }

    private fun requestPermissionAndStartCamera() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
            == PackageManager.PERMISSION_GRANTED
        ) {
            startCamera()
        } else {
            permissionLauncher.launch(Manifest.permission.CAMERA)
        }
    }

    private fun startCamera() {
        val cameraProviderFuture = ProcessCameraProvider.getInstance(this)
        cameraProviderFuture.addListener({
            val cameraProvider = cameraProviderFuture.get()
            val preview = Preview.Builder().build().also {
                it.surfaceProvider = previewView.surfaceProvider
            }

            imageCapture = ImageCapture.Builder().build()
            val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA

            try {
                cameraProvider.unbindAll()
                cameraProvider.bindToLifecycle(this, cameraSelector, preview, imageCapture)
            } catch (e: Exception) {
                Log.e("Camera", "Failed to bind camera use cases: ${e.message}", e)
                showSnackbar("Failed to start camera.")
            }
        }, ContextCompat.getMainExecutor(this))
    }

    private fun captureAndAnalyzeImage() {
        val capture = imageCapture ?: return
        capture.takePicture(
            ContextCompat.getMainExecutor(this),
            object : ImageCapture.OnImageCapturedCallback() {
                override fun onCaptureSuccess(imageProxy: ImageProxy) {
                    imageProxy.image?.let { mediaImage ->
                        val image = InputImage.fromMediaImage(mediaImage, imageProxy.imageInfo.rotationDegrees)
                        if (isBarcodeMode) analyzeImageForBarcode(image)
                        else analyzeImageForText(image)
                    }
                    imageProxy.close()
                }

                override fun onError(exception: ImageCaptureException) {
                    Log.e("ImageCapture", "Capture failed: ${exception.message}", exception)
                    showSnackbar("Capture failed.")
                }
            }
        )
    }

    private fun analyzeImageForText(image: InputImage) {
        val recognizer = TextRecognition.getClient(TextRecognizerOptions.DEFAULT_OPTIONS)
        recognizer.process(image)
            .addOnSuccessListener { visionText ->
                val extracted = visionText.text.trim()
                extractedTextView.text =
                    if (extracted.isEmpty()) "No text was scanned."
                    else highlightAllergens(capitalizeWords(formatIngredients(extracted)))
                bottomSheet.visibility = View.VISIBLE
            }
            .addOnFailureListener { e ->
                Log.e("TextRecognition", "Text recognition failed.", e)
                showSnackbar("Text recognition failed: ${e.message}")
            }
    }

    private fun analyzeImageForBarcode(image: InputImage) {
        val scanner = BarcodeScanning.getClient()
        scanner.process(image)
            .addOnSuccessListener { barcodes ->
                if (barcodes.isEmpty()) {
                    showSnackbar("No barcode found.")
                    return@addOnSuccessListener
                }

                val results = StringBuilder()
                for (barcode in barcodes) {
                    val code = barcode.rawValue ?: "Unknown"
                    results.append(mockProductDB[code] ?: "Product not found for barcode: $code")
                }

                extractedTextView.text = highlightAllergens(results.toString())
                bottomSheet.visibility = View.VISIBLE
            }
            .addOnFailureListener { e ->
                Log.e("BarcodeScanner", "Barcode scanning failed.", e)
                showSnackbar("Barcode scanning failed: ${e.message}")
            }
    }

    private fun formatIngredients(text: String): String =
        text.split(",", "\n").map { it.trim() }.filter { it.isNotEmpty() }.joinToString(", ") + "."

    private fun capitalizeWords(text: String): String =
        text.split(" ").joinToString(" ") { it.replaceFirstChar { c -> c.titlecase(Locale.getDefault()) } }

    private fun highlightAllergens(text: String): SpannableString {
        val spannable = SpannableString(text)
        val lower = text.lowercase(Locale.getDefault())
        userAllergens.forEach { allergen ->
            var start = lower.indexOf(allergen)
            while (start >= 0) {
                spannable.setSpan(
                    ForegroundColorSpan(Color.RED),
                    start,
                    start + allergen.length,
                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                )
                start = lower.indexOf(allergen, start + allergen.length)
            }
        }
        return spannable
    }

    private fun showSnackbar(message: String) {
        Snackbar.make(rootLayout, message, Snackbar.LENGTH_SHORT).show()
    }

    override fun onDestroy() {
        super.onDestroy()
        cameraExecutor.shutdown()
    }
}
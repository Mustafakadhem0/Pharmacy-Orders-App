package com.mustafa.pharmacyorders

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.Gravity
import android.view.ScaleGestureDetector
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    data class OrderItem(
        val name: String,
        val quantity: Int,
        val store: String
    )

    private val medicines = mutableListOf(
        "Panadol",
        "Augmentin",
        "Flagyl",
        "Glucophage",
        "Neurobin",
        "Mamacare",
        "Pregabalin",
        "Duphaston"
    )

    private val photos = mutableListOf<Uri>()
    private val order = mutableListOf<OrderItem>()

    private var photoIndex = 0
    private var medicineIndex = 0
    private var selectedStore = ""
    private var zoom = 1f

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        receivePhotos(intent)
        showHome()
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)

        setIntent(intent)
        receivePhotos(intent)
        showOrderScreen()
    }

    private fun receivePhotos(intent: Intent) {

        when (intent.action) {

            Intent.ACTION_SEND -> {
                val uri =
                    intent.getParcelableExtra<Uri>(
                        Intent.EXTRA_STREAM
                    )

                if (uri != null) {
                    photos.clear()
                    photos.add(uri)
                    photoIndex = 0
                }
            }

            Intent.ACTION_SEND_MULTIPLE -> {
                val list =
                    intent.getParcelableArrayListExtra<Uri>(
                        Intent.EXTRA_STREAM
                    )

                if (!list.isNullOrEmpty()) {
                    photos.clear()
                    photos.addAll(list)
                    photoIndex = 0
                }
            }
        }
    }
    private fun showHome() {

        val box = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            gravity = Gravity.CENTER
            setPadding(30, 30, 30, 30)
        }

        val title = TextView(this).apply {
            text = "صيدليتي"
            textSize = 32f
            gravity = Gravity.CENTER
        }

        val subtitle = TextView(this).apply {
            text = "الطلبية اليومية"
            textSize = 20f
            gravity = Gravity.CENTER
        }

        val start = Button(this).apply {
            text = "هيا نبدأ"
            textSize = 20f

            setOnClickListener {
                showOrderScreen()
            }
        }

        box.addView(title)
        box.addView(subtitle)
        box.addView(start)

        setContentView(box)
    }

    private fun showOrderScreen() {

        val scroll = ScrollView(this)

        val box = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            gravity = Gravity.CENTER_HORIZONTAL
            setPadding(20, 20, 20, 40)
        }

        val counter = TextView(this).apply {
            gravity = Gravity.CENTER
            textSize = 16f
        }

        val image = ImageView(this).apply {
            scaleType = ImageView.ScaleType.FIT_CENTER
            adjustViewBounds = true
        }

        if (photos.isEmpty()) {
            counter.text = "لا توجد صور رفوف"
        } else {
            counter.text =
                "صورة ${photoIndex + 1} من ${photos.size}"

            image.setImageURI(
                photos[photoIndex]
            )
        }

        val detector =
            ScaleGestureDetector(
                this,
                object :
                    ScaleGestureDetector.SimpleOnScaleGestureListener() {

                    override fun onScale(
                        detector: ScaleGestureDetector
                    ): Boolean {

                        zoom *= detector.scaleFactor
                        zoom = zoom.coerceIn(1f, 5f)

                        image.scaleX = zoom
                        image.scaleY = zoom

                        return true
                    }
                }
            )

        image.setOnTouchListener { _, event ->
            detector.onTouchEvent(event)
            true
        }

        box.addView(counter)

        box.addView(
            image,
            LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                700
            )
        )
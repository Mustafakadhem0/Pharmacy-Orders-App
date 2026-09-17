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
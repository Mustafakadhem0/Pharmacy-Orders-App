package com.mustafa.pharmacyorders

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.Gravity
import android.view.ScaleGestureDetector
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.ScrollView
import android.widget.TextView
import android.widget.Toast
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

    private val images = mutableListOf<Uri>()
    private val order = mutableListOf<OrderItem>()

    private var imageIndex = 0
    private var medicineIndex = 0
    private var selectedStore = ""
    private var zoomScale = 1f

    private lateinit var imageView: ImageView
    private lateinit var imageCounter: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        receiveSharedImages(intent)
        showMainScreen()
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)

        setIntent(intent)
        receiveSharedImages(intent)
        showMainScreen()
    }

    private fun receiveSharedImages(intent: Intent) {

        when (intent.action) {

            Intent.ACTION_SEND -> {

                val uri =
                    intent.getParcelableExtra<Uri>(
                        Intent.EXTRA_STREAM
                    )

                if (uri != null) {
                    images
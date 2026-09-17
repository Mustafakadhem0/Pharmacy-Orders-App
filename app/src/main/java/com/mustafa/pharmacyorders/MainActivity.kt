package com.mustafa.pharmacyorders

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.Gravity
import android.view.ScaleGestureDetector
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private val images = mutableListOf<Uri>()
    private var imageIndex = 0
    private var scale = 1f
    private lateinit var imageView: ImageView
    private lateinit var imageCounter: TextView

    private val medicines = mutableListOf(
        "Panadol", "Augmentin", "Flagyl
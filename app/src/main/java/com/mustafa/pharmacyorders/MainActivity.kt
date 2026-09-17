package com.mustafa.pharmacyorders

import android.os.Bundle
import android.view.Gravity
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val root = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            gravity = Gravity.CENTER
            setPadding(40, 40, 40, 40)
        }

        val title = TextView(this).apply {
            text = "صيدليتي"
            textSize = 32f
            gravity = Gravity.CENTER
        }

        val subtitle = TextView(this).apply {
            text = "نظام الطلبية اليومية"
            textSize = 20f
            gravity = Gravity.CENTER
        }

        val start = Button(this).apply {
            text = "هيا نبدأ"
            textSize = 20f
        }

        root.addView(title)
        root.addView(subtitle)
        root.addView(start)

        setContentView(root)
    }
}
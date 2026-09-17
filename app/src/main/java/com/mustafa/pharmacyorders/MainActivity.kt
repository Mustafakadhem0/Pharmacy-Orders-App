package com.mustafa.pharmacyorders

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.Gravity
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private val photos = mutableListOf<Uri>()
    private var photoIndex = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        home()
    }

    private fun home() {
        val box = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            gravity = Gravity.CENTER
            setPadding(30, 30, 30, 30)
        }

        val title = TextView(this).apply {
            text = "صيدليتي"
            textSize = 32f
        }

        val start = Button(this).apply {
            text = "هيا نبدأ"
            setOnClickListener { photosScreen() }
        }

        box.addView(title)
        box.addView(start)
        setContentView(box)
    }

    private fun photosScreen() {
        val box = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            gravity = Gravity.CENTER_HORIZONTAL
            setPadding(20, 20, 20, 20)
        }

        val counter = TextView(this)
        val image = ImageView(this).apply {
            scaleType = ImageView.ScaleType.FIT_CENTER
        }

        fun showPhoto() {
            if (photos.isEmpty()) {
                counter.text = "لا توجد صور"
                image.setImageDrawable(null)
            } else {
                counter.text = "صورة ${photoIndex + 1} من ${photos.size}"
                image.setImageURI(photos[photoIndex])
            }
        }

        val choose = Button(this).apply {
            text = "اختيار صور الرفوف"
            setOnClickListener {
                val i = Intent(Intent.ACTION_OPEN_DOCUMENT).apply {
                    type = "image/*"
                    putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
                }
                startActivityForResult(i, 10)
            }
        }

        val previous = Button(this).apply {
            text = "الصورة السابقة"
            setOnClickListener {
                if (photos.isNotEmpty()) {
                    photoIndex =
                        if (photoIndex == 0) photos.lastIndex
                        else photoIndex - 1
                    photosScreen()
                }
            }
        }

        val next = Button(this).apply {
            text = "الصورة التالية"
            setOnClickListener {
                if (photos.isNotEmpty()) {
                    photoIndex =
                        if (photoIndex == photos.lastIndex) 0
                        else photoIndex + 1
                    photosScreen()
                }
            }
        }

        box.addView(counter)

        box.addView(
            image,
            LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                800
            )
        )

        box.addView(choose)
        box.addView(previous)
        box.addView(next)

        setContentView(box)
        showPhoto()
    }

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(
        requestCode: Int,
        resultCode: Int,
        data: Intent?
    ) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 10 && resultCode == Activity.RESULT_OK) {
            photos.clear()

            val clip = data?.clipData

            if (clip != null) {
                for (i in 0 until clip.itemCount) {
                    photos.add(clip.getItemAt(i).uri)
                }
            } else {
                data?.data?.let { photos.add(it) }
            }

            photoIndex = 0
            photosScreen()
        }
    }
}
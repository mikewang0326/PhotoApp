package com.mike.photoapp.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.mike.photoapp.R
import com.mike.photoapp.models.Photo
import kotlinx.android.synthetic.main.activity_main.*

class DetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        setSupportActionBar(toolbar)

        val imageView  = findViewById(R.id.imageView) as ImageView
        val photo = intent.getSerializableExtra(PHOTO) as Photo?

        photo?.webformatUrl.let {
            Glide.with(this).load(photo?.previewURL).into(imageView)
        }

        imageView.setOnClickListener {
            finish()
        }
    }

    companion object {
        val PHOTO = "PHOTO"
    }
}

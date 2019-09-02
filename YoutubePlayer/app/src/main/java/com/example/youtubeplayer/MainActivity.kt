package com.example.youtubeplayer

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*
import java.lang.IllegalArgumentException

class MainActivity : AppCompatActivity(), View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnStandalone.setOnClickListener(this)
        btnPlaySingleVideo.setOnClickListener(this)
    }

    override fun onClick(view: View?) {
        val intent = when (view?.id) {
            R.id.btnStandalone -> Intent(this, StandaloneActivity::class.java)
            R.id.btnPlaySingleVideo -> Intent(this, YoutubeActivity::class.java)
            else -> throw IllegalArgumentException("Undefined button clicked")
        }

        startActivity(intent)
    }
}

package com.example.youtubeplayer

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.google.android.youtube.player.YouTubeStandalonePlayer
import kotlinx.android.synthetic.main.activity_standalone.*

class StandaloneActivity : AppCompatActivity(), View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_standalone)

        btnPlayVideo.setOnClickListener(this)
        btnPlayPlaylist.setOnClickListener(this)

//        btnPlayVideo.setOnClickListener(object: View.OnClickListener {
//            override fun onClick(p0: View?) {
//                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
//            }
//        })

//        btnPlayVideo.setOnClickListener {
//        }
//
//        btnPlayVideo.setOnClickListener(View.OnClickListener { View ->
//        })

//        val listener = View.OnClickListener {
//
//        }
//
//        btnPlayVideo.setOnClickListener(listener)
//        btnPlayPlaylist.setOnClickListener(listener)
    }

    override fun onClick(view: View?) {
        val intent = when (view?.id) {
            R.id.btnPlayVideo -> YouTubeStandalonePlayer.createVideoIntent(this, getString(R.string.google_api_key), YOUTUBE_VIDEO_ID, 0, true, false)
            R.id.btnPlayPlaylist -> YouTubeStandalonePlayer.createPlaylistIntent(this, getString(R.string.google_api_key), YOUTUBE_PLAYLIST, 0, 0, true, true)
            else -> throw IllegalArgumentException("Undefined button clicked")
        }

        startActivity(intent)
    }
}
package com.example.youtubeplayer

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import com.google.android.youtube.player.YouTubeBaseActivity
import com.google.android.youtube.player.YouTubeInitializationResult
import com.google.android.youtube.player.YouTubePlayer
import com.google.android.youtube.player.YouTubePlayerView

const val YOUTUBE_VIDEO_ID = "AwQHqWyHRpU"
const val YOUTUBE_PLAYLIST = "PLA89DCFA6ADACE599"

class YoutubeActivity : YouTubeBaseActivity(), YouTubePlayer.OnInitializedListener {

    private val TAG = "YoutubeActivity"
    private val DIALOG_REQUEST_CODE = 1
    private val playerView by lazy { YouTubePlayerView(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_youtube)
//        val layout = findViewById<ConstraintLayout>(R.id.activity_youtube)

        val layout = layoutInflater.inflate(R.layout.activity_youtube, null) as ConstraintLayout
        setContentView(layout)

//        val button1 = Button(this)
//        button1.layoutParams = ConstraintLayout.LayoutParams(600, 180)
//        button1.text = "Button Added"
//        layout.addView(button1)

        playerView.layoutParams = ConstraintLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)

        layout.addView(playerView)

        playerView.initialize(getString(R.string.google_api_key), this)
    }

    override fun onInitializationSuccess(provider: YouTubePlayer.Provider?, player: YouTubePlayer?, wasRestored: Boolean) {
        //TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        Log.d(TAG, "onInitializationSuccess: provider is ${provider?.javaClass}")
        Log.d(TAG, "onInitializationSuccess: player is ${player?.javaClass}")
        Toast.makeText(this, "Initialized YouTube player successfully", Toast.LENGTH_SHORT).show()

        player?.setPlayerStateChangeListener(playerStateChangeListener)
        player?.setPlaybackEventListener(playbackEventListener)
        if (!wasRestored) {
            //player?.cueVideo(YOUTUBE_VIDEO_ID)
            player?.loadVideo(YOUTUBE_VIDEO_ID)
        } else {
            player?.play()
        }
    }

    override fun onInitializationFailure(provider: YouTubePlayer.Provider?, youTubeInitializationResult: YouTubeInitializationResult?) {

        val REQUEST_CODE = 0
        if (youTubeInitializationResult?.isUserRecoverableError == true) {
            youTubeInitializationResult.getErrorDialog(this, DIALOG_REQUEST_CODE).show()
        } else {
            val errorMessage = "There was an error Initializing youtubePlayer"
            Toast.makeText(this, errorMessage, Toast.LENGTH_LONG).show()
        }
    }

    private val playbackEventListener = object: YouTubePlayer.PlaybackEventListener {
        override fun onSeekTo(p0: Int) {
        }

        override fun onBuffering(p0: Boolean) {
        }

        override fun onPlaying() {
            Toast.makeText(this@YoutubeActivity, "Video is playing", Toast.LENGTH_SHORT).show()
        }

        override fun onStopped() {
            Toast.makeText(this@YoutubeActivity, "Video has stopped", Toast.LENGTH_SHORT).show()
        }


        override fun onPaused() {
            Toast.makeText(this@YoutubeActivity, "Video has paused", Toast.LENGTH_SHORT).show()
        }

    }

    private val playerStateChangeListener = object: YouTubePlayer.PlayerStateChangeListener {
        override fun onAdStarted() {
            Toast.makeText(this@YoutubeActivity, "Ad Started", Toast.LENGTH_SHORT).show()
        }

        override fun onLoading() {
        }

        override fun onVideoStarted() {
            Toast.makeText(this@YoutubeActivity, "Video Started", Toast.LENGTH_SHORT).show()
        }

        override fun onLoaded(p0: String?) {
        }

        override fun onVideoEnded() {
            Toast.makeText(this@YoutubeActivity, "Video Ended", Toast.LENGTH_SHORT).show()
        }

        override fun onError(p0: YouTubePlayer.ErrorReason?) {
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        Log.d(TAG, "onActivityResult called with response $resultCode for request $requestCode")

        if (requestCode == DIALOG_REQUEST_CODE) {
            Log.d(TAG, "${intent?.toString()}")
            Log.d(TAG, "${intent?.extras?.toString()}")

            playerView.initialize(getString(R.string.google_api_key), this)
        }
    }
}

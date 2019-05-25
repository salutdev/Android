package com.example.chat

import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_create_user.*
import kotlin.random.Random

class CreateUserActivity : AppCompatActivity() {

    var userAvatar = "profileDefault"
    var avatarColor = "[0.5, 0.5, 0.5, 1]"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_user)
    }

    fun generateUserAvatar(view: View) {
        val imageIndex = Random.nextInt(28)
        val color = Random.nextInt(2)

        if (color == 0) {
            userAvatar = "light$imageIndex"
        } else {
            userAvatar = "dark$imageIndex"
        }

        val resoureId = resources.getIdentifier(userAvatar, "drawable", packageName)
        createAvatarImageView.setImageResource(resoureId)
    }

    fun backgroundColorBtnClicked(view: View) {
        val r = Random.nextInt(256)
        val g = Random.nextInt(256)
        val b = Random.nextInt(256)

        createAvatarImageView.setBackgroundColor(Color.rgb(r, g, b))

        val savedR = r.toDouble() / 255
        val savedG = g.toDouble() / 255
        val savedB = b.toDouble() / 255

        avatarColor = "[$savedR, $savedG, $savedB, 1]"
    }

    fun createUserBtnClicked(view: View) {

    }
}
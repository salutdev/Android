package com.example.chat.Services

import android.graphics.Color
import com.example.chat.Controller.App
import java.util.*

object UserDataService {
    var id = ""
    var avatarColor = ""
    var avatarName = ""
    var email = ""
    var name = ""

    fun logout() {
        id = ""
        avatarColor = ""
        avatarName = ""
        email = ""
        name = ""

        App.prefs.authToken = ""
        App.prefs.userEmail = ""
        App.prefs.isLoggedIn = false
    }

    fun getAvatarColor() : Int {

        val strippedColor = avatarColor
            .replace("[", "")
            .replace("]", "")
            .replace(",", "")

        var r = 0
        var g = 0
        var b = 0
        val scanner = Scanner(strippedColor)

        if (scanner.hasNext()) {
            r = (scanner.nextDouble() * 255).toInt()
        }

        if (scanner.hasNext()) {
            g = (scanner.nextDouble() * 255).toInt()
        }

        if (scanner.hasNext()) {
            b = (scanner.nextDouble() * 255).toInt()
        }

        return Color.rgb(r, g, b)
    }
}
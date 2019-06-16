package com.example.chat.Model

data class Message constructor(val message: String, val userName: String, val channelId: String,
                               val userAvatar: String, val avatarColor: String, val id: String,
                               val timeStamp: String)
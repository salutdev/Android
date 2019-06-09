package com.example.chat.Utilities

const val BASE_URL = "https://chat12345678.herokuapp.com/v1"
const val SOCKET_URL = "https://chat12345678.herokuapp.com/"
const val URL_REGISTER = "${BASE_URL}/account/register"
const val URL_LOGIN = "${BASE_URL}/account/login"
const val URL_CREATE_USER = "${BASE_URL}/user/add"
const val BROADCAST_USER_DATA_CHANGE = "BROADCAST_USER_DATA_CHANGE"
const val URL_GET_USER = "${BASE_URL}/user/byEmail/"
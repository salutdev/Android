package com.example.chat.Services

import android.content.Context
import android.util.Log
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley
import com.example.chat.Model.Channel
import com.example.chat.Utilities.URL_GET_CHANNELS
import java.lang.Exception

object MessageService {
    val channels = ArrayList<Channel>()

    fun getChannels(context: Context, complete: (Boolean) -> Unit) {
        val channelsRequest = object : JsonArrayRequest(Method.GET, URL_GET_CHANNELS, null,
            Response.Listener {response ->
                try {
                    for (i in 0 until response.length()) {
                        val channel = response.getJSONObject(i)
                        val name = channel.getString("name")
                        val desc = channel.getString("description")
                        val id = channel.getString("_id")

                        val newChannel = Channel(name, desc, id)
                        this.channels.add(newChannel)
                        complete(true)
                    }
                } catch (e: Exception) {
                    Log.d("JSON", "Exception: ${e.localizedMessage}")
                    complete(false)
                }
            },
            Response.ErrorListener {error ->
                Log.d("ERROR", "Could not retrieve channels")
                complete(false)
            }) {
            override fun getBodyContentType(): String {
                return "application/json; charset=utf-8"
            }

            override fun getHeaders(): MutableMap<String, String> {
                val headers = HashMap<String, String>()
                headers.put("Authorization", "Bearer ${AuthService.authToken}")
                return headers
            }
        }

        Volley.newRequestQueue(context).add(channelsRequest)
    }
}
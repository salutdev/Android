package com.example.chat.Adapters

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.example.chat.Model.Message
import com.example.chat.R
import com.example.chat.Services.UserDataService
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class MessageAdapter(val context: Context, val messages: ArrayList<Message>) : RecyclerView.Adapter<MessageAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.message_list_view, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return messages.count()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindMessage(context, messages[position])
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val userImage = itemView.findViewById<ImageView>(R.id.messageUserImage)
        val timeStamp = itemView.findViewById<TextView>(R.id.timeStampLbl)
        val userName = itemView.findViewById<TextView>(R.id.messageUserNameLbl)
        val messageBody = itemView.findViewById<TextView>(R.id.messageBodyLbl)

        fun bindMessage(context: Context, message: Message) {
            val resourceId = context.resources.getIdentifier(message.userAvatar, "drawable", context.packageName)
            userImage?.setImageResource(resourceId)
            userImage?.setBackgroundColor(UserDataService.getAvatarColor(message.avatarColor))
            userName?.text = message.userName
            timeStamp?.text = formatDate(message.timeStamp)
            messageBody?.text = message.message
        }

        fun formatDate(isoDateString: String) : String {

            val isoFormatter = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
            isoFormatter.timeZone = TimeZone.getTimeZone("UTC")

            var convertedDate = Date()

            try {
                convertedDate = isoFormatter.parse(isoDateString)
            }
            catch (e: ParseException) {
                Log.d("PARSE", "Cannot parse date")
            }

            val outDateFormat = SimpleDateFormat("E, h:mm a", Locale.getDefault())
            return outDateFormat.format(convertedDate)
        }
    }
}
package com.example.chat.Controller

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.graphics.Color
import android.os.Bundle
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v4.widget.DrawerLayout
import android.support.design.widget.NavigationView
import android.support.v4.content.LocalBroadcastManager
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.ArrayAdapter
import android.widget.EditText
import com.example.chat.Model.Channel
import com.example.chat.Model.Message
import com.example.chat.R
import com.example.chat.Services.AuthService
import com.example.chat.Services.MessageService
import com.example.chat.Services.UserDataService
import com.example.chat.Utilities.BROADCAST_USER_DATA_CHANGE
import com.example.chat.Utilities.SOCKET_URL
import io.socket.client.IO
import io.socket.emitter.Emitter
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*
import kotlinx.android.synthetic.main.nav_header_main.*

class MainActivity : AppCompatActivity() {

    val socket = IO.socket(SOCKET_URL)
    lateinit var channelsAdapter: ArrayAdapter<Channel>
    var selectedChannel: Channel? = null

    private  fun setupAdapters() {
        channelsAdapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, MessageService.channels)
        channel_list.adapter = channelsAdapter
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        val navView: NavigationView = findViewById(R.id.nav_view)
        val toggle = ActionBarDrawerToggle(
            this, drawerLayout, toolbar,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        LocalBroadcastManager.getInstance(this).registerReceiver(userDataChangeReceiver, IntentFilter(
            BROADCAST_USER_DATA_CHANGE))

        socket.connect()
        socket.on("channelCreated", onNewChannel)
        socket.on("messageCreated", onNewMessage)

        setupAdapters()

        channel_list.setOnItemClickListener { _, _, position, _ ->
            selectedChannel = MessageService.channels[position]
            drawerLayout.closeDrawer(GravityCompat.START)
            updateWithChannel()
        }

        if (App.prefs.isLoggedIn) {
            AuthService.findUser(this) {}
        }

        addChannelBtn.setOnClickListener {
            if (App.prefs.isLoggedIn) {
                val builder = AlertDialog.Builder(this)
                val dialogView = layoutInflater.inflate(R.layout.add_channel_dialog, null)
                builder.setView(dialogView)
                    .setPositiveButton("Add") {_, _ ->
                        val nameTextField = dialogView.findViewById<EditText>(R.id.addChannelNameTxt)
                        val descTextField = dialogView.findViewById<EditText>(R.id.addChannelDescTxt)
                        val channelName = nameTextField.text.toString()
                        val channelDesc = descTextField.text.toString()

                        socket.emit("newChannel", channelName, channelDesc)
                    }
                    .setNegativeButton("Cancel") {_, _ ->

                    }
                    .show()
            }
        }
    }

    private val onNewChannel = Emitter.Listener {args ->
        if (App.prefs.isLoggedIn) {
            runOnUiThread {
                val name = args[0] as String
                val description = args[1] as String
                val id = args[2] as String

                val newChannel = Channel(name, description, id)
                MessageService.channels.add(newChannel)

                println(newChannel.name)
                println(newChannel.description)
                println(newChannel.id)
                channelsAdapter.notifyDataSetChanged()
            }
        }
    }

    private val onNewMessage = Emitter.Listener { args ->
        if (App.prefs.isLoggedIn) {
            runOnUiThread {
                val channelId = args[2] as String
                if (channelId == selectedChannel?.id) {
                    val msgBody = args[0] as String
                    val userName = args[3] as String
                    val userAvatar = args[4] as String
                    val avatarColor = args[5] as String
                    val id = args[6] as String
                    val timeStamp = args[7] as String

                    val newMessage = Message(msgBody, userName, channelId, userAvatar, avatarColor, id, timeStamp)
                    MessageService.messages.add(newMessage)
                    println(newMessage.message)
                }
            }
        }
    }

    override fun onDestroy() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(userDataChangeReceiver)
        socket.disconnect()
        super.onDestroy()
    }

    private val userDataChangeReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent?) {
            if (App.prefs.isLoggedIn) {
                userNameNavHeader.text = UserDataService.name
                userEmailNavHeader.text = UserDataService.email

                val resourceId = resources.getIdentifier(UserDataService.avatarName, "drawable", packageName)
                userImageNavHeader.setImageResource(resourceId)
                userImageNavHeader.setBackgroundColor(UserDataService.getAvatarColor())

                loginBtnNavHeader.text = "Logout"

                MessageService.getChannels {complete ->
                    if (complete) {
                        if (MessageService.channels.count() > 0) {
                            selectedChannel = MessageService.channels[0]
                            channelsAdapter.notifyDataSetChanged()
                            updateWithChannel()
                        }
                    }
                }
            }
        }
    }

    fun updateWithChannel() {
        mainChannelName.text = "#${selectedChannel?.name}"

        if (selectedChannel != null) {
            MessageService.getMessages(selectedChannel!!.id) {complete ->
                if (complete) {
                    for (msg in MessageService.messages) {
                        println(msg.message)
                    }
                }
            }
        }
    }

    fun hideKeyboard() {
        val inputManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        if (inputManager.isAcceptingText) {
            inputManager.hideSoftInputFromWindow(currentFocus.windowToken, 0)
        }
    }

    override fun onBackPressed() {
        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    fun loginBtnNavHeaderClicked(view: View) {
        if (App.prefs.isLoggedIn) {
            UserDataService.logout()
            userNameNavHeader.text = ""
            userEmailNavHeader.text = ""
            userImageNavHeader.setImageResource(R.drawable.profiledefault)
            userImageNavHeader.setBackgroundColor(Color.TRANSPARENT)
            loginBtnNavHeader.text = "Login"
            MessageService.channels.clear()
            channelsAdapter.notifyDataSetChanged()
            mainChannelName.setText("Please Log In")
        } else {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
    }

    fun sendMessageBtnClicked(view: View) {
        if (App.prefs.isLoggedIn && msgTxt.text.isNotEmpty() && selectedChannel != null) {
            val userId = UserDataService.id
            val channelId = selectedChannel?.id
            socket.emit("newMessage", msgTxt.text.toString(), userId, channelId,
                UserDataService.name, UserDataService.avatarName, UserDataService.avatarColor)

            msgTxt.text.clear()
        }

        hideKeyboard()
    }
}

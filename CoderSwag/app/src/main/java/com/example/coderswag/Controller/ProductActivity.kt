package com.example.coderswag.Controller

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.coderswag.R
import com.example.coderswag.Utitities.EXTRA_CATEGERY

class ProductActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product)

        val categoryTitle = intent.getStringExtra(EXTRA_CATEGERY)
        Toast.makeText(this, "$categoryTitle is clicked", Toast.LENGTH_SHORT).show()
    }
}

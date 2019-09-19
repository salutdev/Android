package com.example.flickrbrowser

import android.os.Bundle
import android.util.Log
import kotlinx.android.synthetic.main.activity_search.*

class SearchActivity : BaseActivity() {

    private val TAG = "SearchActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d(TAG, "onCreate starts")
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

//        setSupportActionBar(toolbar)
//        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        activateToolbar(true)
        Log.d(TAG, "onCreate ends")
    }

}

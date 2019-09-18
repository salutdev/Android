package com.example.flickrbrowser

import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.widget.LinearLayout
import androidx.recyclerview.widget.LinearLayoutManager

import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*

class MainActivity : AppCompatActivity(), GetRawData.OnDownloadComplete, GetFlickrJsonData.OnDataAvailable {

    private val TAG = "MainActivity"
    private val flickrRecyclerViewAdapter = FlickrRecyclerViewAdapter(ArrayList())

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d(TAG, "onCreate called")
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        recycler_view.layoutManager = LinearLayoutManager(this)
        recycler_view.adapter = flickrRecyclerViewAdapter

        val url = createUri("https://www.flickr.com/services/feeds/photos_public.gne", "android,oreo,sdk", "en-us", true)
        val getRawData = GetRawData(this)
        //getRawData.setDownloadCompleteListener(this)
        //getRawData.execute("https://www.flickr.com/services/feeds/photos_public.gne?tag=android,oreo,sdk&format=json&nojsoncallback=1")
        getRawData.execute(url)

//        fab.setOnClickListener { view ->
//            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                .setAction("Action", null).show()
//        }

        Log.d(TAG, "onCreate ends")
    }

    private fun createUri(baseUrl: String, searchCriteria: String, lang: String, matchAll: Boolean): String {
        Log.d(TAG, "createUri starts")

        return  Uri.parse(baseUrl)
            .buildUpon()
            .appendQueryParameter("tag", searchCriteria)
            .appendQueryParameter("tagmode", if (matchAll) "ALL" else "ANY")
            .appendQueryParameter("lang", lang)
            .appendQueryParameter("format", "json")
            .appendQueryParameter("nojsoncallback", "1")
            .build()
            .toString()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        Log.d(TAG, "onCreateOptionsMenu called")
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        Log.d(TAG, "onOptionsItemSelected called")
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

//    companion object {
//        private const val TAG = "MainActivity"
//    }

    override fun onDownloadComplete(data: String, status: DownloadStatus) {
        if (status == DownloadStatus.OK) {
            Log.d(TAG, "onDownloadComplete is called")

            val getFlickrJsonData = GetFlickrJsonData(this)
            getFlickrJsonData.execute(data)
        } else {
            Log.d(TAG, "onDownloadComplete failed with status ${status}. Error message is ${data}")
        }
    }

    override fun onDataAvailable(data: List<Photo>) {
        Log.d(TAG, "onDataAvailable called. Data is ${data}.")
        flickrRecyclerViewAdapter.loadNewData(data)
        Log.d(TAG, "onDataAvailable ends.")
    }

    override fun onError(exception: Exception) {
        Log.d(TAG, "onError called. Error is ${exception.message}.")

        Log.d(TAG, "onError ends.")
    }
}

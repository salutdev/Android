package com.example.downloader

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import java.util.zip.Inflater

class FeedAdapter(context: Context, private val resource: Int, private val movies: List<FeedEntry>) : ArrayAdapter<FeedEntry>(context, resource) {
    private  val TAG = "FeedAdapter"
    private  val inflater = LayoutInflater.from(context)

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        Log.d(TAG, "getView called")

        val view: View
        if (convertView == null) {
            Log.d(TAG, "getView called with null convertView")
            view = inflater.inflate(resource, parent, false)
        } else {
            Log.d(TAG, "getView provided a convertView")
            view = convertView
        }

        val tvName = view.findViewById<TextView>(R.id.tvName)
        val tvArtist = view.findViewById<TextView>(R.id.tvArtist)
        val tvSummary = view.findViewById<TextView>(R.id.tvSummary)

        val currentMovie = movies[position]
        tvName.text = currentMovie.name
        tvArtist.text = currentMovie.artist
        tvSummary.text = currentMovie.summary

        return view
    }

    override fun getCount(): Int {
        Log.d(TAG, "getCount called")
        return movies.count()
    }
}
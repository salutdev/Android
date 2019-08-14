package com.example.downloader

import android.content.Context
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.ListView
import kotlinx.android.synthetic.main.activity_main.*
import java.net.URL
import kotlin.properties.Delegates

class FeedEntry {
    var name: String = ""
    var artist: String = ""
    var releaseDate: String = ""
    var summary: String = ""
    var imageUrl: String = ""

    override fun toString(): String {
        return """
            name = $name
            artist = $artist
            releaseDate = $releaseDate
            imageUrl = $imageUrl
        """.trimIndent()
    }
}

class MainActivity : AppCompatActivity() {
    private val TAG = "MainActivity"
    //private val downloadData by lazy { DownloadData(this, xmlListView) }
    private var downloadData: DownloadData? = null

    private val feedMoviesUrl = "http://ax.itunes.apple.com/WebObjects/MZStoreServices.woa/ws/RSS/topMovies/xml"
    private val feedTopSongsUrlTemplate = "http://ax.itunes.apple.com/WebObjects/MZStoreServices.woa/ws/RSS/topsongs/limit=%d/xml"
    private val feedTopTVSeasonsUrl = "http://ax.itunes.apple.com/WebObjects/MZStoreServices.woa/ws/RSS/topTvSeasons/xml"

    private val STATE_URL = "StateUrl"
    private val STATE_REC_NUM = "StateRecNum"
    private var currentUrl = ""
    private var currentRecNum = 10

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Log.d(TAG, "onCreate: called")
        setContentView(R.layout.activity_main)
        var url = feedMoviesUrl.format(10)

        if (savedInstanceState != null) {
            val stateUrl = savedInstanceState.getString(STATE_URL)
            if (stateUrl != null) {
                url = stateUrl
            }

            currentRecNum = savedInstanceState.getInt(STATE_REC_NUM)
        }

//        outState.putString(STATE_URL, currentUrl)
//        outState.putInt(STATE_REC_NUM, currentRecNum)

        downloadUrl(url)
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy: called")
        downloadData?.cancel(true)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        Log.d(TAG, "onSaveInstanceState: called")
        outState.putString(STATE_URL, currentUrl)
        outState.putInt(STATE_REC_NUM, currentRecNum)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.feeds_menu, menu)
        if (currentRecNum == 10) {
            menu?.findItem(R.id.mnu10)?.isChecked = true
        } else if (currentRecNum == 25) {
            menu?.findItem(R.id.mnu25)?.isChecked = true
        }
        return true
    }

    private fun downloadUrl(feedUrl: String) {
        if (feedUrl != currentUrl) {
            Log.d(TAG, "downloadUrl: called")
            downloadData = DownloadData(this, xmlListView)
            downloadData?.execute(feedUrl)
            currentUrl = feedUrl
            Log.d(TAG, "downloadUrl: done")
        } else {
            Log.d(TAG, "downloadUrl: URL is not changed")
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        var feedUrl: String = ""
        when (item.itemId) {
            R.id.mnuMovies -> feedUrl = feedMoviesUrl
            R.id.mnuTopSongs -> feedUrl = feedTopSongsUrlTemplate.format(25)
            R.id.mnuTopTVSeasons -> feedUrl = feedTopTVSeasonsUrl
            R.id.mnu10 -> {
                if (!item.isChecked) {
                    item.isChecked = true
                    feedUrl = feedTopSongsUrlTemplate.format(10)
                    currentRecNum = 10
                } else {
                    currentUrl = feedUrl
                }
            }
            R.id.mnu25 -> {
                if (!item.isChecked) {
                    item.isChecked = true
                    feedUrl = feedTopSongsUrlTemplate.format(25)
                    currentRecNum = 25
                } else {
                    currentUrl = feedUrl
                }
            }
            R.id.mnuRefresh -> {
                feedUrl = currentUrl
                currentUrl = ""
            }
            else -> return super.onOptionsItemSelected(item)
        }

        downloadUrl(feedUrl)
        return true
    }

    companion object {
        private class DownloadData(context: Context, listView: ListView) : AsyncTask<String, Void, String>() {
            private val TAG = "DownloadData"
            var propContext: Context by Delegates.notNull()
            var propListView: ListView by Delegates.notNull()

            init {
                propContext = context
                propListView = listView
            }


            override fun doInBackground(vararg url: String?): String {
                Log.d(TAG, "doInBackground: starts with ${url[0]}")
                val rssFeed = downloadRSS(url[0])
                if (rssFeed.isEmpty()) {
                    Log.e(TAG, "doInBackground: error downloading")
                }
                return rssFeed
            }

            override fun onPostExecute(result: String) {
                super.onPostExecute(result)
                Log.d(TAG, "onPostExecute: parameter is $result")
                val movieParser = MoviesParser()
                movieParser.parse(result)

//                val arrayAdapter = ArrayAdapter<FeedEntry>(propContext, R.layout.list_item, movieParser.movies)
//                propListView.adapter = arrayAdapter

                val feedAdapter = FeedAdapter(propContext, R.layout.list_record, movieParser.movies)
                propListView.adapter = feedAdapter
            }

            private fun downloadRSS(urlPath: String?): String {
                return URL(urlPath).readText()
            }

//            private fun downloadRSS(urlPath: String?): String {
//                val xmlResult = StringBuilder()
//
//                try {
//                    val url = URL(urlPath)
//                    val connection: HttpURLConnection = url.openConnection() as HttpURLConnection
//                    val response = connection.responseCode
//                    Log.d(TAG, "downloadRSS: response code was $response")
//
//////            val inputStream = connection.inputStream
//////            val inputStreamReader = InputStreamReader(inputStream)
//////            val reader = BufferedReader(inputStreamReader)
////
////                    val reader = BufferedReader(InputStreamReader(connection.inputStream))
////                    val inputBuffer = CharArray(500)
////                    var charsRead = 0
////
////                    while (charsRead >= 0) {
////                        charsRead = reader.read(inputBuffer)
////                        if (charsRead > 0) {
////                            xmlResult.append(inputBuffer, 0, charsRead)
////                        }
////                    }
////                    reader.close()
//
//                    //val stream = connection.inputStream
//                    connection.inputStream.buffered().reader().use { xmlResult.append(it.readText()) }
//
//                    Log.d(TAG, "downloadRSS: Downloaded ${xmlResult.length} bytes")
//
//                    return xmlResult.toString()
//
////                } catch (e: MalformedURLException) {
////                    Log.e(TAG, "downloadRSS: invalid url ${e.message}")
////                } catch (e: IOException) {
////                    Log.e(TAG, "downloadRSS: IO Exception ${e.message}")
////                }  catch (e: SecurityException) {
////                    Log.e(TAG, "downloadRSS: Security Exception. Need permissions. ${e.message}")
////                } catch (e: Exception) {
////                    Log.e(TAG, "downloadRSS: Unknown error ${e.message}")
////                }
//
//                } catch (e: Exception) {
//                    val errorMessage: String = when(e) {
//                        is MalformedURLException -> "downloadRSS: invalid url ${e.message}"
//                        is IOException -> "downloadRSS: IO Exception ${e.message}"
//                        is SecurityException -> {
//                            e.printStackTrace()
//                            "downloadRSS: Security Exception. Need permissions. ${e.message}"
//                        }
//                        else -> "downloadRSS: Unknown error ${e.message}"
//
//                    }
//                }
//
//                return ""
//            }
        }
    }
}

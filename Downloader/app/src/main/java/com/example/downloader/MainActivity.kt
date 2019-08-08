package com.example.downloader

import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import java.io.IOException
import java.lang.Exception
import java.lang.StringBuilder
import java.net.HttpURLConnection
import java.net.MalformedURLException
import java.net.URL

class MainActivity : AppCompatActivity() {
    private val TAG = "MainActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Log.d(TAG, "onCreate: called")

        val downloadData = DownloadData()
        downloadData.execute("http://ax.itunes.apple.com/WebObjects/MZStoreServices.woa/ws/RSS/topMovies/xml")
        Log.d(TAG, "onCreate: done")
    }

    companion object {
        private class DownloadData : AsyncTask<String, Void, String>() {
            private val TAG = "DownloadData"

            override fun doInBackground(vararg url: String?): String {
                Log.d(TAG, "doInBackground: starts with ${url[0]}")
                val rssFeed = downloadRSS(url[0])
                if (rssFeed.isEmpty()) {
                    Log.e(TAG, "doInBackground: error downloading")
                }
                return rssFeed
            }

            override fun onPostExecute(result: String?) {
                super.onPostExecute(result)
                Log.d(TAG, "onPostExecute: parameter is $result")
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
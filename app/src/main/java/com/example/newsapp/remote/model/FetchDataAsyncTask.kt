package com.example.newsapp.remote.model

import android.os.AsyncTask
import org.json.JSONArray
import org.json.JSONObject
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

interface OnDataFetchedListener {
    fun onDataFetched(responseModel: ResponseModel?)
}

class FetchDataAsyncTask(private val listener: OnDataFetchedListener) :
    AsyncTask<String, Void, ResponseModel?>() {

    override fun doInBackground(vararg params: String?): ResponseModel? {
        var connection: HttpURLConnection? = null
        var reader: BufferedReader? = null
        var responseModel: ResponseModel? = null

        try {
            // Specify the URL
            val url = URL(params[0])

            // Open the connection
            connection = url.openConnection() as HttpURLConnection
            connection.requestMethod = "GET"

            // Get the input stream
            val inputStream: InputStream = connection.inputStream
            reader = BufferedReader(InputStreamReader(inputStream))
            val response = StringBuilder()
            var line: String?

            // Read the response
            while (reader.readLine().also { line = it } != null) {
                response.append(line)
            }

            // Convert the response to JSON object
            val jsonResponse = JSONObject(response.toString())

            // Parse the JSON response into ResponseModel
            responseModel = ResponseModel(
                articles = parseArticles(jsonResponse.getJSONArray("articles")),
                status = jsonResponse.getString("status")
            )

        } catch (e: IOException) {
            e.printStackTrace()
        } finally {
            // Close resources
            connection?.disconnect()
            reader?.close()
        }

        return responseModel
    }

    private fun parseArticles(articlesArray: JSONArray): List<ResponseModel.Article?>? {
        val articlesList = mutableListOf<ResponseModel.Article?>()
        for (i in 0 until articlesArray.length()) {
            val articleObject = articlesArray.getJSONObject(i)
            val sourceObject = articleObject.getJSONObject("source")
//            val source = ResponseModel.Article.Source(
//                id = sourceObject.optString("id"),
//                name = sourceObject.optString("name")
//            )
            val article = ResponseModel.Article(
                author = articleObject.optString("author"),
                content = articleObject.optString("content"),
                description = articleObject.optString("description"),
                publishedAt = articleObject.optString("publishedAt"),
                title = articleObject.optString("title"),
                url = articleObject.optString("url"),
                urlToImage = articleObject.optString("urlToImage")
            )
            articlesList.add(article)
        }
        return articlesList
    }

    override fun onPostExecute(result: ResponseModel?) {
        // Notify the listener with the fetched data
        listener.onDataFetched(result)
    }
}

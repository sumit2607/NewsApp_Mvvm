package com.example.newsapp.ui

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.newsapp.R
import com.example.newsapp.databinding.ActivityMainBinding
import com.example.newsapp.remote.model.FetchDataAsyncTask
import com.example.newsapp.remote.model.OnDataFetchedListener
import com.example.newsapp.remote.model.ResponseModel
import com.example.newsapp.ui.adapter.NewsAdapter
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

class MainActivity : AppCompatActivity() , OnDataFetchedListener {
    private lateinit var binding: ActivityMainBinding
   var data = emptyList<ResponseModel.Article>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Replace the URL with your actual API endpoint
        val url = "https://candidate-test-data-moengage.s3.amazonaws.com/Android/news-api-feed/staticResponse.json"

        // Create and execute the FetchDataAsyncTask
        val fetchDataAsyncTask = FetchDataAsyncTask(this)
        fetchDataAsyncTask.execute(url)



    }

    private fun setUpRecyclerview() {
        val adapter = NewsAdapter(this, data , supportFragmentManager)
        binding.newsRecyclerview.adapter = adapter
        binding.newsRecyclerview.layoutManager = LinearLayoutManager(this)
    }

    override fun onDataFetched(responseModel: ResponseModel?) {
        // Handle the fetched data here
        if (responseModel != null) {
            // Access the articles list
            val articles = responseModel.articles


            // Loop through the articles and do something with them
            for (article in articles ?: emptyList()) {
                val title = article?.title
                val content = article?.content
                data = responseModel.articles as ArrayList<ResponseModel.Article>
                // Do something with title and content, e.g., display them in a TextView
                setUpRecyclerview()
            }

            // Access the status
            val status = responseModel.status
            // Do something with the status, if needed

            // Example: Show a toast message with the number of articles fetched
            Toast.makeText(this, "Fetched ${articles?.size ?: 0} articles", Toast.LENGTH_SHORT).show()
        } else {
            // Handle the case where data fetching failed
            Toast.makeText(this, "Failed to fetch data", Toast.LENGTH_SHORT).show()
        }
    }
    }

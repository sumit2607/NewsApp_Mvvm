package com.example.newsapp.remote.model

import androidx.core.view.ContentInfoCompat

data class ResponseModel(
    val articles: List<Article?>?,
    val status: String?
) {
    data class Article(
        val author: String?,
        val content: String?,
        val description: String?,
        val publishedAt: String?,
        val title: String?,
        val url: String?,
        val urlToImage: String?
    ) {

    }
}
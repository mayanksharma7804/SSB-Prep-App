package com.ssbprep.data

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApiService {
    @GET("v2/everything")
    fun getEverything(
        @Query("q") query: String,
        @Query("from") from: String,
        @Query("sortBy") sortBy: String,
        @Query("language") language: String,
        @Query("apiKey") apiKey: String,
        @Query("domains") domains: String? = null
    ): Call<NewsResponse>
}

package com.levqo.githubkotlin.data.network

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object RetrofitService {
    private const val BASE_URL = "https://api.github.com"

    fun getClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .readTimeout(30, TimeUnit.SECONDS)
            .connectTimeout(30, TimeUnit.SECONDS)
            .build()
    }

    fun getRetrofitBuilder(): Retrofit.Builder {
        return Retrofit.Builder().baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
    }

    fun getGitHubApi(retrofit: Retrofit.Builder, client: OkHttpClient): ApiService {
        return retrofit.client(client).build().create(ApiService::class.java)
    }
}
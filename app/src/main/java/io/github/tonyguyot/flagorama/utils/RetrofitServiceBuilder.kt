package io.github.tonyguyot.flagorama.utils

import com.google.gson.Gson
import io.github.tonyguyot.flagorama.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.logging.HttpLoggingInterceptor.Level.BODY
import okhttp3.logging.HttpLoggingInterceptor.Level.NONE
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Create a generic Retrofit service.
 */
fun <T> provideService(theClass: Class<T>, endpoint: String): T =
    createRetrofit(endpoint).create(theClass)

private fun createRetrofit(endpoint: String): Retrofit =
    Retrofit.Builder()
        .baseUrl(endpoint)
        .client(provideHttpClient())
        .addConverterFactory(provideConverterFactory())
        .build()

private fun provideHttpClient(): OkHttpClient =
    OkHttpClient.Builder()
        .addInterceptor(provideInterceptor())
        .build()

private fun provideInterceptor(): HttpLoggingInterceptor =
    HttpLoggingInterceptor().apply {
        level = if (BuildConfig.DEBUG) BODY else NONE
    }

private fun provideConverterFactory(): GsonConverterFactory =
    GsonConverterFactory.create(Gson())

/*
 * Copyright (C) 2020 Tony Guyot
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.github.tonyguyot.flagorama.data.utils

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

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
package io.github.tonyguyot.flagorama.utils

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.map
import io.github.tonyguyot.flagorama.model.Country
import kotlinx.coroutines.Dispatchers

/**
 * The database serves as the single source of truth.
 * Therefore UI can receive data updates from database only.
 * Function notify UI about:
 * [Result.Status.SUCCESS] - with data from database
 * [Result.Status.ERROR] - if error has occurred from any source
 * [Result.Status.LOADING]
 */
object DatabaseFirstStrategy {
    fun <T> getResultAsLiveData(databaseQuery: () -> T,
                                   shouldFetch: (T?) -> Boolean,
                                   networkCall: suspend () -> Resource<T>,
                                   saveCallResult: suspend (T) -> Unit): LiveData<Resource<T>> =
        liveData(Dispatchers.IO) {
            emit(Resource.loading<T>())
            val cachedSource = databaseQuery.invoke()
            if (shouldFetch(cachedSource)) {
                emit(Resource.loading<T>(cachedSource))
                val response = networkCall.invoke()
                if (response.status == Resource.Status.SUCCESS) {
                    saveCallResult(response.data!!)
                    emit(Resource.success(databaseQuery.invoke()))
                } else if (response.status == Resource.Status.ERROR) {
                    emit(Resource.error<T>(response.error!!, cachedSource))
                }
            } else {
                emit(Resource.success(cachedSource))
            }
        }
}
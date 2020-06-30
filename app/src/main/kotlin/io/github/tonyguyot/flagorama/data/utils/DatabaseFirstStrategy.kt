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

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import kotlinx.coroutines.Dispatchers
import timber.log.Timber

/**
 * The database serves as the single source of truth.
 * Therefore UI can receive data updates from database only.
 * Function notify UI about:
 * [Resource.Status.SUCCESS] - with data from database
 * [Resource.Status.ERROR] - if error has occurred from any source
 * [Resource.Status.LOADING]
 */
object DatabaseFirstStrategy {

    fun <T> getResultAsLiveData(databaseQuery: suspend () -> T,
                                shouldFetch: (T) -> Boolean,
                                networkCall: suspend () -> Resource<T>,
                                saveCallResult: suspend (T) -> Unit): LiveData<Resource<T>> =
        liveData(Dispatchers.IO) {
            // report start state LOADING + no data
            Timber.d("Loading...")
            emit(Resource.loading<T>())

            // retrieve the cached data
            val cachedSource = databaseQuery.invoke()

            // check if the cached data is usable
            if (shouldFetch(cachedSource)) {
                // the cached data is not usable, report still LOADING + cached data
                // the UI may decide to use the temporary data or not
                Timber.d("Need fetch from network...")
                emit(
                    Resource.loading<T>(
                        cachedSource
                    )
                )

                // connect to the server to retrieve data
                val response = networkCall.invoke()
                if (response.status == Resource.Status.SUCCESS && response.data != null) {
                    // we had a valid response from the server, save it to the database
                    // then report SUCCESS + data
                    Timber.d("Success from network")
                    saveCallResult(response.data)
                    emit(response)
                } else {
                    // in every other situation, report ERROR
                    if (response.status == Resource.Status.ERROR && response.error != null) {
                        Timber.d("Error from network")
                        emit(
                            Resource.error<T>(
                                response.error,
                                cachedSource
                            )
                        )
                    } else {
                        Timber.d("Unexpected status from network or empty data")
                        emit(
                            Resource.error(
                                Exception("Unexpected error"),
                                cachedSource
                            )
                        )
                    }
               }
            } else {
                // the cached data is usable, report state SUCCESS + cached data
                Timber.d("Success: will use data from cache")
                emit(
                    Resource.success(
                        cachedSource
                    )
                )
            }
        }
}
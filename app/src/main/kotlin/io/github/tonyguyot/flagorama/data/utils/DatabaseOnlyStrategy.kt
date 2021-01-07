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
import androidx.lifecycle.map
import kotlinx.coroutines.Dispatchers
import timber.log.Timber

/**
 * The database serves as the only source.
 * Therefore UI can receive data updates from database only.
 * Function notify UI about:
 * [Resource.Status.SUCCESS] - with data from database
 * [Resource.Status.LOADING]
 */
object DatabaseOnlyStrategy {

    fun <T> getResultAsLiveData(databaseQuery: suspend () -> LiveData<T>): LiveData<Resource<T>> =
        liveData(Dispatchers.IO) {
            // report start state LOADING + no data
            Timber.d("Loading...")
            emit(Resource.loading<T>())

            // retrieve the cached data and report it as SUCCESS
            val source = databaseQuery.invoke().map { Resource.success(it) }
            Timber.d("Success: always use data from cache")
            emitSource(source)
        }
}
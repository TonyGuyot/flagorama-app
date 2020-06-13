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
                    emit(Resource.success(response.data))
                } else if (response.status == Resource.Status.ERROR) {
                    emit(Resource.error<T>(response.error!!, cachedSource))
                }
            } else {
                emit(Resource.success(cachedSource))
            }
        }
}
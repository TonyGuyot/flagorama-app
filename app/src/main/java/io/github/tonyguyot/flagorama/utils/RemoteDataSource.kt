package io.github.tonyguyot.flagorama.utils

import retrofit2.Response
import java.lang.Exception

/**
 * Base data source class for all network access.
 */
abstract class RemoteDataSource {
    protected suspend fun <T, R> fetchResource(call: suspend () -> Response<R>,
                                               transform: suspend (R) -> T): Resource<T> {
        try {
            val response = call()
            if (response.isSuccessful) {
                val body = response.body()
                if (body != null)
                    return Resource.success(transform(body))
                return Resource.error(Exception("empty body"))
            }
            return Resource.error(Exception("HTTP error ${response.code()}"))
        } catch (e: Exception) {
            return Resource.error(e)
        }
    }
}
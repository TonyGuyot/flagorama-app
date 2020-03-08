package io.github.tonyguyot.flagorama.utils

import io.github.tonyguyot.flagorama.utils.Resource.Success
import io.github.tonyguyot.flagorama.utils.Resource.Error
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
                    return Success(transform(body))
                return Error("empty body")
            }
            return Error("HTTP error ${response.code()}")
        } catch (e: Exception) {
            return Error(e.message ?: e.toString())
        }
    }
}
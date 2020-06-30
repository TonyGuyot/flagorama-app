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

import retrofit2.Response
import java.lang.Exception

/**
 * Base data source class for all network access.
 */
abstract class BaseRemoteDataSource {
    protected suspend fun <T, R> fetchResource(call: suspend () -> Response<R>,
                                               transform: suspend (R) -> T): Resource<T> {
        try {
            val response = call()
            if (response.isSuccessful) {
                val body = response.body()
                if (body != null)
                    return Resource.success(
                        transform(body)
                    )
                return Resource.error(
                    Exception("empty body")
                )
            }
            return Resource.error(
                Exception("HTTP error ${response.code()}")
            )
        } catch (e: Exception) {
            return Resource.error(e)
        }
    }
}
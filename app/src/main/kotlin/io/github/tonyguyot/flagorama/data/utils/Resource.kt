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

/** A generic class that contains data and status about loading this data. */
data class Resource<out T>(
    val status: Status,
    val data: T? = null,
    val error: Throwable? = null
) {
    companion object {
        fun <T> success(data: T) =
            Resource<T>(
                Status.SUCCESS,
                data
            )
        fun <T> loading(data: T? = null) =
            Resource<T>(
                Status.LOADING,
                data
            )
        fun <T> error(error: Throwable, data: T? = null) =
            Resource<T>(
                Status.ERROR,
                data,
                error
            )
    }

    enum class Status { SUCCESS, ERROR, LOADING }
}

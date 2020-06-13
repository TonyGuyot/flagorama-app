package io.github.tonyguyot.flagorama.utils

/** A generic class that contains data and status about loading this data. */
data class Resource<out T>(
    val status: Status,
    val data: T? = null,
    val error: Throwable? = null
) {
    companion object {
        fun <T> success(data: T) = Resource<T>(Status.SUCCESS, data)
        fun <T> loading(data: T? = null) = Resource<T>(Status.LOADING, data)
        fun <T> error(error: Throwable, data: T? = null) = Resource<T>(Status.ERROR, data, error)
    }

    enum class Status { SUCCESS, ERROR, LOADING }
}

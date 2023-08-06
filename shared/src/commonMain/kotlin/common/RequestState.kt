package common

import common.Status.*

enum class Status {
    SUCCESS,
    ERROR,
    IDLE,
    LOADING
}

data class RequestState<out T>(
    val status: Status,
    val data: T?,
    val throwable: Throwable?
) {
    companion object {
        fun <T> success(data: T?): RequestState<T> =
            RequestState(
                status = SUCCESS,
                data = data,
                throwable = null
            )

        fun <T> error(throwable: Throwable): RequestState<T> =
            RequestState(
                status = ERROR,
                data = null,
                throwable = throwable
            )

        fun <T> loading(): RequestState<T> =
            RequestState(
                status = LOADING,
                data = null,
                throwable = null
            )

        fun <T> idle(): RequestState<T> =
            RequestState(
                status = IDLE,
                data = null,
                throwable = null
            )
    }
}
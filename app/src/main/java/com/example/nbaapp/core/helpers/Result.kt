package com.example.nbaapp.core.helpers

typealias RootError = Error

sealed interface Result<out D, out E : RootError> {
    data class Success<out D, out E : RootError>(val data: D) : Result<D, E>
    data class Error<out D, out E : RootError>(val error: E, val cachedData: D? = null) :
        Result<D, E>
}

inline fun <T, E : Error, R> Result<T, E>.flatMap(transform: (T) -> Result<R, E>): Result<R, E> =
    when (this) {
        is Result.Success -> transform(data)
        is Result.Error -> Result.Error(error, cachedData = null)
    }
inline fun <T, E : Error, R> Result<T, E>.map(map: (T) -> R): Result<R, E> = when (this) {
    is Result.Success -> Result.Success(map(data))
    is Result.Error -> Result.Error(error)
}

inline fun <T, E : Error, F : Error> Result<T, E>.mapError(map: (E) -> F): Result<T, F> = when (this) {
    is Result.Success -> Result.Success(data)
    is Result.Error -> Result.Error(map(error), cachedData)
}

inline fun <T, E : Error> Result<T, E>.recover(onError: (E) -> T): Result<T, E> = when (this) {
    is Result.Success -> this
    is Result.Error -> Result.Success(onError(error))
}

inline fun <T, E : Error> Result<T, E>.onSuccess(action: (T) -> Unit): Result<T, E> {
    return when (this) {
        is Result.Error -> this
        is Result.Success -> {
            action(data)
            this
        }
    }
}

inline fun <T, E : Error> Result<T, E>.onFailure(action: (E) -> Unit): Result<T, E> {
    return when (this) {
        is Result.Error -> {
            action(error)
            this
        }
        is Result.Success -> this
    }
}

inline fun <T, E : Error> Result<T, E>.onFailureWithCache(action: (error: E, cached: T?) -> Unit): Result<T, E> {
    return when (this) {
        is Result.Error -> {
            action(error, cachedData)
            this
        }
        is Result.Success -> this
    }
}

fun <T, E : Error> Result<T, E>.getOrNull(): T? =
    when (this) {
        is Result.Success -> data
        is Result.Error -> null
    }

inline fun <T, E : Error> Result<T, E>.getOrElse(onError: (E) -> T): T =
    when (this) {
        is Result.Success -> data
        is Result.Error -> onError(error)
    }
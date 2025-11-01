package com.example.nbaapp.data.remote.utils

import com.example.nbaapp.core.helpers.DataError
import com.example.nbaapp.core.helpers.DebugUtils
import com.example.nbaapp.core.helpers.Result
import kotlinx.coroutines.ensureActive
import retrofit2.Response
import java.net.SocketTimeoutException
import java.nio.channels.UnresolvedAddressException
import kotlin.coroutines.coroutineContext

suspend inline fun <reified T> safeCall(
    execute: () -> Response<T>
): Result<T, DataError.Remote> {
    val response = try {
        execute()
    } catch (e: UnresolvedAddressException) {
        return Result.Error(DataError.Remote.NO_INTERNET)
    } catch (e: SocketTimeoutException) {
        return Result.Error(DataError.Remote.REQUEST_TIMEOUT)
    } catch (e: Exception) {
        coroutineContext.ensureActive()
        return Result.Error(DataError.Remote.UNKNOWN)
    }

    return responseToResult(response)
}

fun <T> responseToResult(
    response: Response<T>
): Result<T, DataError.Remote> {
    return if (response.isSuccessful) {
        response.body()?.let {
            Result.Success(it)
        } ?: Result.Error(DataError.Remote.NO_DATA)
    } else {
        val error = getRemoteError(response.code())
        Result.Error(error)
    }
}

private fun getRemoteError(code: Int) = when (code) {
    400 -> DataError.Remote.BAD_REQUEST
    401 -> DataError.Remote.UNAUTHORIZED
    404 -> DataError.Remote.NOT_FOUND
    406 -> DataError.Remote.NOT_ACCEPTABLE
    429 -> DataError.Remote.TOO_MANY_REQUESTS
    500 -> DataError.Remote.INTERNAL_SERVER_ERROR
    503 -> DataError.Remote.SERVICE_UNAVAILABLE
    else -> DataError.Remote.UNKNOWN
}

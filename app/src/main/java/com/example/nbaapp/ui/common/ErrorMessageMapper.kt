package com.example.nbaapp.ui.common

import android.content.Context
import androidx.annotation.StringRes
import com.example.nbaapp.R
import com.example.nbaapp.core.helpers.DataError
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class ErrorMessageMapper @Inject constructor(
    @param:ApplicationContext private val context: Context
) {
    fun toUiMessage(error: DataError): String {
        val resId = when (error) {
            is DataError.Remote -> mapRemoteError(error)
            is DataError.Local -> mapLocalError(error)
        }
        return context.getString(resId)
    }

    private fun mapRemoteError(error: DataError.Remote)= when (error) {
        DataError.Remote.UNAUTHORIZED -> R.string.error_unauthorized
        DataError.Remote.FORBIDDEN -> R.string.error_forbidden
        DataError.Remote.NO_INTERNET -> R.string.error_no_internet
        DataError.Remote.REQUEST_TIMEOUT -> R.string.error_request_timeout
        DataError.Remote.BAD_REQUEST -> R.string.error_bad_request
        DataError.Remote.NOT_FOUND -> R.string.error_not_found
        DataError.Remote.NOT_ACCEPTABLE -> R.string.error_not_acceptable
        DataError.Remote.CONFLICT -> R.string.error_conflict
        DataError.Remote.PAYLOAD_TOO_LARGE -> R.string.error_payload_too_large
        DataError.Remote.UNSUPPORTED_MEDIA_TYPE -> R.string.error_unsupported_media_type
        DataError.Remote.TOO_MANY_REQUESTS -> R.string.error_too_many_requests
        DataError.Remote.INTERNAL_SERVER_ERROR -> R.string.error_internal_server
        DataError.Remote.BAD_GATEWAY -> R.string.error_bad_gateway
        DataError.Remote.SERVICE_UNAVAILABLE -> R.string.error_service_unavailable
        DataError.Remote.GATEWAY_TIMEOUT -> R.string.error_gateway_timeout
        DataError.Remote.SSL_HANDSHAKE -> R.string.error_ssl_handshake
        DataError.Remote.NO_DATA -> R.string.error_no_data
        DataError.Remote.UNKNOWN -> R.string.error_unknown
    }

    private fun mapLocalError(error: DataError.Local)= when (error) {
        DataError.Local.DISK_FULL -> R.string.error_disk_full
        DataError.Local.DB_CORRUPTED -> R.string.error_db_corrupted
        DataError.Local.CACHE_EXPIRED -> R.string.error_cache_expired
        DataError.Local.UNKNOWN -> R.string.error_unknown
    }
}
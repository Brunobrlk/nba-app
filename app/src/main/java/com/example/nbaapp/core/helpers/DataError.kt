package com.example.nbaapp.core.helpers

sealed interface DataError : Error {
    enum class Remote : DataError {
        UNAUTHORIZED,
        FORBIDDEN,
        NO_INTERNET,
        REQUEST_TIMEOUT,
        BAD_REQUEST,
        NOT_FOUND,
        NOT_ACCEPTABLE,
        CONFLICT,
        PAYLOAD_TOO_LARGE,
        UNSUPPORTED_MEDIA_TYPE,
        TOO_MANY_REQUESTS,
        INTERNAL_SERVER_ERROR,
        BAD_GATEWAY,
        SERVICE_UNAVAILABLE,
        GATEWAY_TIMEOUT,
        SSL_HANDSHAKE,
        NO_DATA,
        UNKNOWN
    }

    enum class Local : DataError {
        DISK_FULL,
        DB_CORRUPTED,
        CACHE_EXPIRED,
        UNKNOWN
    }
}

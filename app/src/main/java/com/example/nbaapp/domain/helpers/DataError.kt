package com.example.nbaapp.domain.helpers

sealed interface DataError: Error {
    enum class Remote: DataError {
        UNAUTHORIZED,
        BAD_REQUEST,
        NOT_FOUND,
        NOT_ACCEPTABLE,
        TOO_MANY_REQUESTS,
        INTERNAL_SERVER_ERROR,
        SERVICE_UNAVAILABLE,
        UNKNOWN
    }
    enum class Local: DataError {
        DISK_FULL,
        UNKNOWN
    }
}
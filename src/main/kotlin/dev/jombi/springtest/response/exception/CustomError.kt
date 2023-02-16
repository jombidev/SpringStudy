package dev.jombi.springtest.response.exception

import dev.jombi.springtest.response.exception.impl.ErrorCode

class CustomError(val errorCode: ErrorCode) : RuntimeException() {
    companion object {
        @JvmStatic
        fun of(err: ErrorCode) = CustomError(err)
    }
}
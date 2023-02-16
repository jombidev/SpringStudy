package dev.jombi.springtest.response.exception.impl

import org.springframework.http.HttpStatus

enum class ErrorCode(val status: HttpStatus, val message: String) {
    NOT_FOUND(HttpStatus.NOT_FOUND, "not found."),
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "server is going wrong."),
    INVALID_USER(HttpStatus.UNAUTHORIZED, "User not found."),
    EXISTS_USER(HttpStatus.BAD_REQUEST, "user already exists."),
    TOKEN_EXPIRED(HttpStatus.BAD_REQUEST, "token is expired."),
    TOKEN_NOT_PROVIDED(HttpStatus.BAD_REQUEST, "token is not provided."),
    MALFORMED_TOKEN(HttpStatus.BAD_REQUEST, "token is malformed."),
    INCORRECT_PASSWORD(HttpStatus.BAD_REQUEST, "incorrect password.")
}
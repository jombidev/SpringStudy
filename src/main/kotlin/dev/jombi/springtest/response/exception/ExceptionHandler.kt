package dev.jombi.springtest.response.exception

import dev.jombi.springtest.response.exception.impl.ErrorCode
import org.springframework.http.ResponseEntity
import org.springframework.web.HttpRequestMethodNotSupportedException
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class ExceptionHandler {
    @ExceptionHandler(CustomError::class)
    protected fun handleCustomException(e: CustomError) = ErrorResponseEntity.responseEntity(e.errorCode)

    @ExceptionHandler(Exception::class)
    protected fun handleException(e: Exception): ResponseEntity<ErrorResponseEntity> {
        // TODO: Logger
        e.printStackTrace()
        return ResponseEntity.status(500).body(
            ErrorResponseEntity(
                ErrorCode.INTERNAL_SERVER_ERROR.status.value(),
                ErrorCode.INTERNAL_SERVER_ERROR.name,
                "${e.message} ${e.cause}"
            )
        )
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException::class)
    protected fun handleException(e: HttpRequestMethodNotSupportedException) =
        ResponseEntity.status(405).body(
            ErrorResponseEntity(
                e.statusCode.value(),
                "METHOD_NOT_ALLOWED",
                "${e.method} is not allowed from this request. (${e.supportedMethods?.joinToString(", ")})"
            )
        )

    @ExceptionHandler(MethodArgumentNotValidException::class)
    protected fun handleException(e: MethodArgumentNotValidException) =
        ResponseEntity.status(400).body(
            ErrorResponseEntity(
                e.statusCode.value(),
                "INVALID_ARGUMENTS",
                e.bindingResult.fieldError?.defaultMessage ?: "default"
            )
        )
}
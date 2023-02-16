package dev.jombi.springtest.response.exception

import dev.jombi.springtest.response.exception.impl.ErrorCode
import org.springframework.http.ResponseEntity

class ErrorResponseEntity(val status: Int, val code: String, val message: String) {
    companion object {
        @JvmStatic
        fun responseEntity(e: ErrorCode): ResponseEntity<ErrorResponseEntity> {
            return ResponseEntity.status(e.status).body(ErrorResponseEntity(e.status.value(), e.name, e.message))
        }
    }
}
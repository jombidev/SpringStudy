package dev.jombi.springtest.response

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity

class Response(val code: Int, val message: String) {
    companion object {
        @JvmStatic
        fun of(status: HttpStatus, message: String) = Response(status.value(), message)
        @JvmStatic
        fun ok(message: String) = ResponseEntity(of(HttpStatus.OK, message), HttpStatus.OK)
    }
}
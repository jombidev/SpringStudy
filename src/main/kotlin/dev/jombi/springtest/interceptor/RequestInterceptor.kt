package dev.jombi.springtest.interceptor

import dev.jombi.springtest.jwt.JWTUtil
import dev.jombi.springtest.jwt.TokenType
import dev.jombi.springtest.response.exception.CustomError
import dev.jombi.springtest.response.exception.impl.ErrorCode
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.stereotype.Component
import org.springframework.web.method.HandlerMethod
import org.springframework.web.servlet.HandlerInterceptor

@Component
class RequestInterceptor(val jwt: JWTUtil) : HandlerInterceptor {
    override fun preHandle(request: HttpServletRequest, response: HttpServletResponse, handler: Any): Boolean {
        if (handler !is HandlerMethod) return true

        if (!handler.method.isAnnotationPresent(CheckToken::class.java))
            return true

        try {
            val token = getTokenOfRequest(request).split(' ')[1]

            if (jwt.checkTokenType(token) != TokenType.ACCESS_TOKEN)
                throw CustomError.of(ErrorCode.MALFORMED_TOKEN)

            val user = jwt.getUserByToken(token)

            request.setAttribute("user", user)
        } catch (e: IndexOutOfBoundsException) {
            throw CustomError.of(ErrorCode.TOKEN_NOT_PROVIDED)
        }

        return true
    }

    private fun getTokenOfRequest(request: HttpServletRequest): String {
        return request.getHeaders("Authorization").toList().firstOrNull { it != null } ?: ""
    }
}
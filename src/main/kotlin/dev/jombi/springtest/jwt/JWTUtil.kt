package dev.jombi.springtest.jwt

import dev.jombi.springtest.response.exception.CustomError
import dev.jombi.springtest.response.exception.impl.ErrorCode
import dev.jombi.springtest.services.UserService
import io.jsonwebtoken.Claims
import io.jsonwebtoken.ExpiredJwtException
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.MalformedJwtException
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.UnsupportedJwtException
import io.jsonwebtoken.security.Keys
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import org.w3c.dom.events.EventException
import java.lang.IllegalArgumentException
import java.security.Key
import java.util.Date

@Component
class JWTUtil(@Value("\${product.jwtSecret}") val secretKey: String, val userService: UserService) {
    companion object {
        const val ACCESS_TOKEN_EXPIRES_IN = 1000L * 600L * 10L // 10min
        const val REFRESH_TOKEN_EXPIRES_IN = 1000L * 3600L // 1hr
    }
    private fun getSignKey(secretKey: String): Key {
        val keyBytes = secretKey.toByteArray(charset("utf-8"))
        return Keys.hmacShaKeyFor(keyBytes)
    }

    fun generateJwtToken(username: String, expireDate: Long, type: TokenType): String {
        val claims = Jwts.claims()
        claims["type"] = type
        claims["username"] = username

        val cur = Date()
        return Jwts.builder().setClaims(claims).setIssuedAt(cur).setExpiration(Date(cur.time + expireDate))
            .signWith(getSignKey(secretKey), SignatureAlgorithm.HS256).compact()
    }

    fun extractAllClaims(token: String): Claims {
        try {
            return Jwts.parserBuilder().setSigningKey(getSignKey(secretKey)).build()
                .parseClaimsJws(token).body
        } catch (e: ExpiredJwtException) {
            throw CustomError.of(ErrorCode.TOKEN_EXPIRED)
        } catch (e: IllegalArgumentException) {
            throw CustomError.of(ErrorCode.TOKEN_NOT_PROVIDED)
        } catch (e: UnsupportedJwtException) {
            throw CustomError.of(ErrorCode.MALFORMED_TOKEN)
        } catch (e: MalformedJwtException) {
            throw CustomError.of(ErrorCode.MALFORMED_TOKEN)
        } catch (e: EventException) {
            throw e
        }
    }

    fun checkTokenType(token: String): TokenType {
        return if ("REFRESH_TOKEN" == extractAllClaims(token)["type"]) TokenType.REFRESH_TOKEN
        else TokenType.ACCESS_TOKEN
    }

    fun getUserByToken(token: String) = userService.getUserByUsername(extractAllClaims(token)["username"]!!.toString())

    fun generateAccessToken(username: String): String {
        return generateJwtToken(username, ACCESS_TOKEN_EXPIRES_IN, TokenType.ACCESS_TOKEN)
    }

    fun generateRefreshToken(username: String): String {
        return generateJwtToken(username, REFRESH_TOKEN_EXPIRES_IN, TokenType.REFRESH_TOKEN)
    }
}

enum class TokenType {
    ACCESS_TOKEN, REFRESH_TOKEN
}
package dev.jombi.springtest.services

import dev.jombi.springtest.dto.request.LoginRequestDTO
import dev.jombi.springtest.dto.request.RegisterRequestDTO
import dev.jombi.springtest.repositories.UserRepository
import dev.jombi.springtest.entity.User
import dev.jombi.springtest.jwt.JWTUtil
import dev.jombi.springtest.dto.response.TokenResponseDTO
import dev.jombi.springtest.jwt.TokenType
import dev.jombi.springtest.response.exception.CustomError
import dev.jombi.springtest.response.exception.impl.ErrorCode
import jakarta.validation.Valid
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class AuthService(
    private val repository: UserRepository,
    private val jwt: JWTUtil
) { // TODO: change runtime error to CustomError
    fun login(@Valid data: LoginRequestDTO): User {
        val user = repository.findUserByUsername(data.username)
            ?: throw CustomError.of(ErrorCode.INVALID_USER)
        if (user.password != data.password)
            throw CustomError.of(ErrorCode.INCORRECT_PASSWORD)
        return user
    }

    @Transactional
    fun register(@Valid data: RegisterRequestDTO): User {
        if (repository.existsByUsername(data.username))
            throw CustomError.of(ErrorCode.EXISTS_USER)

        return repository.save(data.toEntity(data))
    }

    fun getUserByRefreshToken(refreshToken: String): User {
        if (jwt.checkTokenType(refreshToken) != TokenType.REFRESH_TOKEN)
            throw CustomError.of(ErrorCode.MALFORMED_TOKEN)
        return jwt.getUserByToken(refreshToken)

    }

    fun getToken(user: User) =
        TokenResponseDTO(jwt.generateAccessToken(user.username), jwt.generateRefreshToken(user.username))
}
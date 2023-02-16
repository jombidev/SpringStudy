package dev.jombi.springtest.controller

import dev.jombi.springtest.jwt.JWTUtil
import dev.jombi.springtest.dto.request.LoginRequestDTO
import dev.jombi.springtest.dto.request.RefreshTokenRequestDTO
import dev.jombi.springtest.dto.request.RegisterRequestDTO
import dev.jombi.springtest.dto.response.LoginResponseDTO
import dev.jombi.springtest.dto.response.RegisterResponseDTO
import dev.jombi.springtest.services.AuthService
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/authenticate")
class AuthenticateController(private val authService: AuthService) {
    @PostMapping("/login")
    fun login(@Valid @RequestBody login: LoginRequestDTO): ResponseEntity<LoginResponseDTO> {
        val user = authService.login(login)
        val token = authService.getToken(user)
        return ResponseEntity.ok(
            LoginResponseDTO(
                true,
                token.accessToken,
                token.refreshToken,
                JWTUtil.ACCESS_TOKEN_EXPIRES_IN
            )
        )
    }

    @PostMapping("/register")
    fun register(@Valid @RequestBody data: RegisterRequestDTO): ResponseEntity<RegisterResponseDTO> {
        val user = authService.register(data)
        return ResponseEntity.ok(RegisterResponseDTO(true, user.username))
    }

    @PostMapping("/refresh")
    fun refresh(@Valid @RequestBody data: RefreshTokenRequestDTO): ResponseEntity<LoginResponseDTO> {
        val user = authService.getUserByRefreshToken(data.refreshToken)
        val token = authService.getToken(user)
        return ResponseEntity.ok(
            LoginResponseDTO(
                true,
                token.accessToken,
                token.refreshToken,
                JWTUtil.ACCESS_TOKEN_EXPIRES_IN
            )
        )
    }
}
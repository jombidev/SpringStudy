package dev.jombi.springtest.dto.request

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Pattern

class RefreshTokenRequestDTO(
    @Pattern(regexp = "[A-Za-z0-9\\-_]+\\.[A-Za-z0-9\\-_]+\\.[A-Za-z0-9\\-_]+")
    @NotBlank(message = "refreshToken must not be blank")
    val refreshToken: String
)
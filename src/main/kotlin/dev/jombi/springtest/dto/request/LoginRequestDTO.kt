package dev.jombi.springtest.dto.request

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size

class LoginRequestDTO(
    @NotBlank(message = "id is blank")
    val username: String,

    @Size(min = 128, max = 128, message = "password is not hashed")
    @NotBlank(message = "password is blank")
    val password: String
)
package dev.jombi.springtest.dto.request

import dev.jombi.springtest.entity.User
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Pattern
import jakarta.validation.constraints.Size

class RegisterRequestDTO(
    @Pattern(regexp = "a-zA-Z0-9-_")
    @Size(min = 5, max = 16, message = "Username must be in 5 between 16.")
    @NotBlank(message = "username must not be blank.")
    val username: String,

    @Size(min = 128, max = 128, message = "password is not hashed.")
    @NotBlank(message = "message must not be blank.")
    val password: String
) {
    fun toEntity(register: RegisterRequestDTO): User {
        return User(username = register.username, password = register.password)
    }
}
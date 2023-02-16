package dev.jombi.springtest.dto.response

class LoginResponseDTO(
    val success: Boolean,
    val access_token: String? = null,
    val refresh_token: String? = null,
    val expires_after: Long? = null
)
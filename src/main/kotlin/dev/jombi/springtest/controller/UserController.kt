package dev.jombi.springtest.controller

import dev.jombi.springtest.dto.response.ProfileResponseDTO
import dev.jombi.springtest.entity.User
import dev.jombi.springtest.interceptor.CheckToken
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestAttribute
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/profile")
class UserController {
    @CheckToken
    @GetMapping
    fun profile(@RequestAttribute user: User): ResponseEntity<ProfileResponseDTO> {
        return ResponseEntity.ok(ProfileResponseDTO(user.username))
    }
}
package dev.jombi.springtest.services

import dev.jombi.springtest.repositories.UserRepository
import dev.jombi.springtest.entity.User
import dev.jombi.springtest.response.exception.CustomError
import dev.jombi.springtest.response.exception.impl.ErrorCode
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class UserService(private val userRepository: UserRepository) {
    fun getUserByUsername(username: String): User =
        userRepository.findUserByUsername(username) ?: throw CustomError.of(ErrorCode.NOT_FOUND)
}
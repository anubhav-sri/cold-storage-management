package com.saikrishna.wms.services

import com.saikrishna.wms.exceptions.InvalidPasswordException
import com.saikrishna.wms.exceptions.UserNotFoundException
import com.saikrishna.wms.repositories.UserRepository
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class LoginService(private val userRepository: UserRepository, private val passwordEncoder: PasswordEncoder) {
    fun login(userName: String, password: String): String {
        val user = userRepository.findByUsername(userName)
        user?.let {
            if (passwordEncoder.matches(password, it.password)) return it.username
            throw InvalidPasswordException(userName)
        }

        throw UserNotFoundException(userName)

    }

}

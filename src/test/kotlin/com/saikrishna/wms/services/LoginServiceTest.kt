package com.saikrishna.wms.services

import com.saikrishna.wms.exceptions.InvalidPasswordException
import com.saikrishna.wms.exceptions.UserNotFoundException
import com.saikrishna.wms.models.User
import com.saikrishna.wms.repositories.UserRepository
import io.mockk.every
import io.mockk.mockk
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder

internal class LoginServiceTest {
    private val userRepository: UserRepository = mockk()
    private val passwordEncoder: PasswordEncoder = BCryptPasswordEncoder()

    private val loginService = LoginService(userRepository, passwordEncoder)

    @Test
    fun shouldBeAbleToLoginWithCorrectUsernameAndPasswordUsingPasswordEncoder() {
        every { userRepository.findByUsername("username") } returns User(1, "username", passwordEncoder.encode("password"))
        val loggedInUser = loginService.login("username", "password")

        assertThat(loggedInUser).isEqualTo("username");
    }

    @Test
    fun shouldThrowUserNotFoundExceptionIfUserNotFound() {
        every { userRepository.findByUsername("username") } returns null

        Assertions.assertThrows(UserNotFoundException::class.java
        ) { loginService.login("username", "password") }
    }

    @Test
    fun shouldThrowInvalidPasswordExceptionIfPasswordIsWrong() {
        every { userRepository.findByUsername("username") } returns User(1,
                "username",
                "wrongpassword")

        Assertions.assertThrows(InvalidPasswordException::class.java
        ) { loginService.login("username", "password") }
    }

}
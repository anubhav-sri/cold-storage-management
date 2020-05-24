package com.saikrishna.wms.controllers

import com.saikrishna.wms.models.LoginRequest
import com.saikrishna.wms.services.LoginService
import io.mockk.every
import io.mockk.mockk
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.http.HttpStatus

internal class LoginControllerTest {

    private val loginService: LoginService = mockk()

    @Test
    fun shouldReturnTheJwtTokenIfTheLoginIsSuccessful() {
        every { loginService.login("username", "password") } returns "username"

        val entity = LoginController(loginService, "saish_secret")
                .login(LoginRequest("username", "password"))

        assertThat(entity.statusCode).isEqualTo(HttpStatus.OK)
        assertThat(entity.body).isNotEmpty()
    }

}
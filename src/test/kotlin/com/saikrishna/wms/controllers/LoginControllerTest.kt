package com.saikrishna.wms.controllers

import com.saikrishna.wms.models.LoginRequest
import com.saikrishna.wms.models.LoginResponse
import com.saikrishna.wms.models.Role
import com.saikrishna.wms.models.UserDto
import com.saikrishna.wms.services.LoginService
import io.mockk.every
import io.mockk.mockk
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.http.HttpStatus
import java.util.*

internal class LoginControllerTest {

    private val loginService: LoginService = mockk()

    @Test
    fun shouldReturnTheJwtTokenIfTheLoginIsSuccessful() {
        every { loginService.login("username", "password") } returns UserDto("username", Role.ADMIN)

        val entity = LoginController(loginService, "saish_secret")
                .login(LoginRequest("username", "password"))

        assertThat(entity.statusCode).isEqualTo(HttpStatus.OK)
		val response = entity.body to LoginResponse::class
		assertThat(response.first?.name).isEqualTo("username")
		assertThat(response.first?.role).isEqualTo(Role.ADMIN)
		assertThat(response.first?.expiresAt).isAfterOrEqualTo(Date(System.currentTimeMillis() + 5183940000))
		assertThat(response.first?.token).isNotEmpty()
    }
}

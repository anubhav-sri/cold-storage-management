package com.saikrishna.wms.controllers

import com.saikrishna.wms.services.LoginService
import io.mockk.every
import io.mockk.mockk
import io.mockk.slot
import io.mockk.verify
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.http.HttpStatus
import javax.servlet.http.Cookie
import javax.servlet.http.HttpServletResponse

internal class LoginControllerTest {

    private val loginService: LoginService = mockk()
    private val httpResponse: HttpServletResponse = mockk(relaxed = true)

    @Test
    fun shouldSetTheCookieIfTheLoginIsSuccessful() {
        every { loginService.login("username", "password") } returns "username"

        val entity = LoginController(loginService)
                .login("username", "password", httpResponse)

        assertThat(entity.statusCode).isEqualTo(HttpStatus.OK)
        val captor = slot<Cookie>()
        verify { httpResponse.addCookie(capture(captor)) }

        assertThat(captor.captured.name).isEqualTo("user")
        assertThat(captor.captured.value).isEqualTo("username")
    }
}
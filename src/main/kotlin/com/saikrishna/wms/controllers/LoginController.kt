package com.saikrishna.wms.controllers

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm.HMAC512
import com.saikrishna.wms.models.LoginRequest
import com.saikrishna.wms.services.LoginService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import java.util.*
import javax.servlet.http.Cookie
import javax.servlet.http.HttpServletResponse


@RestController
class LoginController(@Autowired private val loginService: LoginService) {

    @PostMapping("/authenticate", consumes = ["application/json"])
    fun login(@RequestBody request: LoginRequest, response: HttpServletResponse): ResponseEntity<String> {
        val user = loginService.login(request.userName, request.password)
        val token = JWT.create()
                .withSubject(user)
                .withExpiresAt(Date(System.currentTimeMillis()))
                .sign(HMAC512("saish_secret".toByteArray()))

        val cookie = createCookie(token, 24 * 60 * 7)
        response.addCookie(cookie)

        return ResponseEntity.ok(user)
    }

    @PostMapping("/logout")
    fun logOut(response: HttpServletResponse): ResponseEntity<Any> {
        response.addCookie(createCookie(null, 0))
        return ResponseEntity.ok("")
    }

    private fun createCookie(token: String?, maxAge: Int): Cookie {
        val cookie = Cookie("token", token)
        cookie.maxAge = maxAge
        cookie.isHttpOnly = true
        return cookie
    }


}
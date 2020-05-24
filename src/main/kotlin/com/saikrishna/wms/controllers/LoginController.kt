package com.saikrishna.wms.controllers

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm.HMAC512
import com.saikrishna.wms.models.LoginRequest
import com.saikrishna.wms.services.LoginService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import java.util.*


@RestController
@CrossOrigin
class LoginController(@Autowired private val loginService: LoginService, @Value("\${jwt.token}") val token: String) {

    private val sevenDays = 604800000

    @PostMapping("/authenticate", consumes = ["application/json"])
    fun login(@RequestBody request: LoginRequest): ResponseEntity<String> {
        val user = loginService.login(request.userName, request.password)
        val token = JWT.create()
                .withSubject(user)
                .withExpiresAt(Date(System.currentTimeMillis() + sevenDays))
                .sign(HMAC512(token.toByteArray()))

        return ResponseEntity.ok(token)
    }
}
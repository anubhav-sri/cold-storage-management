package com.saikrishna.wms.controllers

import com.saikrishna.wms.services.LoginService
import org.springframework.http.ResponseEntity
import javax.servlet.http.Cookie
import javax.servlet.http.HttpServletResponse


class LoginController(private val loginService: LoginService) {

    fun login(userName: String, password: String, response: HttpServletResponse): ResponseEntity<String> {
        val user = loginService.login(userName, password)
        response.addCookie(Cookie("user", user))
        return ResponseEntity.ok(user)
    }

}
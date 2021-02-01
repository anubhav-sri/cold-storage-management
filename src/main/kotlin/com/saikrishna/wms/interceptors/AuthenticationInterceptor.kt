package com.saikrishna.wms.interceptors

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpMethod
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.servlet.HandlerInterceptor
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse


@ControllerAdvice
class AuthenticationInterceptor(@Value("\${jwt.token}") val jwtToken: String) : HandlerInterceptor {
	private val filterUrl: List<String> = listOf("/authenticate", "/logout")

	override fun preHandle(request: HttpServletRequest, response: HttpServletResponse, handler: Any): Boolean {

		if (!filterUrl.contains(request.requestURI.toString()) && request.method != HttpMethod.OPTIONS.name) {
			val authToken = request.getHeader("auth")
			if (authToken == null) {
				response.sendError(403)
				return false
			}
			authToken.let {
				try {
					JWT.require(Algorithm.HMAC512(jwtToken.toByteArray()))
							.build()
							.verify(it)
				} catch (ex: Exception) {
					response.sendError(403)
				}
			}
		}
		return super.preHandle(request, response, handler)
	}
}

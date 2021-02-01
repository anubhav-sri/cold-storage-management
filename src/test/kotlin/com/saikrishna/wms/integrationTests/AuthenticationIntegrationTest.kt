package com.saikrishna.wms.integrationTests

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.fasterxml.jackson.databind.ObjectMapper
import com.saikrishna.wms.models.LoginRequest
import com.saikrishna.wms.models.User
import com.saikrishna.wms.repositories.UserRepository
import org.hamcrest.Matchers
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import java.util.*


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
internal class AuthenticationIntegrationTest {

	@Autowired
	private lateinit var mockMvc: MockMvc

	@Autowired
	private lateinit var userRepository: UserRepository
	private val objectMapper: ObjectMapper = ObjectMapper()

	@Test
	fun shouldBeAbleToAuthenticateTheUser() {

		userRepository.save(User(username = "username", password = BCryptPasswordEncoder().encode("password")))
		val loginRequest = LoginRequest("username", "password")

		mockMvc.perform(MockMvcRequestBuilders.post("/authenticate")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(loginRequest)))
				.andExpect(MockMvcResultMatchers.status().isOk)
				.andExpect(MockMvcResultMatchers.content().string(Matchers.notNullValue()))
	}

	@Test
	fun shouldRespondWithHttp401IfPasswordIsWrong() {

		userRepository.save(User(username = "username", password = BCryptPasswordEncoder().encode("password")))
		val loginRequest = LoginRequest("username", "wrong_password")

		mockMvc.perform(MockMvcRequestBuilders.post("/authenticate")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(loginRequest)))
				.andExpect(MockMvcResultMatchers.status().isUnauthorized)
				.andExpect(MockMvcResultMatchers.status().reason("Wrong password"))
				.andExpect(MockMvcResultMatchers.content().string(Matchers.emptyString()))

	}


	@Test
	fun shouldRespondWithHttp403IfTokenMissing() {
		val loginRequest = LoginRequest("wrong_username", "wrong_password")

		mockMvc.perform(MockMvcRequestBuilders.post("/lot")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(loginRequest)))
				.andExpect(MockMvcResultMatchers.status().isForbidden)

	}

	@Test
	fun shouldRespondWithHttp403IfTokenExpired() {
		val loginRequest = LoginRequest("wrong_username", "wrong_password")
		val token = JWT.create()
				.withSubject("username+t")
				.withExpiresAt(Date(System.currentTimeMillis()))
				.sign(Algorithm.HMAC512("test".toByteArray()))

		mockMvc.perform(MockMvcRequestBuilders.post("/lot")
				.contentType(MediaType.APPLICATION_JSON)
				.header("token", token)
				.content(objectMapper.writeValueAsString(loginRequest)))
				.andExpect(MockMvcResultMatchers.status().isForbidden)

	}

	@AfterEach
	fun tearDown() {
		userRepository.deleteAll()
	}
}

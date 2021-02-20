package com.saikrishna.wms.integrationTests

import com.fasterxml.jackson.databind.ObjectMapper
import com.google.gson.Gson
import com.saikrishna.wms.controllers.CreateLotRequest
import com.saikrishna.wms.models.*
import com.saikrishna.wms.repositories.ClearanceRepository
import com.saikrishna.wms.repositories.LotRepository
import com.saikrishna.wms.repositories.UserRepository
import org.hamcrest.core.Is
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*
import java.time.LocalDateTime
import java.util.*


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
internal class DashboardntegrationTest {

	@Autowired
	private lateinit var mockMvc: MockMvc

	@Autowired
	private lateinit var lotRepository: LotRepository

	@Autowired
	private lateinit var clearanceRepository: ClearanceRepository

	@Autowired
	private lateinit var userRepository: UserRepository
	private lateinit var authToken: String
	private val objectMapper: ObjectMapper = ObjectMapper()

	@Test
	fun `should get summary`() {
		val averageWeight = Weight(12.0, Weight.WeightUnit.KG)
		val customer = Customer(UUID.randomUUID(), "", "fname", "", "9159989867")
		val createLotRequest = CreateLotRequest(customer, LocalDateTime.now().toString(),
				12, averageWeight.value, "G4",
				"KG", 10, true, "com")

		val start = LocalDateTime.now().minusDays(3)
		val end = LocalDateTime.now()

		lotRepository.save(createLotRequest.toLotDto(customer.id))

		mockMvc.perform(MockMvcRequestBuilders.get("/summary")
				.param("start", start.toString())
				.param("end", end.toString())
				.header("auth", authToken))
				.andExpect(status().isOk)
				.andExpect(jsonPath("$.numberOfEmptyBagsGiven", Is.`is`(10)))
				.andExpect(jsonPath("$.numberOfBags", Is.`is`(12)))

	}

	@Test
	fun `should get empty summary`() {
		val averageWeight = Weight(12.0, Weight.WeightUnit.KG)
		val customer = Customer(UUID.randomUUID(), "", "fname", "", "9159989867")
		val createLotRequest = CreateLotRequest(customer, LocalDateTime.now().toString(),
				12, averageWeight.value, "G4",
				"KG", 10, true, "com")

		val start = LocalDateTime.now().minusDays(3)
		val end = LocalDateTime.now().minusDays(2)

		mockMvc.perform(MockMvcRequestBuilders.get("/summary")
				.param("start", start.toString())
				.param("end", end.toString())
				.header("auth", authToken))
				.andExpect(status().isOk)
				.andExpect(content().string(""))

	}


	@BeforeEach
	fun beforeEach() {
		userRepository.save(User(username = "username", password = BCryptPasswordEncoder().encode("password"), role = Role.ADMIN))

		authToken = Gson().fromJson<LoginResponse>(mockMvc.perform(MockMvcRequestBuilders.post("/authenticate")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(LoginRequest("username", "password"))))
				.andExpect(status().isOk)
				.andReturn().response.contentAsString, LoginResponse::class.java).token

	}

	@AfterEach
	fun afterEach() {
		userRepository.deleteAll()
		clearanceRepository.deleteAll()
	}

}

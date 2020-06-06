package com.saikrishna.wms.integrationTests

import com.fasterxml.jackson.databind.ObjectMapper
import com.saikrishna.wms.controllers.CreateLotRequest
import com.saikrishna.wms.models.Customer
import com.saikrishna.wms.models.LoginRequest
import com.saikrishna.wms.models.User
import com.saikrishna.wms.models.Weight
import com.saikrishna.wms.models.dto.ClearanceDto
import com.saikrishna.wms.repositories.ClearanceRepository
import com.saikrishna.wms.repositories.LotRepository
import com.saikrishna.wms.repositories.UserRepository
import org.junit.jupiter.api.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import java.time.LocalDate
import java.util.*


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
internal class ClearanceIntegrationTest {

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
    fun `should add clearance`() {
        val averageWeight = Weight(12.0, Weight.WeightUnit.KG)
        val customer = Customer(UUID.randomUUID(), "", "fname", "", "9159989867")
        val createLotRequest = CreateLotRequest(customer, "2020-01-16T19:02:42.531",
                12, averageWeight.value, "G4",
                "KG", 10, true, "com")

        val savedLot = lotRepository.save(createLotRequest.toLotDto(customer.id))
        val clearanceDto = ClearanceDto(lotNumber = savedLot.serialNumber, numberOfBags = 10, date = "20-09-2020")

        mockMvc.perform(MockMvcRequestBuilders.post("/clearance")
                .contentType(MediaType.APPLICATION_JSON)
                .header("auth", authToken)
                .content(objectMapper.writeValueAsString(clearanceDto)))
                .andExpect(status().isOk)
    }

    @Test
    fun `should get clearances for a lot`() {
        val averageWeight = Weight(12.0, Weight.WeightUnit.KG)
        val customer = Customer(UUID.randomUUID(), "", "fname", "", "9159989867")
        val createLotRequest = CreateLotRequest(customer, "2020-01-16T19:02:42.531",
                12, averageWeight.value, "G4",
                "KG", 10, true, "com")

        val savedLot = lotRepository.save(createLotRequest.toLotDto(customer.id))
        val clearanceDto = ClearanceDto(lotNumber = savedLot.serialNumber, numberOfBags = 10, date = "20-09-2020")

        mockMvc.perform(MockMvcRequestBuilders.post("/clearance")
                .contentType(MediaType.APPLICATION_JSON)
                .header("auth", authToken)
                .content(objectMapper.writeValueAsString(clearanceDto)))
                .andExpect(status().isOk)

        mockMvc.perform(MockMvcRequestBuilders.get("/clearance")
                .param("lotNumber", "1")
                .header("auth", authToken)
                .content(objectMapper.writeValueAsString(clearanceDto)))
                .andExpect(status().isOk)
    }

    @Test
    fun `should return 404 if lot not found`() {
        mockMvc.perform(MockMvcRequestBuilders.get("/clearance")
                .param("lotNumber", "1")
                .header("auth", authToken))
                .andExpect(status().isNotFound)
    }

    @BeforeEach
    fun beforeEach() {
        userRepository.save(User(username = "username", password = BCryptPasswordEncoder().encode("password")))

        authToken = mockMvc.perform(MockMvcRequestBuilders.post("/authenticate")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(LoginRequest("username", "password"))))
                .andExpect(status().isOk)
                .andReturn().response.contentAsString
    }

    @AfterEach
    fun afterEach() {
        userRepository.deleteAll()
        clearanceRepository.deleteAll()
        lotRepository.deleteAll()

    }
}

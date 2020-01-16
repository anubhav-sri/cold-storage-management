package com.saikrishna.wms.controllers

import com.fasterxml.jackson.databind.ObjectMapper
import com.saikrishna.wms.models.Customer
import com.saikrishna.wms.models.Weight
import org.hamcrest.core.Is.`is`
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import java.time.LocalDateTime
import java.util.*


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
internal class LotIntegrationTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    private val objectMapper: ObjectMapper = ObjectMapper()

    @Test
    fun shouldBeAbleToCreateAndViewLotUsingPostAndGetCall() {
        val averageWeight = Weight(12.0, Weight.WeightUnit.KG)
        val customer = Customer(UUID.randomUUID(), "", "fname", "", "9159989867")
        val createLotRequest = CreateLotRequest(customer, "2020-01-16T19:02:42.531",
                12, averageWeight.value, "G4",
                "KG", 10, true, "com")

        mockMvc.perform(MockMvcRequestBuilders.post("/lot")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(createLotRequest)))
                .andExpect(status().isCreated)
                .andExpect(jsonPath("$.lot.serialNumber", `is`(1)))

        mockMvc.perform(MockMvcRequestBuilders.get("/lot/" + "1"))
                .andExpect(status().isOk)
                .andExpect(jsonPath("$.lot.numberOfBags", `is`(12)))
                .andExpect(jsonPath("$.customer.phoneNumber", `is`(customer.phoneNumber)))
                .andExpect(jsonPath("$.lot.serialNumber", `is`(1)))
                .andExpect(jsonPath("$.lot.palledariPaid", `is`(true)))
                .andExpect(jsonPath("$.lot.numberOfEmptyBagsGiven", `is`(10)))
                .andExpect(jsonPath("$.lot.comments", `is`("com")))
                .andExpect(jsonPath("$.lot.date", `is`("2020-01-16T19:02:42.531")))
                .andExpect(jsonPath("$.customer.fatherName", `is`("fname")))
    }
}

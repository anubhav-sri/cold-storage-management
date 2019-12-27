package com.saikrishna.wms.controllers

import com.fasterxml.jackson.databind.ObjectMapper
import com.saikrishna.wms.models.Weight
import com.saikrishna.wms.repositories.LotDto
import net.bytebuddy.implementation.bytecode.assign.Assigner
import org.hamcrest.core.Is.`is`
import org.junit.jupiter.api.Test
import org.mockito.internal.matchers.Equals
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*
import java.util.*


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//@WebMvcTest
@AutoConfigureMockMvc
internal class LotIntegrationTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    private val objectMapper: ObjectMapper = ObjectMapper()

    @Test
    fun shouldBeAbleToCreateLotUsingPostCall() {
        val averageWeight = Weight(12.0, Weight.WeightUnit.KG)
        val totalWeight = Weight(144.0, Weight.WeightUnit.KG)
        val customer = UUID.randomUUID()
        val expectedLotDto = LotDto(id = UUID.randomUUID(), numberOfBags = 12, averageWeight = averageWeight,
                totalWeight = totalWeight, customer = customer, type = "G4")

        val createLotRequest = CreateLotRequest(customer.toString(), 12, averageWeight.value, "G4",
                "KG")
        mockMvc.perform(MockMvcRequestBuilders.post("/lot")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(createLotRequest)))
                .andExpect(status().isCreated)
                .andExpect(jsonPath("$.numberOfBags", `is`(12)))

    }
}

package com.saikrishna.wms.controllers

import com.fasterxml.jackson.databind.ObjectMapper
import com.saikrishna.wms.models.Customer
import com.saikrishna.wms.models.Location
import com.saikrishna.wms.models.Weight
import com.saikrishna.wms.repositories.*
import org.hamcrest.CoreMatchers.hasItem
import org.hamcrest.Matchers.*
import org.hamcrest.core.Is.`is`
import org.junit.jupiter.api.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.mock.web.MockMultipartFile
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*
import java.util.*


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation::class)
internal class LotIntegrationTest {

    @Autowired
    private lateinit var mockMvc: MockMvc
    @Autowired
    private lateinit var lotRepository: LotRepository
    @Autowired
    private lateinit var locationRepo: LocationRepository
    private val objectMapper: ObjectMapper = ObjectMapper()

    @Test
    @Order(2)
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
                .andExpect(jsonPath("$.lot.serialNumber", `is`(lotRepository.count().toInt())))

        mockMvc.perform(MockMvcRequestBuilders.get("/lot/" + lotRepository.count().toInt()))
                .andExpect(status().isOk)
                .andExpect(jsonPath("$.lot.numberOfBags", `is`(12)))
                .andExpect(jsonPath("$.customer.phoneNumber", `is`(customer.phoneNumber)))
                .andExpect(jsonPath("$.lot.serialNumber", `is`(lotRepository.count().toInt())))
                .andExpect(jsonPath("$.lot.palledariPaid", `is`(true)))
                .andExpect(jsonPath("$.lot.numberOfEmptyBagsGiven", `is`(10)))
                .andExpect(jsonPath("$.lot.comments", `is`("com")))
                .andExpect(jsonPath("$.lot.date", `is`("2020-01-16T19:02:42.531")))
                .andExpect(jsonPath("$.customer.fatherName", `is`("fname")))
    }


    @Test
    @Order(1)
    fun shouldBeAbleToBulkUplaodLotData() {
        mockMvc.perform(MockMvcRequestBuilders
                .multipart("/uploadLotData")
                .file(MockMultipartFile("file", "dataFile.csv", null, this.javaClass.classLoader.getResourceAsStream("testFile.csv"))))
                .andExpect(status().isCreated)
                .andExpect(content().string("Saved 8 number of lots"))

        mockMvc.perform(MockMvcRequestBuilders.get("/lot/" + lotRepository.count().toInt()))
                .andExpect(status().isOk)
                .andExpect(jsonPath("$.lot.numberOfBags", `is`(50)))
                .andExpect(jsonPath("$.customer.phoneNumber", `is`("8953143293")))
                .andExpect(jsonPath("$.lot.serialNumber", `is`(lotRepository.count().toInt())))
                .andExpect(jsonPath("$.lot.palledariPaid", `is`(false)))
                .andExpect(jsonPath("$.lot.numberOfEmptyBagsGiven", `is`(0)))
                .andExpect(jsonPath("$.lot.date", `is`("2020-02-25T00:00:00")))
                .andExpect(jsonPath("$.customer.fatherName", `is`("KANHYAI LAL")))


    }

    @Test
    @Order(3)
    fun `should be able to upload the location of lots`() {
        locationRepo.save(Location("1-A-24", 1, 'A', 24))
        locationRepo.save(Location("1-A-25", 1, 'A', 25))
        val averageWeight = Weight(12.0, Weight.WeightUnit.KG)
        val customer = Customer(UUID.randomUUID(), "", "fname", "", "9159989867")
        val createLotRequest = CreateLotRequest(customer, "2020-01-16T19:02:42.531",
                12, averageWeight.value, "G4",
                "KG", 10, true, "com")

        mockMvc.perform(MockMvcRequestBuilders.post("/lot")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(createLotRequest)))
                .andExpect(status().isCreated)
                .andExpect(jsonPath("$.lot.serialNumber", `is`(lotRepository.count().toInt())))

        lotRepository.save(Lot(serialNumber = 2))

        mockMvc.perform(MockMvcRequestBuilders
                .multipart("/updateLotLocation")
                .file(MockMultipartFile("file", "dataFile.csv", null, this.javaClass.classLoader.getResourceAsStream("testFile_lot_location-multiple.csv"))))
                .andExpect(status().isCreated)
                .andExpect(content().string("Update Locations for 3 number of lots"))

        mockMvc.perform(MockMvcRequestBuilders.get("/lot/1"))
                .andExpect(status().isOk)
                .andExpect(jsonPath("$.lot.location").isArray)
                .andExpect(jsonPath("$.lot.location.[*].id.locationId", containsInAnyOrder("1-A-24","1-A-25")))


    }
}

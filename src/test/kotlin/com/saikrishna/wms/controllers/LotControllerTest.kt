package com.saikrishna.wms.controllers

import com.saikrishna.wms.models.Customer
import com.saikrishna.wms.models.Weight
import com.saikrishna.wms.repositories.LotDto
import com.saikrishna.wms.services.LotService
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import java.util.*

internal class LotControllerTest {
    private val lotService: LotService = mockk()

    @Test
    fun shouldBeAbleToSaveTheLot() {
        val lotController = LotController(lotService)
        val averageWeight = Weight(12.0, Weight.WeightUnit.KG)
        val totalWeight = Weight(144.0, Weight.WeightUnit.KG)
        val customer = Customer(UUID.randomUUID(), "name", "fname", "", 1212)
        val lotDto = LotDto(numberOfBags = 12, averageWeight = averageWeight, customer = customer.id, type = "G4")
        val expectedLot = LotDto(lotDto.id, lotDto.numberOfBags, averageWeight, totalWeight, customer.id, type = "G4")

        every { lotService.saveLot(any()) } returns expectedLot

        val createLotRequest = CreateLotRequest(
                customerId = customer.id.toString(),
                numberOfBags = 12,
                averageWeight = 12.0,
                type = "G4", weightUnit = Weight.WeightUnit.KG.name
        )
        val actualLot = lotController.createLot(createLotRequest)

        verify { lotService.saveLot(any()) }
        assertThat(actualLot.body).isEqualTo(expectedLot)

    }

    @Test
    fun shouldBeAbleToViewTheLot() {
        val lotController = LotController(lotService)
        val averageWeight = Weight(12.0, Weight.WeightUnit.KG)
        val totalWeight = Weight(144.0, Weight.WeightUnit.KG)
        val customerId = UUID.randomUUID()
        val lotId = UUID.randomUUID()
        val expectedLot = LotDto(lotId, 12, averageWeight, totalWeight,
                customerId, type = "G4", serialNumber = 1)

        every { lotService.findByLotNumber(1).get() } returns expectedLot

        val actualLot = lotController.getByLotNumber(1)

        verify { lotService.findByLotNumber(1) }
        assertThat(actualLot.body).isEqualTo(expectedLot)

    }
}

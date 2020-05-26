package com.saikrishna.wms.controllers

import com.saikrishna.wms.models.Customer
import com.saikrishna.wms.models.LotResponse
import com.saikrishna.wms.models.Weight
import com.saikrishna.wms.models.Lot
import com.saikrishna.wms.services.CustomerService
import com.saikrishna.wms.services.LotService
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import java.time.LocalDateTime
import java.util.*

internal class LotControllerTest {
    private val lotService: LotService = mockk()
    private val customerService: CustomerService = mockk()

    @Test
    fun shouldBeAbleToSaveTheLot() {
        val lotController = LotController(lotService, customerService)
        val averageWeight = Weight(12.0, Weight.WeightUnit.KG)
        val totalWeight = Weight(144.0, Weight.WeightUnit.KG)
        val customer = Customer(name = "name", fatherName = "fname", address = "addd", phoneNumber = "1212")
        val date = LocalDateTime.parse("2020-01-16T19:02:42.531")
        val lotDto = Lot(date = date, numberOfBags = 12, averageWeight = averageWeight, customer = customer.id, type = "G4"
                , isPalledariPaid = true, numberOfEmptyBagsGiven = 10, comments = "comments")
        val lot = Lot(lotDto.id, lotDto.date, lotDto.numberOfBags,
                averageWeight, totalWeight, customer.id, "G4",
                numberOfEmptyBagsGiven = lotDto.numberOfEmptyBagsGiven,
                isPalledariPaid = lotDto.isPalledariPaid, comments = lotDto.comments)
        val expectedLotResponse = LotResponse(customer,
                lot)

        every { lotService.saveLot(any()) } returns lot
        every { customerService.saveCustomer(any()) } returns customer

        val createLotRequest = CreateLotRequest(
                date = "2020-01-16T19:02:42.531",
                customer = customer,
                numberOfBags = 12,
                averageWeight = 12.0,
                type = "G4", weightUnit = Weight.WeightUnit.KG.name,
                numberOfEmptyBagsGiven = 10,
                comments = "comments", palledariPaid = true)
        val actualLot = lotController.createLot(createLotRequest)

        verify {
            lotService.saveLot(withArg {
                assertThat(it).isEqualToIgnoringGivenFields(lot, "id")
            })
        }

        verify {
            customerService.saveCustomer(withArg {
                assertThat(it).isEqualToIgnoringGivenFields(customer, "id")
            })
        }
        assertThat(actualLot.body).isEqualToIgnoringGivenFields(expectedLotResponse,
                "customer.id", "lot.id")

    }

    @Test
    fun shouldBeAbleToViewTheLot() {
        val lotController = LotController(lotService, customerService)
        val averageWeight = Weight(12.0, Weight.WeightUnit.KG)
        val totalWeight = Weight(144.0, Weight.WeightUnit.KG)
        val lotId = UUID.randomUUID()
        val customer = Customer(name = "name", fatherName = "fname", address = "", phoneNumber = "1212")

        val date = LocalDateTime.parse("2020-01-16T19:02:42.531")
        val lot = Lot(lotId, date, 12, averageWeight, totalWeight,
                customer.id, type = "G4", serialNumber = 1)
        val expectedLotResponse = LotResponse(customer,
                lot)

        every { lotService.findByLotNumber(1).get() } returns lot
        every { customerService.getCustomer(customer.id) } returns customer

        val actualLot = lotController.getByLotNumber(1)

        verify { lotService.findByLotNumber(1) }
        assertThat(actualLot.body).isEqualTo(expectedLotResponse)

    }
}

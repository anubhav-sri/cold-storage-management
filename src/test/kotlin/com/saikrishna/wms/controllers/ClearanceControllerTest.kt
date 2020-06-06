package com.saikrishna.wms.controllers

import com.nhaarman.mockitokotlin2.refEq
import com.saikrishna.wms.exceptions.LotNotFoundException
import com.saikrishna.wms.models.Clearance
import com.saikrishna.wms.models.Lot
import com.saikrishna.wms.models.dto.ClearanceDto
import com.saikrishna.wms.services.ClearanceService
import com.saikrishna.wms.services.LotService
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*

internal class ClearanceControllerTest {

    private val clearanceService: ClearanceService = mockk()
    private val lotService: LotService = mockk()

    @Test
    fun `should add a clearance for a lot`() {
        val lot = Lot(serialNumber = 1)
        val date = LocalDate.parse("20-09-2020", DateTimeFormatter.ofPattern("dd-MM-yyyy"))
        val clearanceDto = ClearanceDto(lotNumber = 1,
                numberOfBags = 1, date = "20-09-2020")
        val expectedClearance = Clearance(lot = lot, numberOfBags = 1, date = date)
        val expectedSavedClearance = ClearanceDto(id = 1, lotNumber = 1, numberOfBags = 1, date = "20-09-2020")

        every { lotService.findByLotNumber(1) } returns Optional.of(lot)
        every { clearanceService.saveClearance(expectedClearance) } returns Clearance(id = 1, lot = lot, numberOfBags = 1, date = date)

        val savedClearance = ClearanceController(clearanceService, lotService)
                .addClearance(clearanceDto)

        assertThat(savedClearance).isEqualTo(expectedSavedClearance)
        verify { clearanceService.saveClearance(expectedClearance) }
    }

    @Test
    fun `should get all clearances for a lot`() {
        val lot = Lot(serialNumber = 1)
        val date = LocalDate.parse("20-09-2020", DateTimeFormatter.ofPattern("dd-MM-yyyy"))
        val expectedSavedClearance = ClearanceDto(id = 1, lotNumber = 1, numberOfBags = 1, date = "20-09-2020")

        every { lotService.findByLotNumber(1) } returns Optional.of(lot)
        every { clearanceService.findAllForLot(lot) } returns listOf(Clearance(id = 1, lot = lot, numberOfBags = 1, date = date))

        val savedClearances = ClearanceController(clearanceService, lotService)
                .findAllClearanceForLot(lot.serialNumber)

        assertThat(savedClearances).containsOnly(expectedSavedClearance)
        verify { clearanceService.findAllForLot(lot) }
    }

    @Test
    fun `should throw lot not found exception of lot is not present`() {
        val clearanceDto = ClearanceDto(lotNumber = 1,
                numberOfBags = 1, date = "20-09-2020")

        every { lotService.findByLotNumber(1) } returns Optional.empty()

        Assertions.assertThrows(LotNotFoundException::class.java) {
            ClearanceController(clearanceService, lotService)
                    .addClearance(clearanceDto)
        }
    }
}
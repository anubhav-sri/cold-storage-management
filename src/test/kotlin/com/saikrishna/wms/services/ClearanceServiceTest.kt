package com.saikrishna.wms.services

import com.saikrishna.wms.exceptions.ClearanceNotAllowedException
import com.saikrishna.wms.models.Clearance
import com.saikrishna.wms.models.Lot
import com.saikrishna.wms.repositories.ClearanceRepository
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import java.time.LocalDate

internal class ClearanceServiceTest {
    private val clearanceRepository: ClearanceRepository = mockk()

    @Test
    fun shouldSaveTheClearanceForALot() {
        val clearance = Clearance(Lot(numberOfBags = 24), 23, LocalDate.now())
        every { clearanceRepository.save(clearance) } returns clearance
        ClearanceService(clearanceRepository).saveClearance(clearance);
        verify { clearanceRepository.save(clearance) }
    }

    @Test
    fun shouldThrowClearanceNotAllowedExceptionIfLotIsAlreadyExhausted() {
        val clearance = Clearance(Lot(numberOfBags = 1), 23, LocalDate.now())
        every { clearanceRepository.findAllByLot(clearance.lot) } returns listOf(clearance)
        every { clearanceRepository.save(clearance) } returns clearance
        Assertions.assertThrows(ClearanceNotAllowedException::class.java) {
            ClearanceService(clearanceRepository).saveClearance(clearance)
        }
    }

}

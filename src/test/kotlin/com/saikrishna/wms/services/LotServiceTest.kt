package com.saikrishna.wms.services

import com.saikrishna.wms.models.Customer
import com.saikrishna.wms.models.Weight
import com.saikrishna.wms.repositories.LotDto
import com.saikrishna.wms.repositories.LotRepository
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Test
import java.util.*

internal class LotServiceTest {

    private val lotRepo: LotRepository = mockk()

    private var lotService: LotService = LotService(lotRepo)

    @Test
    fun shouldSaveTheLot() {

        val customer = Customer(UUID.randomUUID(), "name",
                "fname", "addr1", 12342)
        val lot = LotDto(UUID.randomUUID(), 20,
                Weight(12.0, Weight.WeightUnit.KG),
                Weight(23.0, Weight.WeightUnit.KG),
                customer.id, type = "G4")

        every { lotRepo.save(lot) } returns lot
        lotService.saveLot(lot)
        verify { lotRepo.save(lot) }
    }

    @Test
    fun shouldFindTheLotBySerialNumber() {

        val customer = Customer(UUID.randomUUID(), "name",
                "fname", "addr1", 12342)
        val lot = LotDto(UUID.randomUUID(), 20,
                Weight(12.0, Weight.WeightUnit.KG),
                Weight(23.0, Weight.WeightUnit.KG),
                customer.id, type = "G4", serialNumber = 1)

        every { lotRepo.findById(1).get() } returns lot
        lotService.findByLotNumber(lot.serialNumber)
        verify { lotRepo.findById(lot.serialNumber) }
    }

}

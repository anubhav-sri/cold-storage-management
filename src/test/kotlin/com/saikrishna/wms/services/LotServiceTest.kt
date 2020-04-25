package com.saikrishna.wms.services

import com.nhaarman.mockitokotlin2.*
import com.saikrishna.wms.models.Customer
import com.saikrishna.wms.models.Weight
import com.saikrishna.wms.repositories.Lot
import com.saikrishna.wms.repositories.LotRepository
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.mockito.ArgumentCaptor
import java.time.LocalDateTime
import java.util.*

internal class LotServiceTest {

    private val lotRepo: LotRepository = mock()

    private var lotService: LotService = LotService(lotRepo)

    @Test
    fun shouldSaveTheLot() {
        val customer = Customer(UUID.randomUUID(), "name",
                "fname", "addr1", "12342")
        val lot = Lot(UUID.randomUUID(), LocalDateTime.now(), 20,
                Weight(12.0, Weight.WeightUnit.KG),
                customer = customer.id, type = "G4")

        val passedLot = Lot(UUID.randomUUID(), LocalDateTime.now(), 20,
                Weight(12.0, Weight.WeightUnit.KG),
                Weight(240.0, Weight.WeightUnit.KG),
                customer = customer.id, type = "G4")

        val expectedLot = Lot(UUID.randomUUID(), LocalDateTime.now(), 20,
                Weight(12.0, Weight.WeightUnit.KG),
                Weight(240.0, Weight.WeightUnit.KG),
                customer = customer.id, type = "G4")

        val lotCaptor: ArgumentCaptor<Lot> = ArgumentCaptor.forClass(Lot::class.java);

        given(lotRepo.save(lotCaptor.capture())).willReturn(expectedLot)

        val savedLot = lotService.saveLot(lot)

        assertThat(savedLot).isEqualTo(expectedLot)
        assertThat(lotCaptor.firstValue)
                .isEqualToIgnoringGivenFields(passedLot,
                        "id", "date")
    }

    @Test
    fun `should save multiple lots`() {
        val customer = Customer(UUID.randomUUID(), "name",
                "fname", "addr1", "12342")
        val lot = Lot(UUID.randomUUID(), LocalDateTime.now(), 20,
                Weight(12.0, Weight.WeightUnit.KG),
                Weight(23.0, Weight.WeightUnit.KG),
                customer.id, type = "G4")

        given(lotRepo.saveAll(listOf(lot))).willReturn(listOf(lot))
        lotService.saveAll(listOf(lot))
        verify(lotRepo).saveAll(listOf(lot))
    }

    @Test
    fun shouldFindTheLotBySerialNumber() {

        val customer = Customer(UUID.randomUUID(), "name",
                "fname", "addr1", "12342")
        val lot = Lot(UUID.randomUUID(), LocalDateTime.now(), 20,
                Weight(12.0, Weight.WeightUnit.KG),
                Weight(23.0, Weight.WeightUnit.KG),
                customer.id, type = "G4", serialNumber = 1)

        given(lotRepo.findById(1)).willReturn(Optional.of(lot))
        lotService.findByLotNumber(lot.serialNumber)
        verify(lotRepo).findById(lot.serialNumber)
    }

}

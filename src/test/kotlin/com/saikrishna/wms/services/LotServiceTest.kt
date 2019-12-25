package com.saikrishna.wms.services

import com.saikrishna.wms.models.Customer
import com.saikrishna.wms.models.Name
import com.saikrishna.wms.models.Weight
import com.saikrishna.wms.repositories.LotDto
import com.saikrishna.wms.repositories.LotRepository
import org.junit.jupiter.api.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import java.util.*

internal class LotServiceTest {

    private val lotRepo: LotRepository = mock(LotRepository::class.java)

    private var lotService: LotService = LotService(lotRepo)

    @Test
    fun shouldSaveTheLot() {
        val name = Name("", "", "")
        val customer = Customer(name,
                name, "addr1", 12342)
        val lot = LotDto(UUID.randomUUID(), 20,
                Weight(12.0, Weight.WeightUnit.KG),
                Weight(23.0, Weight.WeightUnit.KG),
                customer)
        lotService.saveLot(lot)

        verify(lotRepo).save(lot)


    }

}

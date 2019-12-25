package com.saikrishna.wms.services

import com.saikrishna.wms.repositories.LotDto
import com.saikrishna.wms.models.Weight
import com.saikrishna.wms.repositories.LotRepository

class LotService(private val lotRepo: LotRepository) {
    fun saveLot(lot: LotDto) {
        lot.totalWeight = Weight(lot.averageWeight.value.times(lot.numberOfBags), lot.averageWeight.unit)
        lotRepo.save(lot)
    }
}

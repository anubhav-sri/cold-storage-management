package com.saikrishna.wms.services

import com.saikrishna.wms.repositories.LotDto
import com.saikrishna.wms.models.Weight
import com.saikrishna.wms.repositories.LotRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class LotService(@Autowired private val lotRepo: LotRepository) {
    fun saveLot(lot: LotDto) : LotDto {
        lot.totalWeight = Weight(lot.averageWeight.value.times(lot.numberOfBags), lot.averageWeight.unit)
        return lotRepo.save(lot)
    }
}
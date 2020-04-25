package com.saikrishna.wms.services

import com.saikrishna.wms.models.Weight
import com.saikrishna.wms.repositories.Lot
import com.saikrishna.wms.repositories.LotRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.*

@Service
class LotService(@Autowired private val lotRepo: LotRepository

) {
    fun saveLot(lot: Lot): Lot {
        lot.totalWeight = Weight(lot.averageWeight.value.times(lot.numberOfBags), lot.averageWeight.unit)
        return lotRepo.save(lot)
    }

    fun findByLotNumber(serialNumber: Int): Optional<Lot> {
        return lotRepo.findById(serialNumber)
    }

    fun saveAll(lots: List<Lot>): List<Lot> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}

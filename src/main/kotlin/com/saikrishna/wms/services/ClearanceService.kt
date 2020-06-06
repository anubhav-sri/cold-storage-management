package com.saikrishna.wms.services

import com.saikrishna.wms.exceptions.ClearanceNotAllowedException
import com.saikrishna.wms.models.Clearance
import com.saikrishna.wms.models.Lot
import com.saikrishna.wms.repositories.ClearanceRepository
import org.springframework.stereotype.Service

@Service
class ClearanceService(private val clearanceRepository: ClearanceRepository) {
    fun saveClearance(clearance: Clearance): Clearance {
        val existingClearance = clearanceRepository.findAllByLot(clearance.lot)
        val totalBagsCleared = existingClearance.sumBy { it.numberOfBags }

        if (clearance.lot.numberOfBags - totalBagsCleared < clearance.numberOfBags) {
            throw ClearanceNotAllowedException(clearance.lot.serialNumber)
        }

        return clearanceRepository.save(clearance)
    }

    fun findAllForLot(lot: Lot): List<Clearance> {
        return clearanceRepository.findAllByLot(lot)
    }
}
package com.saikrishna.wms.services

import com.saikrishna.wms.models.Clearance
import com.saikrishna.wms.repositories.ClearanceRepository

class ClearanceService(private val clearanceRepository: ClearanceRepository) {
    fun saveClearance(clearance: Clearance): Clearance {
        return clearanceRepository.save(clearance)
    }
}
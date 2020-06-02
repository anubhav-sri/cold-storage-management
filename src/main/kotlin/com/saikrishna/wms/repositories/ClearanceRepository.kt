package com.saikrishna.wms.repositories

import com.saikrishna.wms.models.Clearance
import com.saikrishna.wms.models.Lot
import org.springframework.data.repository.CrudRepository

interface ClearanceRepository : CrudRepository<Clearance, Int> {
    fun findAllByLot(lot: Lot): List<Clearance>
}
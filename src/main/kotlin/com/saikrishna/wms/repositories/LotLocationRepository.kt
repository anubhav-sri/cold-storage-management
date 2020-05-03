package com.saikrishna.wms.repositories

import com.saikrishna.wms.models.LotLocation
import com.saikrishna.wms.models.LotLocationId
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface LotLocationRepository : CrudRepository<LotLocation, LotLocationId>

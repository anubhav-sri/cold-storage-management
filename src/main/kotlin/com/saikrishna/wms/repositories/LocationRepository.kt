package com.saikrishna.wms.repositories

import com.saikrishna.wms.models.Location
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface LocationRepository : CrudRepository<Location, String>

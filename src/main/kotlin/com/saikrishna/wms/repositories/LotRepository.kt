package com.saikrishna.wms.repositories

import org.springframework.data.repository.CrudRepository
import java.util.*

interface LotRepository : CrudRepository<LotDto, UUID> {

}

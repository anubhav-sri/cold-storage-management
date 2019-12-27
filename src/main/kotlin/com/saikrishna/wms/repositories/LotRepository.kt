package com.saikrishna.wms.repositories

import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface LotRepository : CrudRepository<LotDto, UUID> {

}

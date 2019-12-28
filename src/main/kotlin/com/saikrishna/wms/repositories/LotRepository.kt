package com.saikrishna.wms.repositories

import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface LotRepository : CrudRepository<LotDto, Int> {

}

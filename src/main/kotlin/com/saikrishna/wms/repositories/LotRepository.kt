package com.saikrishna.wms.repositories

import com.saikrishna.wms.models.Lot
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface LotRepository : CrudRepository<Lot, Int> {

}

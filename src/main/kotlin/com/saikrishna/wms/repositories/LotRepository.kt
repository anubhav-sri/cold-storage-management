package com.saikrishna.wms.repositories

import com.saikrishna.wms.models.Lot
import com.saikrishna.wms.models.Summary
import org.springframework.data.jdbc.repository.query.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import java.time.LocalDateTime

@Repository
interface LotRepository : CrudRepository<Lot, Int> {

	@Query("select sum(lot.number_of_bags) as totalNumberOfBags, sum(lot.number_of_empty_bags_given) as numberOfEmptyBags from lotdb.lot where date between ?1 and ?2 limit 1")
	fun findSummaryByDateBetween(start: LocalDateTime, end: LocalDateTime) : List<Summary>
}

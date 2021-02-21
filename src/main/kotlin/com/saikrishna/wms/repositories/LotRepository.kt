package com.saikrishna.wms.repositories

import com.saikrishna.wms.models.Lot
import com.saikrishna.wms.models.Summary
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import java.sql.Date
import java.time.LocalDateTime

@Repository
interface LotRepository : JpaRepository<Lot, Int> {

	fun findAllByDateBetween(start: LocalDateTime, end: LocalDateTime): List<Lot>
}

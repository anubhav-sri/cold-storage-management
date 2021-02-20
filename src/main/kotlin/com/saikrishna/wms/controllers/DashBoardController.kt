package com.saikrishna.wms.controllers

import com.saikrishna.wms.models.Summary
import com.saikrishna.wms.services.LotService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.format.annotation.DateTimeFormat
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.time.LocalDateTime

@RestController
@CrossOrigin
class DashBoardController(@Autowired private val lotService: LotService) {

	@GetMapping("/summary", produces = ["application/json"])
	fun getSummary(@RequestParam("start")
				   @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
				   from: LocalDateTime,
				   @RequestParam("end")
				   @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
				   to: LocalDateTime): ResponseEntity<Summary> {
		return ResponseEntity.ok().body(lotService.findSummaryBetween(from, to))
	}

}

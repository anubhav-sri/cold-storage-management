package com.saikrishna.wms.controllers

import com.saikrishna.wms.repositories.LotDto
import com.saikrishna.wms.services.LotService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import java.net.URI

@RestController
class LotController(@Autowired private val lotService: LotService) {

    @PostMapping(value = ["/lot"], consumes = ["application/json"], produces = ["application/json"])
    fun createLot(@RequestBody lotRequest: CreateLotRequest): ResponseEntity<LotDto> {

        val lot = lotService.saveLot(lotRequest.toLotDto())
        return ResponseEntity.created(URI.create("/lot/" + lot.id)).body(lot)
    }
}

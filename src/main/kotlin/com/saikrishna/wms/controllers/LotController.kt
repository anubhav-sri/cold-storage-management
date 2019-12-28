package com.saikrishna.wms.controllers

import com.saikrishna.wms.repositories.LotDto
import com.saikrishna.wms.services.LotService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.net.URI

@RestController
class LotController(@Autowired private val lotService: LotService) {

    @PostMapping(value = ["/lot"], consumes = ["application/json"], produces = ["application/json"])
    fun createLot(@RequestBody lotRequest: CreateLotRequest): ResponseEntity<LotDto> {
        val lot = lotService.saveLot(lotRequest.toLotDto())
        return ResponseEntity.created(URI.create("/lot/" + lot.serialNumber)).body(lot)
    }

    @GetMapping("/lot/{serialNumber}", produces = ["application/json"])
    fun getByLotNumber(@PathVariable serialNumber: Int): ResponseEntity<LotDto> {
        return ResponseEntity.ok().body(lotService.findByLotNumber(serialNumber).get())
    }
}

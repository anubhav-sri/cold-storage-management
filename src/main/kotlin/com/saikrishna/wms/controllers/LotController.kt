package com.saikrishna.wms.controllers

import com.saikrishna.wms.models.LotResponse
import com.saikrishna.wms.services.CustomerService
import com.saikrishna.wms.services.LotService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.net.URI

@RestController
class LotController(@Autowired private val lotService: LotService,
                    @Autowired private val customerService: CustomerService) {

    @PostMapping(value = ["/lot"], consumes = ["application/json"], produces = ["application/json"])
    fun createLot(@RequestBody lotRequest: CreateLotRequest): ResponseEntity<LotResponse> {
        val customer = customerService.saveCustomer(lotRequest.toCustomerDto())
        val lot = lotService.saveLot(lotRequest.toLotDto(customer.id))

        return ResponseEntity.created(URI.create("/lot/" + lot.serialNumber))
                .body(LotResponse(customer, lot))
    }

    @GetMapping("/lot/{serialNumber}", produces = ["application/json"])
    fun getByLotNumber(@PathVariable serialNumber: Int): ResponseEntity<LotResponse> {
        val lot = lotService.findByLotNumber(serialNumber).get()
        val customer = customerService.getCustomer(lot.customer)
        return ResponseEntity.ok().body(LotResponse(customer, lot))
    }
}

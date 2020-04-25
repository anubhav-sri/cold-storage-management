package com.saikrishna.wms.controllers

import com.saikrishna.wms.exceptions.InvalidFileNameException
import com.saikrishna.wms.services.CsvLotParser
import com.saikrishna.wms.services.CustomerService
import com.saikrishna.wms.services.LotService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile

@RestController
class BulkLotController(@Autowired private val lotService: LotService,
                        @Autowired private val customerService: CustomerService,
                        @Autowired private val csvLotParser: CsvLotParser

) {

    @PostMapping(value = ["/uploadLotData"], consumes = ["multipart/form-data"], produces = ["application/json"])
    @ResponseStatus(value = HttpStatus.CREATED)
    fun loadData(@RequestParam("file") dataFile: MultipartFile): String {
        if (!dataFile.originalFilename?.endsWith(".csv")!!) {
            throw InvalidFileNameException(dataFile.name)
        }
        val customerLots = csvLotParser.parseCsvToLot(dataFile)
        lotService.saveAll(customerLots.map { customerLot -> customerLot.toLot() })
        customerService.saveAll(customerLots.map { customerLot -> customerLot.toCustomer() })
        return "Saved ${customerLots.size} number of lots"
    }
}

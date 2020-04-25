package com.saikrishna.wms.controllers

import com.saikrishna.wms.exceptions.InvalidFileNameException
import com.saikrishna.wms.services.CsvLotParser
import com.saikrishna.wms.services.CustomerService
import com.saikrishna.wms.services.LotService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.multipart.MultipartFile

class BulkLotController(@Autowired private val lotService: LotService,
                        @Autowired private val customerService: CustomerService,
                        @Autowired private val csvLotParser: CsvLotParser

) {

    @PostMapping(value = ["/uploadLotData"], consumes = ["application/json"], produces = ["application/json"])
    fun loadData(@RequestBody dataFile: MultipartFile): String {
        if (!dataFile.name.endsWith(".csv")) {
            throw InvalidFileNameException(dataFile.name)
        }
        val customerLots = csvLotParser.parseCsvToLot(dataFile)
        lotService.saveAll(customerLots.map { customerLot -> customerLot.lot })
        customerService.saveAll(customerLots.map { customerLot -> customerLot.customer })
        return "Saved ${customerLots.size} number of lots"
    }
}

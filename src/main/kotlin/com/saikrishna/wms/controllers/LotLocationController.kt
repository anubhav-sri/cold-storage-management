package com.saikrishna.wms.controllers

import com.saikrishna.wms.exceptions.InvalidFileNameException
import com.saikrishna.wms.services.CsvLotParser
import com.saikrishna.wms.services.LocationService
import com.saikrishna.wms.services.LotService
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile

@RestController
@CrossOrigin
class LotLocationController(private val lotService: LotService,
                            private val csvLotParser: CsvLotParser,
                            private val locationService: LocationService) {

    @PostMapping(value = ["/updateLotLocation"], consumes = ["multipart/form-data"], produces = ["application/json"])
    @ResponseStatus(value = HttpStatus.CREATED)
    fun loadLotLocations(@RequestParam("file") dataFile: MultipartFile): String {
        if (!dataFile.originalFilename?.endsWith(".csv")!!) {
            throw InvalidFileNameException(dataFile.name)
        }
        val lotLocations = csvLotParser.parseCsvToLotLocation(dataFile)

        lotService.updateLocations(lotLocations.map { lotLocationDTO ->
            lotLocationDTO.toLotLocation(lotService.findByLotNumber(lotLocationDTO.lotNumber).orElseThrow(),
                    locationService.findById(lotLocationDTO.locationVal))

        })
        return "Update Locations for ${lotLocations.size} number of lots"
    }

}
package com.saikrishna.wms.controllers

import com.saikrishna.wms.exceptions.LotNotFoundException
import com.saikrishna.wms.mappers.ClearanceMapper
import com.saikrishna.wms.models.dto.ClearanceDto
import com.saikrishna.wms.services.ClearanceService
import com.saikrishna.wms.services.LotService
import org.springframework.web.bind.annotation.*

@RestController
class ClearanceController(private val clearanceService: ClearanceService,
                          private val lotService: LotService) {

    @PostMapping(value = ["/clearance"], consumes = ["application/json"], produces = ["application/json"])
    fun addClearance(@RequestBody clearanceDto: ClearanceDto): ClearanceDto {
        val lot = lotService.findByLotNumber(clearanceDto.lotNumber)
        return ClearanceMapper.toDto(clearanceService.saveClearance(ClearanceMapper.toClearance(clearanceDto,
                lot.orElseThrow { LotNotFoundException(clearanceDto.lotNumber) })))
    }

    @GetMapping(value = ["/clearance"], produces = ["application/json"])
    fun findAllClearanceForLot(@RequestParam lotNumber: Int): List<ClearanceDto> {
        return clearanceService.findAllForLot(lotService
                .findByLotNumber(lotNumber)
                .orElseThrow { LotNotFoundException(lotNumber) })
                .map { ClearanceMapper.toDto(it) }
    }

}
package com.saikrishna.wms.controllers

import com.saikrishna.wms.exceptions.LotNotFoundException
import com.saikrishna.wms.mappers.ClearanceMapper
import com.saikrishna.wms.models.dto.ClearanceDto
import com.saikrishna.wms.services.ClearanceService
import com.saikrishna.wms.services.LotService

class ClearanceController(private val clearanceService: ClearanceService,
                          private val lotService: LotService) {
    fun addClearance(clearanceDto: ClearanceDto): ClearanceDto {
        val lot = lotService.findByLotNumber(clearanceDto.lotNumber)
        return ClearanceMapper.toDto(clearanceService.saveClearance(ClearanceMapper.toClearance(clearanceDto,
                lot.orElseThrow { LotNotFoundException(clearanceDto.lotNumber) })))
    }

}
package com.saikrishna.wms.mappers

import com.saikrishna.wms.models.Clearance
import com.saikrishna.wms.models.Lot
import com.saikrishna.wms.models.dto.ClearanceDto
import java.time.LocalDate
import java.time.format.DateTimeFormatter

private val dateTimeFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy")

class ClearanceMapper {

    companion object Mapper {
        fun toClearance(dto: ClearanceDto, lot: Lot): Clearance {
            return Clearance(lot = lot, numberOfBags = dto.numberOfBags, date = LocalDate.parse(dto.date, dateTimeFormatter))
        }

        fun toDto(clearance: Clearance): ClearanceDto {
            return ClearanceDto(id = clearance.id, lotNumber = clearance.lot.serialNumber, numberOfBags = clearance.numberOfBags, date = clearance.date.format(dateTimeFormatter))
        }
    }
}

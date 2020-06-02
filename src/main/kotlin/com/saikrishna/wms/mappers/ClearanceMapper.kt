package com.saikrishna.wms.mappers

import com.saikrishna.wms.models.Clearance
import com.saikrishna.wms.models.Lot
import com.saikrishna.wms.models.dto.ClearanceDto

class ClearanceMapper {

    companion object Mapper {
        fun toClearance(dto: ClearanceDto, lot: Lot): Clearance {
            return Clearance(lot = lot, numberOfBags = dto.numberOfBags, date = dto.date)
        }

        fun toDto(clearance: Clearance): ClearanceDto {
            return ClearanceDto(id = clearance.id, lotNumber = clearance.lot.serialNumber, numberOfBags = clearance.numberOfBags, date = clearance.date)
        }
    }
}

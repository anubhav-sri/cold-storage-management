package com.saikrishna.wms.controllers

import com.saikrishna.wms.models.Weight
import com.saikrishna.wms.repositories.LotDto
import java.util.*

class CreateLotRequest(val customerId: String = "",
                       val numberOfBags: Int = 0,
                       val averageWeight: Double = 0.0,
                       val type: String = "",
                       val weightUnit: String = "") {

    fun toLotDto(): LotDto {
        return LotDto(UUID.randomUUID(),
                numberOfBags,
                Weight(averageWeight, Weight.WeightUnit.valueOf(weightUnit)),
                Weight(averageWeight.times(numberOfBags), Weight.WeightUnit.valueOf(weightUnit)),
                UUID.fromString(customerId),
                type)

    }
}


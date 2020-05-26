package com.saikrishna.wms.controllers

import com.saikrishna.wms.models.Customer
import com.saikrishna.wms.models.Weight
import com.saikrishna.wms.models.Lot
import java.time.LocalDateTime
import java.util.*

class CreateLotRequest(val customer: Customer = Customer(),
                       val date: String,
                       val numberOfBags: Int = 0,
                       val averageWeight: Double = 0.0,
                       val type: String = "",
                       val weightUnit: String = "",
                       val numberOfEmptyBagsGiven: Int = 0,
                       val palledariPaid: Boolean,
                       val comments: String) {

    fun toLotDto(customerId: UUID): Lot {
        return Lot(UUID.randomUUID(),
                LocalDateTime.parse(date),
                numberOfBags,
                Weight(averageWeight, Weight.WeightUnit.valueOf(weightUnit)),
                Weight(averageWeight.times(numberOfBags), Weight.WeightUnit.valueOf(weightUnit)),
                customerId,
                type,
                numberOfEmptyBagsGiven,
                palledariPaid,
                comments
        )

    }

    fun toCustomerDto(): Customer {
        return Customer(UUID.randomUUID(),
                customer.name, customer.fatherName, customer.address, customer.phoneNumber)

    }
}


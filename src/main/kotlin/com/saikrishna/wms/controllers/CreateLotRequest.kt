package com.saikrishna.wms.controllers

import com.saikrishna.wms.models.Customer
import com.saikrishna.wms.models.Weight
import com.saikrishna.wms.repositories.Lot
import java.util.*

class CreateLotRequest(val customer: Customer = Customer(),
                       val numberOfBags: Int = 0,
                       val averageWeight: Double = 0.0,
                       val type: String = "",
                       val weightUnit: String = "") {

    fun toLotDto(customerId: UUID): Lot {
        return Lot(UUID.randomUUID(),
                numberOfBags,
                Weight(averageWeight, Weight.WeightUnit.valueOf(weightUnit)),
                Weight(averageWeight.times(numberOfBags), Weight.WeightUnit.valueOf(weightUnit)),
                customerId,
                type)

    }

    fun toCustomerDto(): Customer {
        return Customer(UUID.randomUUID(),
                customer.name, customer.fatherName, customer.address, customer.phoneNumber)

    }
}


package com.saikrishna.wms.models


class LotResponse(val customer: Customer, val lot: Lot){
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as LotResponse

        if (customer != other.customer) return false
        if (lot != other.lot) return false

        return true
    }

    override fun hashCode(): Int {
        var result = customer.hashCode()
        result = 31 * result + lot.hashCode()
        return result
    }
}

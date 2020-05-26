package com.saikrishna.wms.models.dto

import com.fasterxml.jackson.annotation.JsonProperty
import com.saikrishna.wms.models.Customer
import com.saikrishna.wms.models.Loan
import com.saikrishna.wms.models.Weight
import com.saikrishna.wms.models.Lot
import java.math.BigDecimal
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*


class CustomerLot(
        @JsonProperty("LOT NO.")
        val lotNumber: Int = 0,
        @JsonProperty("NAME")
        val name: String = "",
        @JsonProperty("FATHERS NAME")
        val fatherName: String = "",
        @JsonProperty("VILLAGE")
        val address: String = "",
        @JsonProperty("MOBILE NO.")
        val phoneNumber: String = "",
        @JsonProperty("DATE")
        val date: String = "",
        @JsonProperty("NO. OF POTATO BAGS")
        val numberOfBags: Int = 0,
        @JsonProperty("AVERAGE WEIGHT")
        val averageWeight: Double = 50.0,
        @JsonProperty("POTATO TYPE")
        val type: String = "",
        @JsonProperty("NO. OF EMPTY BAGS PROVIDED")
        val numberOfEmptyBagsGiven: Int = 0,
        @JsonProperty("PALLEDARI PAID")
        val palledariPaid: Boolean = false,
        @JsonProperty("COMMENTS")
        val comments: String = "",
        @JsonProperty("LOAN")
        val loanAmount: BigDecimal? = null) {

    private val customerId: UUID = UUID.randomUUID()

    fun toCustomer(): Customer {
        return Customer(customerId, name, fatherName, address, phoneNumber)
    }

    fun toLot(): Lot {
        val lot = Lot(UUID.randomUUID(),
                LocalDate.parse(date, DateTimeFormatter.ofPattern("dd-MM-yyyy")).atStartOfDay()
                , numberOfBags, Weight(averageWeight), Weight(averageWeight.times(numberOfBags)), customerId,
                type, numberOfEmptyBagsGiven, palledariPaid, comments, lotNumber)

        loanAmount?.let { lot.addLoan(Loan(lotNumber, it, lot.date.toLocalDate())) }
        return lot
    }
}
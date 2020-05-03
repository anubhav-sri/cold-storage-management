package com.saikrishna.wms.models

import com.fasterxml.jackson.annotation.JsonProperty
import com.saikrishna.wms.repositories.Lot
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class LotLocationDTO(
        @JsonProperty("LOT NO.")
        val lotNumber: Int,
        @JsonProperty("DATE")
        val date: String,
        @JsonProperty("LOCATION")
        val locationVal: String) {
    fun toLotLocation(lot: Lot, location: Location): LotLocation {
        return LotLocation(LotLocationId(lotNumber, locationVal), LocalDate.parse(date, DateTimeFormatter.ofPattern("dd-MM-yyyy")), location, lot)
    }

}
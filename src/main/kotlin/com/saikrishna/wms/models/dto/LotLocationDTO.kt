package com.saikrishna.wms.models.dto

import com.fasterxml.jackson.annotation.JsonProperty
import com.saikrishna.wms.models.Location
import com.saikrishna.wms.models.Lot
import com.saikrishna.wms.models.LotLocation
import com.saikrishna.wms.models.LotLocationId
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class LotLocationDTO(
		@JsonProperty("LOT NO.")
		val lotNumber: Int,
		@JsonProperty("DATE")
		val date: String,
		@JsonProperty("CHAMBER")
		val chamber: Int,
		@JsonProperty("FLOOR")
		val floor: Char,
		@JsonProperty("RACK")
		val rack: Int
) {
	fun toLotLocation(lot: Lot, location: Location): LotLocation {
		return LotLocation(LotLocationId(lotNumber, location.id),
				LocalDate.parse(date, DateTimeFormatter.ofPattern("dd-MM-yyyy")), location, lot)
	}

}

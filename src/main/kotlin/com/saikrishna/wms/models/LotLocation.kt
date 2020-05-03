package com.saikrishna.wms.models

import com.fasterxml.jackson.annotation.JsonIgnore
import com.saikrishna.wms.repositories.Lot
import java.time.LocalDate
import javax.persistence.*

@Entity
@Table(name = "lot_locations")
data class LotLocation(
        @EmbeddedId
        val id: LotLocationId = LotLocationId(),
        val date: LocalDate = LocalDate.now(),
        @ManyToOne(fetch = FetchType.LAZY)
        @MapsId("locationId")
        @JsonIgnore
        val location: Location = Location(),
        @ManyToOne(fetch = FetchType.LAZY)
        @MapsId("lotNumber")
        @JsonIgnore
        val lot: Lot = Lot())
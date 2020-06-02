package com.saikrishna.wms.models

import lombok.Builder
import lombok.Getter
import java.time.LocalDate
import javax.persistence.*

@Entity
@Builder
@Getter
data class Clearance(
        @Id
        @GeneratedValue(strategy = GenerationType.SEQUENCE,
                generator = "clearance_id_seq")
        val id: Int? = null,
        @ManyToOne
        val lot: Lot,
        val numberOfBags: Int,
        val date: LocalDate) {

    constructor(lot: Lot, numberOfBags: Int,
                date: LocalDate) : this(id = null, lot = lot, numberOfBags = numberOfBags,
            date = date)
}
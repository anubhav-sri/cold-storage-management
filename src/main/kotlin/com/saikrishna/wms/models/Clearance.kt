package com.saikrishna.wms.models

import java.time.LocalDate
import javax.persistence.*

@Entity
class Clearance(
        @Id
        @GeneratedValue(strategy = GenerationType.SEQUENCE,
                generator = "clearance_id_seq")
        private val id: Int? = null,
        @ManyToOne
        private val lot: Lot,
        private val numberOfBags: Int,
        private val date: LocalDate) {

    constructor(lot: Lot, numberOfBags: Int,
                date: LocalDate) : this(id = null, lot = lot, numberOfBags = numberOfBags,
            date = date)
}
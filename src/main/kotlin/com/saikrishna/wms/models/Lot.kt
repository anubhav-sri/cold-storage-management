package com.saikrishna.wms.models

import java.time.LocalDate
import java.time.LocalDateTime
import java.util.*
import javax.persistence.*

@Entity
data class Lot(
        var id: UUID = UUID.randomUUID(),
        var date: LocalDateTime = LocalDateTime.now(),
        val numberOfBags: Int = 0,
        @Embedded
        @AttributeOverrides(value = [AttributeOverride(name = "value", column = Column(name = "avg_weight_value")), AttributeOverride(name = "unit", column = Column(name = "avg_weight_unit"))])
        val averageWeight: Weight = Weight(0.0, Weight.WeightUnit.KG),
        @Embedded
        @AttributeOverrides(value = [
            AttributeOverride(name = "value", column = Column(name = "tot_weight_value")),
            AttributeOverride(name = "unit", column = Column(name = "tot_weight_unit"))
        ])
        var totalWeight: Weight? = Weight(0.0, Weight.WeightUnit.KG),
        val customer: UUID = UUID.randomUUID(),
        val type: String = "",
        val numberOfEmptyBagsGiven: Int = 0,
        val isPalledariPaid: Boolean = false,
        val comments: String = "",
        @Id
		@SequenceGenerator(name="lot_serial_number_seq",sequenceName="lot_serial_number_seq", allocationSize = 1)
		@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="lot_serial_number_seq")
        var serialNumber: Int = 0
) {

    @OneToMany(
            targetEntity = LotLocation::class,
            cascade = [CascadeType.ALL]
    )
    var location: Set<LotLocation> = hashSetOf()

    @OneToOne(cascade = [CascadeType.ALL])
    var loan: Loan? = null

    fun addLocation(location: Location, date: LocalDate) {
        this.location = this.location.plusElement(LotLocation(id = LotLocationId(this.serialNumber, location.id), date = date, location = location, lot = this))
    }

    fun addLoan(loan: Loan) {
        this.loan = loan
    }


//    @CreatedDate
//    lateinit var createdAt: LocalDateTime
//    @LastModifiedDate
//    lateinit var updatedAt: LocalDateTime
}

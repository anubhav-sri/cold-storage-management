package com.saikrishna.wms.repositories

import com.saikrishna.wms.models.Weight
import java.util.*
import javax.persistence.*

@Entity
class Lot(
        var id: UUID = UUID.randomUUID(),
        val numberOfBags: Int = 0,
        @Embedded
        @AttributeOverrides(value = [AttributeOverride(name = "value", column = Column(name = "avg_weight_value")), AttributeOverride(name = "unit", column = Column(name = "avg_weight_unit"))])
        val averageWeight: Weight = Weight(0.0, Weight.WeightUnit.KG),
        @Embedded
        @AttributeOverrides(value = [
            AttributeOverride(name = "value", column = Column(name = "tot_weight_value")),
            AttributeOverride(name = "unit", column = Column(name = "tot_weight_unit"))
        ])
        var totalWeight: Weight = Weight(0.0, Weight.WeightUnit.KG),
        val customer: UUID = UUID.randomUUID(),
        val type: String = "",
        val numberOfEmptyBagsGiven: Int = 0,
        val isPalledariPaid: Boolean = false,
        val comments: String = "",
        @Id
        @GeneratedValue(strategy = GenerationType.SEQUENCE,
                generator="lot_serial_number_seq")
        var serialNumber: Int = 0

) {
//    @CreatedDate
//    lateinit var createdAt: LocalDateTime
//    @LastModifiedDate
//    lateinit var updatedAt: LocalDateTime
}

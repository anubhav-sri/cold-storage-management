package com.saikrishna.wms.models

import java.math.BigDecimal
import javax.persistence.Embeddable
import javax.persistence.EnumType
import javax.persistence.Enumerated

@Embeddable
class Weight(
        val value: Double = 0.0,
        @Enumerated(EnumType.STRING) val unit: WeightUnit = WeightUnit.KG) {
    enum class WeightUnit {
        KG,
        Quintal,
        Tonne

    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Weight

        if (value != other.value) return false
        if (unit != other.unit) return false

        return true
    }

    override fun hashCode(): Int {
        var result = value.hashCode()
        result = 31 * result + unit.hashCode()
        return result
    }

}

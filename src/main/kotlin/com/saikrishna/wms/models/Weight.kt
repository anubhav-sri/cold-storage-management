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
}

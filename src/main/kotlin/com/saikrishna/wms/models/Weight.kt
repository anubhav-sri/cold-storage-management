package com.saikrishna.wms.models

import javax.persistence.Embeddable

@Embeddable
class Weight(val value: Double = 0.0, val unit: WeightUnit = WeightUnit.KG) {
    enum class WeightUnit {
        KG,
        Quintal,
        Tonne

    }
}

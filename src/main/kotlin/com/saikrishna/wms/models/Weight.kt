package com.saikrishna.wms.models

class Weight(val value: Double, val unit: WeightUnit) {
    enum class WeightUnit {
        KG,
        Quintal,
        Tonne

    }
}

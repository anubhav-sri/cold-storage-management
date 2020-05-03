package com.saikrishna.wms.models

import lombok.EqualsAndHashCode
import java.io.Serializable
import javax.persistence.Embeddable

@Embeddable
@EqualsAndHashCode
class LotLocationId(val lotNumber: Int = 0,
                    val locationId: String = "") : Serializable {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as LotLocationId

        if (lotNumber != other.lotNumber) return false
        if (locationId != other.locationId) return false

        return true
    }

    override fun hashCode(): Int {
        var result = lotNumber
        result = 31 * result + locationId.hashCode()
        return result
    }
}
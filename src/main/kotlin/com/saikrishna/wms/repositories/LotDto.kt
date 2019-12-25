package com.saikrishna.wms.repositories

import com.saikrishna.wms.models.Customer
import com.saikrishna.wms.models.Weight
import java.util.*
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.OneToMany

@Entity
data class LotDto(
        @Id
        val id: UUID,
        val numberOfBags: Int,
        val averageWeight: Weight,
        var totalWeight: Weight,
        @OneToMany val customer: Customer)

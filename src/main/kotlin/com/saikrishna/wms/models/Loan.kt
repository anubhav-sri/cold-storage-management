package com.saikrishna.wms.models

import java.math.BigDecimal
import java.time.LocalDate
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id

@Entity
data class Loan(
        @Id
        val id: Int = 0,
        val principal: BigDecimal = BigDecimal.ZERO,
        val date: LocalDate = LocalDate.now())
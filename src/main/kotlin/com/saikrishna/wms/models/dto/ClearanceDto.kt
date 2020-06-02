package com.saikrishna.wms.models.dto

import java.time.LocalDate

data class ClearanceDto(
        val id: Int? = null,
        val lotNumber: Int,
        val numberOfBags: Int,
        val date: LocalDate)

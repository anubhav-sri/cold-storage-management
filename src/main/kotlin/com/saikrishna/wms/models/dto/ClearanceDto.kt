package com.saikrishna.wms.models.dto

import lombok.EqualsAndHashCode

@EqualsAndHashCode
data class ClearanceDto(
        val id: Int? = null,
        val lotNumber: Int,
        val numberOfBags: Int,
        val date: String)

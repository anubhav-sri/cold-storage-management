package com.saikrishna.wms.models

import javax.persistence.Entity
import javax.persistence.Id


@Entity
data class Location(
		@Id
        val id: String = "",
		val chamber: Int = 0,
		val floor: Char = 'A',
		val rack: Int = 3)

package com.saikrishna.wms.models

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

@Entity
data class Jobber(
		@Id
		@GeneratedValue(strategy = GenerationType.SEQUENCE,
				generator = "jobber_id_seq")
		val id: Int = 0,
		val name: String = "",
		val address: String = "",
		val phoneNumber: String = ""
) : Cust

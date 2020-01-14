package com.saikrishna.wms.models

import java.util.*
import javax.persistence.*

@Entity
data class Customer(
        @Id
        val id: UUID = UUID.randomUUID(),
        val name: String = "",
        val fatherName: String = "",
        val address: String = "",
        val phoneNumber: String = "")

package com.saikrishna.wms.models

import javax.persistence.Embedded
import javax.persistence.Entity

@Entity
data class Customer(@Embedded val name: Name,
                    @Embedded val fatherName: Name,
                    val address: String,
                    val phoneNumber: Long)

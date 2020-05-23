package com.saikrishna.wms.models

import javax.persistence.*


@Entity
data class User(
        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        val id: Long? = null,
        @Column(nullable = false, unique = true)
        val username: String = "",
        val password: String = "")

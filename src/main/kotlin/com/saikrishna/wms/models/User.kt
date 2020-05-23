package com.saikrishna.wms.models

import javax.persistence.*


@Entity
@Table(name = "app_user")
data class User(
        @Id
        @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "app_user_id_seq")
        val id: Int? = null,
        @Column(nullable = false, unique = true)
        val username: String = "",
        val password: String = "")

package com.saikrishna.wms.repositories

import com.saikrishna.wms.models.User
import org.springframework.data.jpa.repository.JpaRepository


interface UserRepository : JpaRepository<User, Long> {
    fun findByUsername(username: String): User?
}
package com.saikrishna.wms.repositories

import com.saikrishna.wms.models.Clearance
import org.springframework.data.jpa.repository.JpaRepository

interface ClearanceRepository : JpaRepository<Clearance, Int>
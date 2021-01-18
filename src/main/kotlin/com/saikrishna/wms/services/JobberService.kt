package com.saikrishna.wms.services

import com.saikrishna.wms.models.Jobber
import com.saikrishna.wms.repositories.JobberRepository
import org.springframework.stereotype.Service

@Service
class JobberService(private val jobberRepository: JobberRepository) {
    fun saveJobber(jobber: Jobber): Jobber {
       return jobberRepository.save(jobber)
    }

    fun findAll(): List<Jobber> {
        return jobberRepository.findAll().toList()
    }
}

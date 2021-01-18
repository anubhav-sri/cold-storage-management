package com.saikrishna.wms.controllers

import com.saikrishna.wms.mappers.JobberMapper
import com.saikrishna.wms.models.dto.JobberDto
import com.saikrishna.wms.services.JobberService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class JobberController(private val jobberService: JobberService) {

	@PostMapping(value = ["/jobber"], consumes = ["application/json"], produces = ["application/json"])
	fun addJobber(@RequestBody jobberDto: JobberDto): JobberDto {
		return JobberMapper.toDto(jobberService.saveJobber(JobberMapper.toJobber(jobberDto)))
	}

	@GetMapping(value = ["/jobber"], produces = ["application/json"])
	fun findAllJobbers(): List<JobberDto> {
		return jobberService.findAll().map { k -> JobberMapper.toDto(k) }
	}

}

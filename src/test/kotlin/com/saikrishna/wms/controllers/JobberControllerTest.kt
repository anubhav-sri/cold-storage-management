package com.saikrishna.wms.controllers

import com.saikrishna.wms.models.Jobber
import com.saikrishna.wms.models.dto.JobberDto
import com.saikrishna.wms.services.JobberService
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

internal class JobberControllerTest {

	private val jobberService: JobberService = mockk()

	@Test
	fun `should add a jobber`() {
		val jobber = Jobber(name = "", address = "", phoneNumber = "")
		val expectedJobber = Jobber(id = 1, name = "", address = "", phoneNumber = "")
		val expectedJobberDto = JobberDto(id = 1, name = "", address = "", phoneNumber = "")

		every { jobberService.saveJobber(jobber) } returns expectedJobber

		val savedJobber = JobberController(jobberService)
				.addJobber(jobberDto = JobberDto(name = "", address = "", phoneNumber = "", id = 0))

		assertThat(savedJobber).isEqualTo(expectedJobberDto)
		verify { jobberService.saveJobber(jobber) }
	}

	@Test
	fun `should get all Jobbers`() {
		val expectedJobberDto = JobberDto(id = 1, name = "", address = "", phoneNumber = "")
		val jobber = Jobber(id = 1, name = "", address = "", phoneNumber = "")
		every { jobberService.findAll() } returns listOf(jobber)

		val savedJobbers = JobberController(jobberService)
				.findAllJobbers()

		assertThat(savedJobbers).containsOnly(expectedJobberDto)
		verify { jobberService.findAll() }
	}

}

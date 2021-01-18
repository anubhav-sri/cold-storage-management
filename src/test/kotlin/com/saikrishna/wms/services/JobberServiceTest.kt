package com.saikrishna.wms.services

import com.saikrishna.wms.models.Jobber
import com.saikrishna.wms.repositories.JobberRepository
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test

internal class JobberServiceTest {
	private val jobberRepository: JobberRepository = mockk()

	@Test
	fun shouldSaveJobber() {
		val jobber = Jobber(name = "test", address = "asas", phoneNumber = "1234567891")
		val savedJobber = Jobber(id = 1, name = "test", address = "asas", phoneNumber = "1234567891")
		every { jobberRepository.save(jobber) } returns savedJobber
		JobberService(jobberRepository).saveJobber(jobber)
		verify { jobberRepository.save(jobber) }
	}

	@Test
	fun shouldGetAllJobbers() {
		val savedJobber = Jobber(id = 1, name = "test", address = "asas", phoneNumber = "1234567891")
		every { jobberRepository.findAll() } returns listOf(savedJobber)
		val clearances: List<Jobber> = JobberService(jobberRepository)
				.findAll()
		Assertions.assertThat(clearances).containsExactly(savedJobber)
		verify { jobberRepository.findAll() }
	}

}

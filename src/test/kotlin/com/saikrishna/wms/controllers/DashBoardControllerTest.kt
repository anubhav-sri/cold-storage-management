package com.saikrishna.wms.controllers

import com.saikrishna.wms.models.Summary
import com.saikrishna.wms.services.LotService
import io.mockk.every
import io.mockk.mockk
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import java.time.LocalDateTime

internal class DashBoardControllerTest {
	private val lotService: LotService = mockk()

	@Test
	fun shouldBeAbleToGetSummary() {
		val start = LocalDateTime.now().minusDays(3)
		val end = LocalDateTime.now()

		val expectedSummary = Summary(12, 23)
		every { lotService.findSummaryBetween(start, end) } returns Summary(12, 23)

		val summary: Summary? = DashBoardController(lotService)
				.getSummary(start, end).body

		Assertions.assertThat(summary).isEqualTo(expectedSummary)

	}
}

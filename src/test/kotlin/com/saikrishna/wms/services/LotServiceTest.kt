package com.saikrishna.wms.services

import com.nhaarman.mockitokotlin2.*
import com.saikrishna.wms.models.*
import com.saikrishna.wms.repositories.LotRepository
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.mockito.ArgumentCaptor
import java.math.BigDecimal
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.*


internal class LotServiceTest {

	private val lotRepo: LotRepository = mock()

	private var lotService: LotService = LotService(lotRepo)

	@Test
	fun shouldSaveTheLot() {
		val customer = Customer(UUID.randomUUID(), "name",
				"fname", "addr1", "12342")
		val lot = Lot(UUID.randomUUID(), LocalDateTime.now(), 20,
				Weight(12.0, Weight.WeightUnit.KG),
				customer = customer.id, type = "G4")

		val passedLot = Lot(UUID.randomUUID(), LocalDateTime.now(), 20,
				Weight(12.0, Weight.WeightUnit.KG),
				Weight(240.0, Weight.WeightUnit.KG),
				customer = customer.id, type = "G4")

		val expectedLot = Lot(UUID.randomUUID(), LocalDateTime.now(), 20,
				Weight(12.0, Weight.WeightUnit.KG),
				Weight(240.0, Weight.WeightUnit.KG),
				customer = customer.id, type = "G4")

		val lotCaptor: ArgumentCaptor<Lot> = ArgumentCaptor.forClass(Lot::class.java);

		given(lotRepo.save(lotCaptor.capture())).willReturn(expectedLot)

		val savedLot = lotService.saveLot(lot)

		assertThat(savedLot).isEqualTo(expectedLot)
		assertThat(lotCaptor.firstValue)
				.isEqualToIgnoringGivenFields(passedLot,
						"id", "date")
	}

	@Test
	fun `should save multiple lots`() {
		val customer = Customer(UUID.randomUUID(), "name",
				"fname", "addr1", "12342")
		val lot = Lot(UUID.randomUUID(), LocalDateTime.now(), 20,
				Weight(12.0, Weight.WeightUnit.KG),
				Weight(23.0, Weight.WeightUnit.KG),
				customer.id, type = "G4")

		given(lotRepo.saveAll(listOf(lot))).willReturn(listOf(lot))
		lotService.saveAll(listOf(lot))
		verify(lotRepo).saveAll(listOf(lot))
	}

	@Test
	fun shouldFindTheLotBySerialNumber() {

		val customer = Customer(UUID.randomUUID(), "name",
				"fname", "addr1", "12342")
		val lot = Lot(UUID.randomUUID(), LocalDateTime.now(), 20,
				Weight(12.0, Weight.WeightUnit.KG),
				Weight(23.0, Weight.WeightUnit.KG),
				customer.id, type = "G4", serialNumber = 1)

		given(lotRepo.findById(1)).willReturn(Optional.of(lot))
		lotService.findByLotNumber(lot.serialNumber)
		verify(lotRepo).findById(lot.serialNumber)
	}

	@Test
	fun `should update the locations`() {
		val customer = Customer(UUID.randomUUID(), "name",
				"fname", "addr1", "12342")
		val lot = Lot(UUID.randomUUID(), LocalDateTime.now(), 20,
				Weight(12.0, Weight.WeightUnit.KG),
				Weight(23.0, Weight.WeightUnit.KG),
				customer.id, type = "G4", serialNumber = 1)
		val lotLocation = LotLocation(lot = lot, id = LotLocationId(lot.serialNumber, "1-A-24"), date = LocalDate.now(), location = Location("1-A-24", 1, 'A', 24))
		lot.location = linkedSetOf(lotLocation)

		lotService.updateLocations(listOf(lotLocation))

		verify(lotRepo).saveAll(listOf(lot))
	}

	@Test
	fun `should update the locations when called multiple times`() {
		val customer = Customer(UUID.randomUUID(), "name",
				"fname", "addr1", "12342")
		val lot = Lot(UUID.randomUUID(), LocalDateTime.now(), 20,
				Weight(12.0, Weight.WeightUnit.KG),
				Weight(23.0, Weight.WeightUnit.KG),
				customer.id, type = "G4", serialNumber = 1)

		val lotLocation = LotLocation(lot = lot, id = LotLocationId(lot.serialNumber, "1-A-24"), date = LocalDate.now(), location = Location("1-A-24", 1, 'A', 24))

		val newLotLocation = LotLocation(lot = lot, id = LotLocationId(lot.serialNumber, "1-A-26"), date = LocalDate.now(), location = Location("1-A-26", 1, 'A', 26))
		lot.location = linkedSetOf(lotLocation)
		lotService.updateLocations(listOf(lotLocation))

		val newLot = lot.copy()
		newLot.location = linkedSetOf(newLotLocation)
		verify(lotRepo, atLeastOnce()).saveAll(listOf(lot))

		lotService.updateLocations(listOf(newLotLocation))
		verify(lotRepo, atLeastOnce()).saveAll(listOf(newLot))

	}

	@Test
	fun `should be able to add loan to a lot`() {
		val customer = Customer(UUID.randomUUID(), "name",
				"fname", "addr1", "12342")
		val lot = Lot(UUID.randomUUID(), LocalDateTime.now(), 20,
				Weight(12.0, Weight.WeightUnit.KG),
				Weight(23.0, Weight.WeightUnit.KG),
				customer.id, type = "G4", serialNumber = 1)
		lot.addLoan(Loan(lot.serialNumber, BigDecimal.TEN, LocalDate.now()))

		given(lotRepo.save(lot)).willReturn(lot)

		val savedLot = lotService.saveLot(lot)

		assertThat(savedLot).isEqualTo(lot)

	}

	@Test
	fun `should find total lot Lot till now`() {

		given(lotRepo.count()).willReturn(1)

		assertThat(lotService.getTotalLot()).isEqualTo(1)

		verify(lotRepo).count()
	}

	@Test
	fun `should find summary in between`() {
		val start = LocalDateTime.now().minusDays(3)
		val end = LocalDateTime.now()
		val customer = Customer(UUID.randomUUID(), "name",
				"fname", "addr1", "12342")
		val lot = Lot(UUID.randomUUID(), LocalDateTime.now(), 20,
				Weight(12.0, Weight.WeightUnit.KG),
				Weight(23.0, Weight.WeightUnit.KG),
				customer.id, type = "G4", serialNumber = 1, numberOfEmptyBagsGiven = 12)

		given(lotRepo.findAllByDateBetween(start, end)).willReturn(listOf(lot))

		assertThat(lotService.findSummaryBetween(start, end)).isEqualTo(Summary(20, 12))

		verify(lotRepo).findAllByDateBetween(start, end)
	}

}

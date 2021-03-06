package com.saikrishna.wms.services

import com.saikrishna.wms.models.Lot
import com.saikrishna.wms.models.LotLocation
import com.saikrishna.wms.models.Summary
import com.saikrishna.wms.models.Weight
import com.saikrishna.wms.repositories.LotRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.time.LocalDateTime
import java.util.*

@Service
class LotService(@Autowired private val lotRepo: LotRepository) {
	fun saveLot(lot: Lot): Lot {
		calculateTotalWeight(lot)
		return lotRepo.save(lot)
	}

	fun findByLotNumber(serialNumber: Int): Optional<Lot> {
		return lotRepo.findById(serialNumber)
	}

	fun saveAll(lots: List<Lot>): Iterable<Lot> {
		lots.forEach { l -> calculateTotalWeight(l) }
		return lotRepo.saveAll(lots)
	}

	fun updateLocations(lotLocations: List<LotLocation>): Iterable<Lot> {
		val lots = lotLocations.map { lotLocation -> lotLocation.lot }
		lots.forEach { lot -> lot.location = hashSetOf() }
		lotLocations.forEach { lotLocation -> lotLocation.lot.addLocation(location = lotLocation.location, date = lotLocation.date) }
		return lotRepo.saveAll(lots)
	}

	fun findSummaryBetween(from: LocalDateTime, to: LocalDateTime): Summary {
		val lots = lotRepo.findAllByDateBetween(from, to)
		if (lots.isEmpty()) {
			return Summary(0, 0)
		}

		val totalBags = lots.map { l -> l.numberOfBags }.reduce { sum, bags -> sum + bags }
		val totalEmptyBags = lots.map { l -> l.numberOfEmptyBagsGiven }.reduce { sum, bags -> sum + bags }
		return Summary(totalBags, totalEmptyBags)
	}

	fun getTotalLot(): Long {
		return lotRepo.count()
	}

	private fun calculateTotalWeight(lot: Lot) {
		lot.totalWeight = Weight(lot.averageWeight.value.times(lot.numberOfBags), lot.averageWeight.unit)
	}
}

package com.saikrishna.wms.models

data class Summary(private val numberOfBags: Int, private val numberOfEmptyBags: Int) {

	fun getNumberOfBags(): Int {
		return numberOfBags
	}
	fun getNumberOfEmptyBagsGiven(): Int {
		return numberOfEmptyBags
	}
}


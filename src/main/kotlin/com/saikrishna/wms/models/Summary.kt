package com.saikrishna.wms.models

interface Summary {
	fun getNumberOfBags(): Int = 0
	fun getNumberOfEmptyBagsGiven(): Int = 0
}


package com.saikrishna.wms.services

import com.nhaarman.mockitokotlin2.given
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.saikrishna.wms.models.Location
import com.saikrishna.wms.repositories.LocationRepository
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import java.util.*

internal class LocationServiceTest {
    private val locationRepo: LocationRepository = mock()

    private var locationService: LocationService = LocationService(locationRepo)

    @Test
    fun shouldSaveTheLocation() {
        val location = Location("1-A-24", 1, 'A', 24)

        given(locationRepo.save(location)).willReturn(location)

        val savedLocation = locationService.saveLocation(location)

        Assertions.assertThat(savedLocation).isEqualTo(location)
    }

    @Test
    fun `should save multiple lots`() {
        val location = Location("1-A-24", 1, 'A', 24)

        given(locationRepo.saveAll(listOf(location))).willReturn(listOf(location))

        val savedLocation = locationService.saveAll(listOf(location))

        Assertions.assertThat(savedLocation).isEqualTo(listOf(location))
    }

    @Test
    fun `should find location by id`() {
        val location = Location("1-A-24", 1, 'A', 24)

        given(locationRepo.findById("1-A-24")).willReturn(Optional.of(location))

        locationService.findById("1-A-24")

        verify(locationRepo).findById("1-A-24")
    }

}
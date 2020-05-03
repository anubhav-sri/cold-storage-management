package com.saikrishna.wms.services

import com.saikrishna.wms.models.Location
import com.saikrishna.wms.repositories.LocationRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class LocationService(@Autowired private val locationRepo: LocationRepository

) {
    fun saveLocation(location: Location): Location {
        return locationRepo.save(location)
    }

    fun saveAll(locations: List<Location>): Iterable<Location> {
        return locationRepo.saveAll(locations)
    }

    fun findById(locationId: String): Location {

        return locationRepo.findById(locationId).orElseThrow()
    }
}

package com.saikrishna.wms.controllers

import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.given
import com.nhaarman.mockitokotlin2.mock
import com.saikrishna.wms.models.Location
import com.saikrishna.wms.models.dto.LotLocationDTO
import com.saikrishna.wms.models.Lot
import com.saikrishna.wms.services.CsvLotParser
import com.saikrishna.wms.services.LocationService
import com.saikrishna.wms.services.LotService
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.mock.web.MockMultipartFile
import java.util.*

internal class LotLocationControllerTest {
    private val lotService: LotService = mock()
    private val csvLotParser: CsvLotParser = mock()
    private val locationService: LocationService = mock()

    @Test
    fun `should save the lot location csv file to the lot service`() {
        val lotLocationController = LotLocationController(lotService, csvLotParser, locationService)
        val file = MockMultipartFile("file", "newData.csv", null, this.javaClass.classLoader.getResourceAsStream("testFile.csv"))

        val locationOfLotInFile = LotLocationDTO(1, "25-02-2020", "A-12-43")
        val lotLocations = listOf(locationOfLotInFile)

        given(csvLotParser.parseCsvToLotLocation(file)).willReturn(lotLocations)
        val lot: Lot = mock()
        val location: Location = mock()
        given(lotService.findByLotNumber(1)).willReturn(Optional.of(lot))
        given(locationService.findById("A-12-43")).willReturn(location)
        given(lotService.updateLocations(any())).willReturn(listOf(lot))

        val response = lotLocationController.loadLotLocations(file)

        Assertions.assertThat(response).isEqualTo("Update Locations for 1 number of lots")
    }
}
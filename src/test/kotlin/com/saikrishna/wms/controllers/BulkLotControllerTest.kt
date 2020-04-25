package com.saikrishna.wms.controllers

import com.saikrishna.wms.exceptions.InvalidFileNameException
import com.saikrishna.wms.models.Customer
import com.saikrishna.wms.models.CustomerLot
import com.saikrishna.wms.repositories.Lot
import com.saikrishna.wms.services.CsvLotParser
import com.saikrishna.wms.services.CustomerService
import com.saikrishna.wms.services.LotService
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Test
import org.springframework.mock.web.MockMultipartFile
import java.nio.file.Files
import java.nio.file.Path

internal class BulkLotControllerTest {
    private val customerService: CustomerService = mockk()
    private val lotService: LotService = mockk()
    private val csvLotParser: CsvLotParser = mockk()

    @Test
    fun `should return 400 bad request if the uploaded file is not csv extended`() {
        val bulkLotController = BulkLotController(lotService, customerService, csvLotParser)
        val file = MockMultipartFile("newData", null)
        assertThrows(InvalidFileNameException::class.java) { bulkLotController.loadData(file) };
    }

    @Test
    fun `should save the csv file to the service`() {
        val bulkLotController = BulkLotController(lotService, customerService, csvLotParser)
        val file = MockMultipartFile("newData.csv", Files.newInputStream(Path.of("/Users/anubhavsrivastava/code/sai/wms/src/test/resources/testFile.csv")))

        val lotInFile = Lot()
        val customerOnFile = Customer()
        val customerLots = listOf(CustomerLot(customerOnFile, lotInFile))
        every { csvLotParser.parseCsvToLot(file) } returns customerLots
        every { lotService.saveAll(listOf(lotInFile)) } returns listOf(lotInFile)
        every { customerService.saveAll(listOf(customerOnFile)) } returns listOf(customerOnFile)

        val lot = bulkLotController.loadData(file)

        verify { lotService.saveAll(listOf(lotInFile)) }
        verify { customerService.saveAll(listOf(customerOnFile)) }
        assertThat(lot).isEqualTo("Saved 1 number of lots")
    }
}
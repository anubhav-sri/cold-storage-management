package com.saikrishna.wms.controllers

import com.saikrishna.wms.exceptions.InvalidFileNameException
import com.saikrishna.wms.models.CustomerLot
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
import java.util.*

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

        val customerLotInFile = CustomerLot( 1,
                "someName", "fatherName", "address", "phoneNumber", "20-09-2019",
                12, 50.0, "G4", 12,false, "comments")
        val customerLots = listOf(customerLotInFile)
        every { csvLotParser.parseCsvToLot(file) } returns customerLots
        every { lotService.saveAll(listOf(customerLotInFile.toLot())) } returns listOf(customerLotInFile.toLot())
        every { customerService.saveAll(listOf(customerLotInFile.toCustomer())) } returns listOf(customerLotInFile.toCustomer())

        val lot = bulkLotController.loadData(file)

        verify { lotService.saveAll(listOf(customerLotInFile.toLot())) }
        verify { customerService.saveAll(listOf(customerLotInFile.toCustomer())) }
        assertThat(lot).isEqualTo("Saved 1 number of lots")
    }
}
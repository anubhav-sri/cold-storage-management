package com.saikrishna.wms.services

import com.saikrishna.wms.exceptions.FailedToParseCsvFileException
import com.saikrishna.wms.models.CustomerLot
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.mock.web.MockMultipartFile

internal class CsvLotParserTest {

    private val csvLotParser = CsvLotParser()

    @Test
    fun `should parse the csv to objects with multiple rows`() {
        val resourceAsStream = this.javaClass.classLoader.getResourceAsStream("testFile.csv")
        val file = MockMultipartFile("newData.csv", resourceAsStream)
        val parseCsvToLot = csvLotParser.parseCsvToLot(file)
        assertThat(parseCsvToLot.size).isEqualTo(8)

    }

    @Test
    fun `should parse the csv to objects with single row`() {
        val resourceAsStream = this.javaClass.classLoader.getResourceAsStream("testFile_singleRow.csv")
        val file = MockMultipartFile("newData.csv", resourceAsStream)
        val parseCsvToLot = csvLotParser.parseCsvToLot(file)

        val expectedCustomerLot = CustomerLot(1, "NANKU RAM PATEL",
                "RAMJIYAWAN", "REKHAYI KA PURA", "7991529353",
                "25-02-2020", 19, 50.0, "GOLA", 50, false, "")

        assertThat(parseCsvToLot.size).isEqualTo(1)
        val parsedRow = parseCsvToLot[0]
        assertThat(parsedRow).isEqualToIgnoringGivenFields(expectedCustomerLot, "customerId", "lotId")
    }

    @Test
    fun `should throw UnableToParseCsv exception if data is wrong`() {
        val resourceAsStream = this.javaClass.classLoader.getResourceAsStream("testFile_wrong_data.csv")
        val file = MockMultipartFile("newData.csv", resourceAsStream)
        Assertions.assertThrows(FailedToParseCsvFileException::class.java) { csvLotParser.parseCsvToLot(file) }
    }
}
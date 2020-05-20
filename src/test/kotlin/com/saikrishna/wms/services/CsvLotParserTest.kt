package com.saikrishna.wms.services

import com.saikrishna.wms.exceptions.FailedToParseCsvFileException
import com.saikrishna.wms.models.CustomerLot
import com.saikrishna.wms.models.LotLocationDTO
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.mock.web.MockMultipartFile
import java.math.BigDecimal

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
                "25-02-2020", 19, 50.0, "GOLA", 50, false, "",
                BigDecimal.valueOf(2000))

        assertThat(parseCsvToLot.size).isEqualTo(1)
        val parsedRow = parseCsvToLot[0]
        assertThat(parsedRow).isEqualToIgnoringGivenFields(expectedCustomerLot, "customerId", "lotId")
    }

    @Test
    fun `should parse the csv to lot location objects with single row`() {
        val resourceAsStream = this.javaClass.classLoader.getResourceAsStream("testFile_lot_location.csv")
        val file = MockMultipartFile("newData.csv", resourceAsStream)
        val parseCsvToLotLocation = csvLotParser.parseCsvToLotLocation(file)

        val expectedLotLocation = LotLocationDTO(1, "25-02-2020", "1-A-24")
        assertThat(parseCsvToLotLocation.size).isEqualTo(1)
        val parsedRow = parseCsvToLotLocation[0]
        assertThat(parsedRow).isEqualToIgnoringGivenFields(expectedLotLocation)
    }

    @Test
    fun `should throw UnableToParseCsv exception if data is wrong`() {
        val resourceAsStream = this.javaClass.classLoader.getResourceAsStream("testFile_wrong_data.csv")
        val file = MockMultipartFile("newData.csv", resourceAsStream)
        Assertions.assertThrows(FailedToParseCsvFileException::class.java) { csvLotParser.parseCsvToLot(file) }
    }

    @Test
    fun `should not add loan in the lot if no loan present`() {
        val resourceAsStream = this.javaClass.classLoader.getResourceAsStream("testFile_singleRow_wo_loan.csv")
        val file = MockMultipartFile("newData.csv", resourceAsStream)
        val parseCsvToLot = csvLotParser.parseCsvToLot(file)

        val expectedCustomerLot = CustomerLot(1, "NANKU RAM PATEL",
                "RAMJIYAWAN", "REKHAYI KA PURA", "7991529353",
                "25-02-2020", 19, 50.0, "GOLA", 50, false, "",
                null)

        assertThat(parseCsvToLot.size).isEqualTo(1)
        val parsedRow = parseCsvToLot[0]
        assertThat(parsedRow).isEqualToIgnoringGivenFields(expectedCustomerLot, "customerId", "lotId")
        assertThat(parsedRow.toLot().loan).isNull()
    }
}
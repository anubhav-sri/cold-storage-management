package com.saikrishna.wms.services

import com.fasterxml.jackson.databind.MappingIterator
import com.fasterxml.jackson.dataformat.csv.CsvMapper
import com.fasterxml.jackson.dataformat.csv.CsvSchema
import com.saikrishna.wms.exceptions.FailedToParseCsvFileException
import com.saikrishna.wms.models.CustomerLot
import com.saikrishna.wms.models.LotLocationDTO
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import org.springframework.web.multipart.MultipartFile

@Component
open class CsvLotParser {
    open fun parseCsvToLot(dataFile: MultipartFile): List<CustomerLot> {
        return try {
            val bootstrapSchema = CsvSchema.emptySchema().withHeader()
            val mapper = CsvMapper()
            val readValues: MappingIterator<CustomerLot> = mapper.readerFor(CustomerLot::class.java).with(bootstrapSchema).readValues(dataFile.resource.inputStream)
            readValues.readAll()
        } catch (ex: Exception) {
            logger.error("Unable to parse the file", ex)
            throw FailedToParseCsvFileException()
        }
    }

    open fun parseCsvToLotLocation(dataFile: MultipartFile): List<LotLocationDTO> {
        return try {
            val bootstrapSchema = CsvSchema.emptySchema().withHeader()
            val mapper = CsvMapper()
            val readValues: MappingIterator<LotLocationDTO> = mapper.readerFor(LotLocationDTO::class.java)
                    .with(bootstrapSchema)
                    .readValues(dataFile.resource.inputStream)
            readValues.readAll()
        } catch (ex: Exception) {
            logger.error("Unable to parse the file", ex)
            throw FailedToParseCsvFileException()
        }
    }

    companion object {
        val logger: Logger = LoggerFactory.getLogger(this::class.java)
    }
}
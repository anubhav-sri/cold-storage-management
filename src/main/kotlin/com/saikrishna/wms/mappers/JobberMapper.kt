package com.saikrishna.wms.mappers

import com.saikrishna.wms.models.Jobber
import com.saikrishna.wms.models.dto.JobberDto

class JobberMapper {

    companion object Mapper {
        fun toJobber(dto: JobberDto): Jobber {
            return Jobber(name = dto.name,address = dto.address, phoneNumber = dto.phoneNumber )
        }

        fun toDto(jobber: Jobber): JobberDto {
            return JobberDto(id = jobber.id, name = jobber.name, address = jobber.address, phoneNumber = jobber.phoneNumber)
        }
    }
}

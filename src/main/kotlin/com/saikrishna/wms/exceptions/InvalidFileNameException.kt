package com.saikrishna.wms.exceptions

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "File should be a csv")
class InvalidFileNameException(name: String) : RuntimeException("File should be a csv file. File Name:$name") {
}
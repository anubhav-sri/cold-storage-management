package com.saikrishna.wms.exceptions

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Lot not found with given serial number")
class LotNotFoundException(lotNumber: Int) : RuntimeException("Lot $lotNumber Not Found")

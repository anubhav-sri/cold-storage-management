package com.saikrishna.wms.exceptions

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(value = HttpStatus.UNAUTHORIZED, reason = "Wrong password")
class InvalidPasswordException(userName: String) : RuntimeException("Invalid password found for username $userName")

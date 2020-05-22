package com.saikrishna.wms.exceptions

class InvalidPasswordException(userName: String) : RuntimeException("Invalid password found for username $userName")

package com.saikrishna.wms.exceptions

import java.lang.RuntimeException

class UserNotFoundException(userName: String) : RuntimeException("No user found for username $userName")
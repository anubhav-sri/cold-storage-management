package com.saikrishna.wms.models

import java.util.*

class LoginResponse(val token: String, val expiresAt: Date, val role: Role, val name: String)

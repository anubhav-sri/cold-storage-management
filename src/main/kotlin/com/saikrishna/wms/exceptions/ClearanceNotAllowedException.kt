package com.saikrishna.wms.exceptions

class ClearanceNotAllowedException(lotNumber: Int) : RuntimeException("Not enough bags available for lot $lotNumber")

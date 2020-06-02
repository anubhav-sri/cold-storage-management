package com.saikrishna.wms.exceptions

class LotNotFoundException(lotNumber: Int) : RuntimeException("Lot $lotNumber Not Found")

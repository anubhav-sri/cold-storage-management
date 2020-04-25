package com.saikrishna.wms.models

import com.saikrishna.wms.repositories.Lot

class CustomerLot(val customer: Customer,
                  val lot: Lot) {}
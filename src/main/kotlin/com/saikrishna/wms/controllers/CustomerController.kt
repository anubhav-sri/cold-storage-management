package com.saikrishna.wms.controllers

import com.saikrishna.wms.models.Customer
import com.saikrishna.wms.services.CustomerService
import org.springframework.http.ResponseEntity
import java.util.*

class CustomerController(private val customerService: CustomerService) {
    fun getCustomer(customerId: UUID): ResponseEntity<Customer> {
        return ResponseEntity.ok(customerService.getCustomer(customerId))
    }
}

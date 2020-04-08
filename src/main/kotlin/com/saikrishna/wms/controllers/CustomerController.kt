package com.saikrishna.wms.controllers

import com.saikrishna.wms.models.Customer
import com.saikrishna.wms.services.CustomerService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.RestController
import java.util.*

@CrossOrigin
@RestController
class CustomerController(private val customerService: CustomerService) {
    fun getCustomer(customerId: UUID): ResponseEntity<Customer> {
        return ResponseEntity.ok(customerService.getCustomer(customerId))
    }
}

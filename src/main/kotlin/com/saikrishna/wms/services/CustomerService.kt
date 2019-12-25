package com.saikrishna.wms.services

import com.saikrishna.wms.models.Customer
import com.saikrishna.wms.repositories.CustomerRepository

class CustomerService(val customerRepository: CustomerRepository) {
    fun saveCustomer(customer: Customer) {
        customerRepository.save(customer)
    }

}

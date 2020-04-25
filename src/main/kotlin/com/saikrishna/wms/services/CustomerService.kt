package com.saikrishna.wms.services

import com.saikrishna.wms.exceptions.CustomerNotFoundException
import com.saikrishna.wms.models.Customer
import com.saikrishna.wms.repositories.CustomerRepository
import org.springframework.stereotype.Service
import java.util.*

@Service
class CustomerService(private val customerRepository: CustomerRepository) {
    fun saveCustomer(customer: Customer): Customer {
        return customerRepository.save(customer)
    }

    fun getCustomer(customerId: UUID): Customer {
        return customerRepository.findById(customerId)
                .orElseThrow(CustomerNotFoundException::class.java::newInstance)
    }

    fun saveAll(customers: List<Customer>): Iterable<Customer> {
        return customerRepository.saveAll(customers)
    }

}
